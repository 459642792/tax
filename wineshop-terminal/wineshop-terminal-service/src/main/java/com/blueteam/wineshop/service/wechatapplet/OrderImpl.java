package com.blueteam.wineshop.service.wechatapplet;

import com.alibaba.fastjson.JSON;
import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.constant.OrderConstant;
import com.blueteam.base.help.generator.KeyGeneratorEnum;
import com.blueteam.base.help.generator.KeyGeneratorStrategy;
import com.blueteam.base.help.order.OrderStateHelp;
import com.blueteam.base.lang.RStr;
import com.blueteam.base.util.StringUtil;
import com.blueteam.entity.bo.OrderStateBO;
import com.blueteam.entity.bo.wechatapplet.*;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.wechatapplet.FaceToFaceOrderDTO;
import com.blueteam.entity.dto.wechatapplet.OrderConfirmationDTO;
import com.blueteam.entity.dto.wechatapplet.OrderGoodsDTO;
import com.blueteam.entity.po.*;
import com.blueteam.entity.vo.*;
import com.blueteam.wineshop.mapper.*;
import com.blueteam.wineshop.service.CouponInfoService;

import com.blueteam.wineshop.service.MessageSubService;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.json.Json;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单相关
 *
 * @author xiaojiang
 * @create 2018-01-05  16:31
 */
@Service
public class OrderImpl implements OrderService {
    /**
     * 订单相关
     */
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 用户地址相关
     */
    @Autowired
    private UserAddressMapper userAddressMapper;

    /**
     * 用户优惠券相关
     */
    @Autowired
    private CouponInfoService couponInfoService;
    @Resource(name="messageService")
    private  MessageSubService messageSubService;
    @Autowired
    private CouponRecordMapper couponRecordMapper;

    /**
     * 用户订单其他相关 （如：商品详情，订单类型，订单支付类型等）
     */
    @Autowired
    private OrderOtherMapper orderOtherMapper;
    /**
     * 用户订单商品属性
     */
    @Autowired
    private OrderAttrPOMapper orderAttrPOMapper;

    /**
     * 用户订单商品实例
     */
    @Autowired
    private OrderGoodsItemPOMapper orderGoodsItemPOMapper;
    /**
     * 用户订单配送信息
     */
    @Autowired
    private  OrderDispatchPOMapper orderDispatchPOMapper;

    @Autowired
    private OrderComplainService orderComplainService;

//    static {
//
//    }
    /**
     * 方法的功能描述: 获取支付类型的支付方式
     *@methodName listChannelPayWay
     * @param: vendorId
     * @param: payTypeId
     *@return com.blueteam.entity.dto.BaseResult
     *@since 1.4.0
     *@author xiaojiang 2018/1/9 10:41
     *@modifier
     */
    @Override
    public BaseResult listChannelPayWay(Integer vendorId, Integer payTypeId) {
        List<VendorPayWayVO> listPayType = orderOtherMapper.listVendorPayWay(vendorId,payTypeId);
        return ApiResult.success(listPayType);
    }

    /**
     * 方法的功能描述: 获取用户收获地址列表和优惠券列表和支付类型列表
     *
     * @return
     * @methodName
     * @ * @param: null
     * @author xiaojiang 2018/1/5 16:51
     * @modifier
     * @since 1.4.0
     */
    @Override
    public BaseResult listCouponAndAddress(Integer userId, Integer vendorId, Long totalMoney) {
        Map<String, Object> newMap = new HashMap<>();
        List<UserAddressVO> addressList = userAddressMapper.listUserAddress(userId, UserAddressPO.STATE_TAG_VALID);
        newMap.put("address", addressList);
        List<CouponBO> listCouponInfo = orderOtherMapper.listCouponByVendorAndUser(vendorId, userId);
        newMap.put("coupon", listCouponVo(listCouponInfo,totalMoney));
        //暂时只支撑微信渠道id
        List<VendorPayTypeVO> listPayType = orderOtherMapper.listVendorPayType(vendorId,1);
        newMap.put("payType", listPayType);
        return ApiResult.success(newMap);
    }
    /**
     * 方法的功能描述: 订单确认
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/8 11:41
     *@modifier
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult saveOrderConfirmation(OrderConfirmationDTO orderConfirmationDTO, Integer userId){
        Map<String,Object> map = new HashMap<>();
        Integer vendorId = orderConfirmationDTO.getVendorId();
        Long price  = 0L;
        //判断价格
        Map<String,Object> goodsMap = decideGoods(orderConfirmationDTO);
        boolean goodsFlag =   (Boolean) goodsMap.get("flag");
        List<OrderGoodsItemBO> orderGoodsItemBO;
        if(goodsFlag){
            price =  (Long) goodsMap.get("total");
            orderGoodsItemBO = (List<OrderGoodsItemBO>)goodsMap.get("listGoods");
        }else{
            return ApiResult.error(JSON.toJSONString(goodsMap));
        }
        OrderPO order = new OrderPO();
        //订单原价
        order.setOriginalPrice(price);
        //优惠券相关判断
        Integer couponId =  orderConfirmationDTO.getCouponId();
        if (RStr.isNotEmpty(couponId)){
            Map<String,Object> couponMap = decideCoupon(vendorId,userId,couponId,price);
            boolean flag =   (Boolean) couponMap.get("flag");
            if (flag) {
                Long couponTotal = (long )couponMap.get("couponMoney");
                if (orderConfirmationDTO.getCouponTotal().equals(couponTotal)){
                    Long couponAmount = getCouponAmount(price,couponTotal);
                    //优惠金额
                    order.setCouponAmount(couponAmount);
                }else{
                    map.put("msg","优惠券金额不对");
                    return ApiResult.error(JSON.toJSONString(map));
                }
            }else{
                return ApiResult.error(JSON.toJSONString(couponMap));
            }
            //判断优惠后的价格
            if(orderConfirmationDTO.getTotal().equals(order.getOriginalPrice()-order.getCouponAmount())){
                order.setPayPrice(orderConfirmationDTO.getTotal());
                //修改优惠券状态
                couponInfoService.updateCouponInfo(CouponInfo.COST_STATUS_USED,couponId);
            }else{
                map.put("msg","优惠后价格不一致");
                return ApiResult.error(JSON.toJSONString(map));
            }
        }else{
            //没有优惠券
            order.setCouponAmount(0L);
            order.setPayPrice(price);
        }

        order.setOrderId(KeyGeneratorStrategy.match(KeyGeneratorEnum.ORDER_ID).generateKey());
        order.setOrderNo(KeyGeneratorStrategy.match(KeyGeneratorEnum.ORDER_NO).generateKey().toString());
        order.setUserId(userId);
        order.setVendorId(orderConfirmationDTO.getVendorId());
        order.setOrderState(OrderConstant.OrderStateEnum.TO_PAY.getState());
        order.setCompleteState(OrderConstant.CompleteStateEnum.UNFINISHED.getState());
        order.setRefundState(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState());
        order.setCommentState(OrderConstant.CommentStateEnum.TO_COMMENT.getState());
        order.setOrderChannel(orderConfirmationDTO.getOrderChannel());
        //下单渠道开始默认支付渠道
        order.setPayChannel(orderConfirmationDTO.getOrderChannel());
        order.setPayTypeId(orderConfirmationDTO.getPayTypeId());
        order.setPayTypeName(orderConfirmationDTO.getPayTypeName());
        order.setOriginalPrice(price);
        order.setDeliveryType(orderConfirmationDTO.getDeliveryType());
        order.setDeliveryAddress(orderConfirmationDTO.getDeliveryAddress());
        order.setRemark(orderConfirmationDTO.getRemark());
        //暂时没有配送费用
        order.setDeliveryFee(0L);
        //配送时间 组装
        order.setDeliveryTime(orderConfirmationDTO.getDeliveryTime());
        order.setVersion(1);
        Date date = new Date();
        order.setCreateTime(date);
        order.setUpdateTime(date);
        order.setUpdateStaffId(userId);
        //保存订单
        int i = orderMapper.saveOrder(order);
        if(i == 1){
            //保存商品信息
            saveOrderGoodsItem(orderGoodsItemBO,order);
            //保存订单配送信息
//            if(orderConfirmationDTO.getDeliveryType() == 1){
            saveOrderAddress(orderConfirmationDTO,order);
//            }
            //保存优惠券信息
            if(RStr.isNotEmpty(couponId)){
                saveCouponRecord(couponId,order);
            }
            return ApiResult.success(order.getOrderNo());
        }else{
            map.put("msg","保存订单失败");
            return ApiResult.error(JSON.toJSONString(map));
        }

    }
    /**
     * 方法的功能描述: 修改订单状态
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/11 15:14
     *@modifier
     */
    @Override
    public BaseResult changeOrderStatus(String orderNo,Integer userId) {
        OrderPO order = orderMapper.getByOrderNo(orderNo);
        if (RStr.isNotEmpty(order)){
            if (userId.equals(order.getUserId())){
                if( order.getOrderState().equals(OrderConstant.OrderStateEnum.TO_PAY.getState())){
                    OrderPO newOrder = new OrderPO();
                    newOrder.setOrderId(order.getOrderId());
                    newOrder.setOrderState(OrderConstant.OrderStateEnum.TO_CONFIRM.getState());
                    newOrder.setVersion(order.getVersion());
                    newOrder.setPayTime(new Date());
                    orderMapper.updateOrder(newOrder);
                }
                return ApiResult.success();
            }else{
                return ApiResult.error("用户id不对");
            }
        }else{
            return ApiResult.error("订单号不存在");
        }
    }
    /**
     * 方法的功能描述: 获取用户订单列表
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/13 14:26
     *@modifier
     */
    @Override
    public BaseResult listCustomerOrder(Integer userId,Integer state,Integer pageIndex,Integer pageSize) {
        CustomerzSearchOrderBO customerzSearchOrder = new CustomerzSearchOrderBO(userId,pageIndex,pageSize,state);
        List<CustomerOrderBO> listOrder = orderMapper.listCustomerOrder(customerzSearchOrder);
        List<CustomerOrderVO> listOrderVo = new ArrayList<>();
        if (RStr.isNotEmpty(listOrder)){
            for (CustomerOrderBO customerOrderBO : listOrder) {
                CustomerOrderVO customerOrder = new CustomerOrderVO(customerOrderBO);
                listOrderVo.add(customerOrder);
            }
        }
        Integer count =  orderMapper.getCustomerOrderCount(customerzSearchOrder);
        return ApiResult.success(listOrderVo,count);
    }
    /**
     * 方法的功能描述: 删除订单 取消订单
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/13 15:41
     *@modifier
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult deleteOrder(String orderNo, Integer userId, Integer state) {
        OrderPO order = orderMapper.getByOrderNo(orderNo);
        boolean flag = false;
        String msg = "状态发生改变，请刷新";
        //删除
        if (state.equals(OrderConstant.OrderValidityStateEnum.TO_DELETE.getState()) && order.getValidityState().equals(OrderPO.VALIDITY_STATE_VALID)){
            if (order.getUserId().equals(userId) && !order.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState())){
                OrderPO newOrder = new OrderPO();
                newOrder.setOrderId(order.getOrderId());
                newOrder.setValidityState(OrderPO.VALIDITY_STATE_INVALID);
                newOrder.setVersion(order.getVersion());
                newOrder.setUpdateTime(new Date());
                int i=  orderMapper.updateOrder(newOrder);
                if(i == 1){
                    flag = true;
                    msg = "删除订单成功";
                }
            }
        }
        //取消
        if (state.equals(OrderConstant.OrderValidityStateEnum.TO_CANCEL.getState()) && order.getValidityState().equals(OrderPO.VALIDITY_STATE_VALID)){
            if (order.getUserId().equals(userId) && order.getCompleteState().equals(OrderConstant.OrderStateEnum.TO_PAY.getState())){
                OrderPO newOrder = new OrderPO();
                newOrder.setOrderId(order.getOrderId());
                newOrder.setCompleteState(OrderConstant.CompleteStateEnum.CANCELED.getState());
                newOrder.setVersion(order.getVersion());
                newOrder.setUpdateTime(new Date());
                int i=  orderMapper.updateOrder(newOrder);
                if(i == 1){
                    flag = true;
                    msg = "取消订单成功";
                }
            }
        }
        return flag ? ApiResult.success(msg) : ApiResult.error(msg);
    }
    /**
     * 方法的功能描述: 获取订单详情
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/13 17:25
     *@modifier
     */
    @Override
    public BaseResult getOrderDetail(String orderNo, Integer userId) {
        Long orderId=orderMapper.getOrderIdByNo(orderNo);
        UserOrderVo vo = orderMapper.getOrderDetail(orderId,orderNo,userId);
        if (vo.getValidityState().equals(OrderPO.VALIDITY_STATE_VALID)){
            if (orderComplainService.isHaveComplain(orderId)>0)
                vo.setIsHaveComplain(1);
            return ApiResult.success(vo);
        }else{
            return ApiResult.error("订单已删除");
        }

    }
    /**
     * 方法的功能描述: 面对面保存订单
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/15 15:26
     *@modifier
     */
    @Override
    public  Map<String,Object> saceFaceToFaceOrder(FaceToFaceOrderDTO faceToFaceOrderDTO) {
        Integer userId = faceToFaceOrderDTO.getUserId();
        Integer vendorId = faceToFaceOrderDTO.getVendorId();
        boolean flag = true;
        String msg = "成功";
        OrderPO order = new OrderPO();
        String totalMoney = faceToFaceOrderDTO.getTotalMoney();
        Long price = StringUtil.getLongByString("原价",totalMoney);
        order.setOriginalPrice(price);
        //优惠券相关判断s
        Integer couponId =  faceToFaceOrderDTO.getCouponId();
        if (RStr.isNotEmpty(couponId)){
            Map<String,Object> couponMap = decideCoupon(vendorId,userId,couponId,price);
            flag =   (Boolean) couponMap.get("flag");
            if (flag) {
                Long couponTotals = (long )couponMap.get("couponMoney");
                Long couponTotal =  StringUtil.getLongByString("优惠券价格",faceToFaceOrderDTO.getCouponTotal());
                if (couponTotals.equals(couponTotal)){
                    Long couponAmount = getCouponAmount(price,couponTotal);
                    //优惠金额
                    order.setCouponAmount(couponAmount);
                }else{
                    flag = false;
                    msg = "优惠券金额不对";
                }
            }else{
                msg = (String)couponMap.get("msg");
            }
            if (flag){
                Long payPrice = StringUtil.getLongByString("优惠后的价格",faceToFaceOrderDTO.getPayPrice());
                //判断优惠后的价格
                if(payPrice.equals(order.getOriginalPrice()-order.getCouponAmount())){
                    order.setPayPrice(payPrice);
                    //修改优惠券状态
                    couponInfoService.updateCouponInfo(CouponInfo.COST_STATUS_USED,couponId);
                }else{
                    flag = false;
                    msg = "优惠后价格不一致";
                }
            }
        }else{
            //没有优惠券
            order.setCouponAmount(0L);
            order.setPayPrice(price);
        }
        order.setOrderId(KeyGeneratorStrategy.match(KeyGeneratorEnum.ORDER_ID).generateKey());
        order.setOrderNo(KeyGeneratorStrategy.match(KeyGeneratorEnum.ORDER_NO).generateKey().toString());
        order.setUserId(userId);
        order.setVendorId(vendorId);
        order.setOrderState(OrderConstant.OrderStateEnum.TO_PAY.getState());
        order.setCompleteState(OrderConstant.CompleteStateEnum.UNFINISHED.getState());
        order.setRefundState(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState());
        order.setCommentState(OrderConstant.CommentStateEnum.TO_COMMENT.getState());
        //面对面付款
        order.setOrderSource(OrderPO.ORDER_SOURCE_FACE);
        order.setOrderChannel(faceToFaceOrderDTO.getOrderChannel());
        //下单渠道开始默认支付渠道
        order.setPayChannel(faceToFaceOrderDTO.getOrderChannel());
        order.setPayTypeId(faceToFaceOrderDTO.getPayTypeId());
        order.setPayTypeName(faceToFaceOrderDTO.getPayTypeName());
        order.setDeliveryType(OrderPO.DELIVER_TYPE_SINCE);
        order.setDeliveryAddress(faceToFaceOrderDTO.getDeliveryAddress());
        //暂时没有配送费用
        order.setDeliveryFee(0L);
        //配送时间 组装
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss-HH:ss");
        Date date = new Date();
        order.setDeliveryTime(sdf.format(date));
        order.setVersion(1);
        order.setCreateTime(date);
        order.setUpdateTime(date);
        order.setUpdateStaffId(userId);
        Map<String,Object> map = new HashMap<>();
        if (flag){
            int i = orderMapper.saveOrder(order);
            if( i != 1){
                flag = false;
                msg = "生成订单失败！";
            }
        }
        map.put("flag",flag);
        map.put("msg",msg);
        map.put("orderNo",order.getOrderNo());
        return map;
    }
    /**
     * 方法的功能描述: 30分钟未付款关闭订单
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/7 16:53
     *@modifier
     */
    @Override
    public void updateByUserOrderNoPay() {
        orderMapper.updateByUserOrderNoPay();
    }


    /**方法的功能描述: 获取商家商品详情*/
    private List<OrderGoodsItemBO> getOrderGoodsItem(Integer vendorId,List<Long> goodsList){
        List<OrderGoodsItemBO> orderGoodsItemBO = orderOtherMapper.getOrderGoodsItem(vendorId,goodsList);
        return orderGoodsItemBO;
    }
    /**方法的功能描述: 判断商品价格是否和数据库一致*/
    private Map<String,Object> decideGoods(OrderConfirmationDTO orderConfirmationDTO){
        List<Long> list = new ArrayList<>();
        List<OrderGoodsDTO> listGoods = orderConfirmationDTO.getListGoods();
        for (OrderGoodsDTO listGood : listGoods) {
            if (null !=listGood  && null !=listGood.getGoodsId() && !"".equals(listGood.getGoodsId()) ){
                list.add(listGood.getGoodsId());
            }
        }
        //获取所有商品信息
        List<OrderGoodsItemBO> orderGoodsItemBO =  getOrderGoodsItem(orderConfirmationDTO.getVendorId(),list);
        List<OrderGoodsDTO> listOrderGoods = orderConfirmationDTO.getListGoods();
        boolean flag = true;
        String msg = "";
        String downGoods = "";
        Long total = 0L;
        Map<String,Object> map = new HashMap<>();
        if (orderGoodsItemBO.size() != listOrderGoods.size()) {
            flag = false;
            msg = "有商品已下架，请刷新！";
            for (Long aLong : list ) {
                boolean goodsFlag = false;
                for (OrderGoodsItemBO goodsItemBO : orderGoodsItemBO) {
                    if (aLong.equals(goodsItemBO.getGoodsId())){
                        goodsFlag = true;
                        break;
                    }
                }
                if (!goodsFlag){
                    downGoods+=","+aLong;
                }
            }

            downGoods = "".equals(downGoods) ? "": downGoods.substring(1,downGoods.length());
        }
        //一个订单没有多少个商品 可以用双循环
        if (flag){
            for (OrderGoodsDTO orderGoods : listOrderGoods) {
                for (OrderGoodsItemBO goods : orderGoodsItemBO) {
                    //商品id是否一样
                    if (orderGoods.getGoodsId().equals(goods.getGoodsId())){
                        //暂时不做每个商品单独价格判断
                        //根据数量等到数据库的总价
                        total+=goods.getOrderGoodsItemPO().getOriginalPrice()*orderGoods.getSum();
                        //商品数量
                        goods.getOrderGoodsItemPO().setGoodsNum(orderGoods.getSum());
                        goods.getOrderGoodsItemPO().setGoodsId(orderGoods.getGoodsId());
                        //目前没有针对单商品优惠的情况 故存放商品原价
                        goods.getOrderGoodsItemPO().setPayPrice(goods.getOrderGoodsItemPO().getOriginalPrice());
                        goods.getOrderGoodsItemPO().setCouponAmount(0L);
                        String str = "";
                        List<GoodsImageBO> listImage = goods.getGoodsImageBOS();
                        for (GoodsImageBO goodsImageBO : listImage) {
                            str+="^"+goodsImageBO.getGoodsImage();
                        }
                        //保存图片
                        goods.getOrderGoodsItemPO().setGoodsPhotos(str.substring(1,str.length()));
                    }
                }
            }
        }

        if (flag && !total.equals(orderConfirmationDTO.getPrice())){
            flag = false;
            msg = "商品价格变动，请重新下单";
        }
        //商品数据库原价
        map.put("total",total);
        map.put("flag",flag);
        map.put("msg",msg);
        map.put("listGoods",orderGoodsItemBO);
        map.put("downGoods",downGoods);
        return map;
    }
    /**方法的功能描述: 保存商品信息*/
    private void saveOrderGoodsItem(   List<OrderGoodsItemBO> orderGoodsItemBO,OrderPO orderPO ){
        for (OrderGoodsItemBO goodsItemBO : orderGoodsItemBO) {
            OrderGoodsItemPO orderGoodsItemPO = goodsItemBO.getOrderGoodsItemPO();
            orderGoodsItemPO.setOrderId(orderPO.getOrderId());
            orderGoodsItemPOMapper.saveOrerGoodsItem(orderGoodsItemPO);
            List<OrderAttrPO> listAttr = goodsItemBO.getOrderAttrPO();
            for (OrderAttrPO orderAttrPO : listAttr) {
                orderAttrPO.setOrderId(orderPO.getOrderId());
                orderAttrPO.setGoodsId(goodsItemBO.getGoodsId());
                orderAttrPOMapper.saveOrderAttr(orderAttrPO);
            }
        }
    }
    /**
     * 方法的功能描述: 保存订单收获地址
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/10 17:32
     *@modifier
     */
    private void saveOrderAddress(OrderConfirmationDTO orderConfirmationDTO,OrderPO orderPO) {
        Map<String,Object> map = orderOtherMapper.getVendorDispatch(orderConfirmationDTO.getVendorId());
        UserAddressPO userAddressPO =  userAddressMapper.getUserAddress(orderPO.getUserId(),orderConfirmationDTO.getUserAddressId());
        OrderDispatchPO orderDispatchPO = new OrderDispatchPO();
        orderDispatchPO.setOrderId(orderPO.getOrderId());
        orderDispatchPO.setDispatchName(null != map.get("dispatchName") && !"".equals( map.get("dispatchName")) ? map.get("dispatchName").toString():"");
        orderDispatchPO.setDispatchType(null != map.get("dispatchType") && !"".equals( map.get("dispatchType")) ? new Integer(map.get("dispatchType").toString()):null);
        orderDispatchPO.setDispatchProvider(null != map.get("provider") && !"".equals( map.get("provider")) ? map.get("provider").toString():"");
        orderDispatchPO.setDispatchDetail(null != map.get("detail") && !"".equals( map.get("detail")) ? map.get("detail").toString():"");
        orderDispatchPO.setDispatchFee(0L);
        if (null != userAddressPO){
            orderDispatchPO.setContactName(userAddressPO.getContactName());
            orderDispatchPO.setContactPhone(userAddressPO.getContactPhone());
            orderDispatchPO.setAddressName(userAddressPO.getAddressName());
            orderDispatchPO.setAddressDesc(userAddressPO.getAddressDesc());
            orderDispatchPO.setInputAddress(userAddressPO.getInputAddress());
            orderDispatchPO.setStructuredAddress(userAddressPO.getFormattedAddress());
            orderDispatchPO.setLongitude(userAddressPO.getLongitude());
            orderDispatchPO.setLatitude(userAddressPO.getLatitude());
            orderDispatchPO.setCityCode(userAddressPO.getCityCode());
            orderDispatchPO.setadCode(userAddressPO.getadCode());
            orderDispatchPO.setTownCode(userAddressPO.getTownCode());
            orderDispatchPO.setUpdateTime(new Date());
            orderDispatchPO.setUpdateStaffId(orderPO.getUserId());
        }
        orderDispatchPOMapper.saveOrderDispatch(orderDispatchPO);
    }
    /**方法的功能描述:  优惠多少钱 单位L*/
    private  Long  getCouponAmount(Long price,Long couponMoney){
        if (price - couponMoney <= 0) {
            return price - 1L;
        } else {
            return couponMoney;
        }
    }
    /**方法的功能描述: 判断优惠券是否可以使用*/
    private   Map<String,Object> decideCoupon(Integer vendorId,Integer userId,Integer couponId,Long price) {
        CouponInfo couponInfo = couponInfoService.couponDetail(couponId);
        boolean flag = true;
        String msg = "";
        Map<String,Object> map = new HashMap();
        if (flag){
            if (null == couponInfo){
                flag = false;
                msg = "没有该优惠券!";
            }else{
                map.put("couponMoney",new Double(couponInfo.getMoney().doubleValue()*100).longValue());
            }
        }
        //是否使用用户自己的优惠券
        if(flag){
            if (userId != null && !userId.equals(couponInfo.getUserId())) {
                flag = false;
                msg = "不能使用他人优惠券!";
            }
        }
        //是否使用其他商家优惠券
        if(flag){
            if (vendorId!= null && !(vendorId.equals(couponInfo.getForeignKey()) || couponInfo.getForeignKey().equals(0))) {
                flag = false;
                msg = "不能使用其他商家优惠券!";
            }
        }
        //是否已经使用
        if (flag){
            if( CouponInfo.COST_STATUS_USED.equals(couponInfo.getCostStatus())){
                flag = false;
                msg = "优惠券已经使用！";
            }
        }
        //有效时间 和 价格
        if (flag){
            SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dataS = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            try {
                date = dataS.parse(dataS.format(date));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            String beginTimeStr = couponInfo.getBeginTime();
            String endTimeStr = couponInfo.getEndTime();
            msg = "优惠券使用时间有误";
            //无条件
            if (null != couponInfo.getCondition() && !"".equals(couponInfo.getCondition())) {
                try {
                    if (SDF.parse(beginTimeStr).getTime() <= date.getTime() && date.getTime() <= SDF.parse(endTimeStr).getTime()) {
                        msg = "优惠券可以使用";
                    }else{
                        flag = false;
                        msg = "优惠券使用时间有误";
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //满多少
            } else {
                if (price >= (couponInfo.getCostLimitMoney().intValue()*100)) {
                    try {
                        if (SDF.parse(beginTimeStr).getTime() <= date.getTime() && date.getTime() <= SDF.parse(endTimeStr).getTime()) {
                            msg = "优惠券可以使用";
                        }else{
                            flag = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    flag = false;
                    msg = "优惠券不满足使用条件！";
                }
            }
        }
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    /**
     * 方法的功能描述:获取所有的优惠券
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/11 10:17
     *@modifier
     */
    private  List<CouponVO> listCouponVo( List<CouponBO> listCouponInfo,Long totalMoney){
        List<CouponVO> listCouponInfoV = new ArrayList<>();
        if (null != listCouponInfo && listCouponInfo.size() != 0) {
            for (CouponBO couponBO : listCouponInfo) {
                CouponVO coupon = new CouponVO();
                //判断是否无条件使用
                if (null == couponBO.getCondition()|| "".equals(couponBO.getCondition())) {
                    coupon.setDescribe("满" + mathRoundDouble(couponBO.getCostLimitMoney()) + "元减" + mathRoundDouble(couponBO.getMoney()) + "元");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
                    Long long1 = couponBO.getStartDate().getTime();
                    Long long2 = System.currentTimeMillis();
                    if (couponBO.getCostLimitMoney()*100 <= totalMoney &&  long1 <= long2){
                        coupon.setFlag("Y");
                    }else{
                        coupon.setFlag("N");
                    }
                } else {
                    coupon.setFlag("Y");
                    coupon.setDescribe("无门槛直减" + mathRoundDouble(couponBO.getMoney()) + "元");
                }
                coupon.setCouponName(couponBO.getCouponName());
                coupon.setEndDate(couponBO.getEndDate());
                coupon.setStartDate(couponBO.getStartDate());
                coupon.setCouponId(couponBO.getCouponId());
                coupon.setMoney(couponBO.getMoney());
                listCouponInfoV.add(coupon);
            }
        }
        return listCouponInfoV;
    }

    /**
     * 方法的功能描述: 方法的功能描述: 保存优惠券使用记录
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/11 10:17
     *@modifier
     */
    private int saveCouponRecord(Integer couponId,OrderPO order){
        int i = 0;
        if (null != couponId && !"".equals(couponId)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            CouponRecord couponRecord = new CouponRecord();
            couponRecord.setUserId(order.getUserId().toString());
            couponRecord.setCouponId(couponId.toString());
            couponRecord.setCreateBy("system");
            couponRecord.setCreateDate(sdf.format(new Date()));
            couponRecord.setUpdateBy("system");
            couponRecord.setUpdateDate(sdf.format(new Date()));
            couponRecord.setTotalMoney( new BigDecimal(order.getOriginalPrice()/100));
            CouponInfo couponInfo = couponInfoService.couponDetail(couponId);
            couponRecord.setMoney(new BigDecimal(StringUtil.changeF2Y(order.getCouponAmount().toString())));
            couponRecord.setTitle(couponInfo.getTitle());
            couponRecord.setOrderNo(order.getOrderId().toString());
            couponRecord.setVendorInfoCouponId(couponInfo.getExpandId());
            i = couponRecordMapper.insertCouponRecord(couponRecord);
        }
        return i;
    }
    /*** 如没有小数去除*/
    private String mathRoundDouble(Object o) {
        Double d = Double.parseDouble(o.toString());
        if (Math.round(d) - d == 0D) {
            return String.valueOf(Math.round(d));
        }
        return String.valueOf(d);
    }
    /**
     * 方法的功能描述: 封装状态判断
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/5 14:18
     *@modifier
     */
    private Map<String,Integer> getMapState(Integer state){
        OrderStateBO orderState =  OrderStateHelp.transOrderStateBO(state);
        Map<String,Integer> map = new HashMap<>();
        if (RStr.isNotEmpty(orderState.getCommentState())){
            map.put("comment_state",orderState.getCommentState());
        }
        if (RStr.isNotEmpty(orderState.getCompleteState())){
            map.put("complete_state",orderState.getCompleteState());
        }
        if (RStr.isNotEmpty(orderState.getOrderState())){
            map.put("order_state",orderState.getOrderState());
        }
        if (RStr.isNotEmpty(orderState.getRefundState())){
            map.put("refund_state",orderState.getRefundState());
        }
        return map;
    }


    @Override
    public List<OrderPO> getSettlementOrder(Integer settlementId) {
        return orderMapper.getSettlementOrder(settlementId);
    }
}
