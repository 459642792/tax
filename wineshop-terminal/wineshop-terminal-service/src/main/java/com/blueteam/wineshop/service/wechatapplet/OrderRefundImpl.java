package com.blueteam.wineshop.service.wechatapplet;

import com.alibaba.fastjson.JSON;
import com.blueteam.base.constant.OrderConstant;
import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.help.generator.KeyGeneratorEnum;
import com.blueteam.base.help.generator.KeyGeneratorStrategy;
import com.blueteam.base.help.order.OrderStateHelp;
import com.blueteam.base.lang.RStr;
import com.blueteam.base.util.StringUtil;
import com.blueteam.base.util.weixin.RefundBO;
import com.blueteam.base.util.weixin.RefundQueryBO;
import com.blueteam.base.util.weixin.UUIDHexGenerator;
import com.blueteam.base.util.weixin.WeiXinUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.wechatapplet.RefundDTO;
import com.blueteam.entity.po.*;
import com.blueteam.wineshop.mapper.*;

import com.blueteam.wineshop.service.MessageSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.interfaces.RSAKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单退款
 *
 * @author xiaojiang
 * @create 2018-01-11  16:04
 */
@Service
public class OrderRefundImpl implements  OrderRefundService {
    /** 微信成功返回标示*/
    private static final  String CODE = "SUCCESS";
    private static final  String MSG = "SYSTEM ERROR";

    /**
     * 订单相关
     */
    @Autowired
    private OrderMapper orderMapper;
    /**
     * 记忆记录明细
     */
    @Autowired
    private TradeRecordMapper tradeRecordMapper;
    //退款结果
    @Autowired
    private RefundResultMapper refundResultMapper;
    /**
     * 申请退款记录
     */
    @Autowired
    private ApplyRefundRecordMapper applyRefundRecordMapper;
    @Resource
    private MessageSubService messageSubService;
//
//    /**
//     * 用户优惠券相关
//     */
//    @Autowired
//    private CouponInfoService couponInfoService;

    /**
     * 用户订单其他相关 （如：商品详情，订单类型，订单支付类型等）
     */
    @Autowired
    private OrderOtherMapper orderOtherMapper;
    /**
     * 方法的功能描述:s申请退款
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/11 16:18
     *@modifier
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult applyToRefund(RefundDTO refundDTO, Integer userId) {
        OrderPO order =  orderMapper.getByOrderNo(refundDTO.getOrderNo());
        if(RStr.isNotEmpty(order)){
            if (order.getUserId().equals(userId)){
                Integer state = order.getOrderState();
                //未完成  未退款 直接退款
                if(order.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                        order.getRefundState().equals(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState()) &&
                        order.getValidityState().equals(OrderPO.VALIDITY_STATE_VALID)){
                    // 待接单 用户可以直接退款
                    if (state.equals(OrderConstant.OrderStateEnum.TO_PROMISE.getState()) || state.equals(OrderConstant.OrderStateEnum.TO_RECEIVE.getState())){
                        Map<String,Object > map = userRefund(order,refundDTO,state);
                        boolean flag = (boolean)map.get("flag") ;
                        String msg = map.get("msg").toString() ;
                        if (flag){
                            return ApiResult.success(msg);
                        }else{
                            return ApiResult.error(msg);
                        }
                    }else if(state.equals(OrderConstant.OrderStateEnum.TO_PAY.getState()) || state.equals(OrderConstant.OrderStateEnum.TO_CONFIRM.getState()) ){
                        return ApiResult.error("对不起！该订单没有支付不能退款！");
                    }else{
                        return ApiResult.error("该订单已收货，不能申请退款！");
                    }
                }else{
                    if(order.getValidityState().equals(OrderPO.VALIDITY_STATE_INVALID)){
                        return  ApiResult.error("对不起该订单以删除！");
                    }else if(!order.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState())){
                        return ApiResult.error("对不起该订单以完成！");
                    }else{
                        return ApiResult.error("对不起该订单以退款！");
                    }
                }
            }else{
                return ApiResult.error("对不起该订单用户错误！");
            }
        }else{
            return ApiResult.error("对不起没有该订单！");
        }
    }
    /**
     * 方法的功能描述: 取消退款
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/12 15:54
     *@modifier
     */
    @Override
    public BaseResult cancelRefund(String orderNo, Integer userId) {
        OrderPO order =  orderMapper.getByOrderNo(orderNo);
        if(order.getRefundState().equals(OrderConstant.RefundStateEnum.APPLY_REFUND_ING.getState())){
            OrderPO newOrder = new OrderPO();
            newOrder.setOrderId(order.getOrderId());
            newOrder.setVersion(order.getVersion());
            newOrder.setRefundState(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState());
            int i = orderMapper.updateOrder(newOrder);
            if(i == 1){
                saveApplyRefundRecord(userId,order.getOrderId(),order.getPayPrice(),null,null,ApplyRefundRecordPO.PROCESS_STATUS_CANCELED);
                return ApiResult.success("取消退款成功");
            }else{
                return ApiResult.success("状态发生改变请重新刷新");
            }
        }else{
            return ApiResult.success("状态发生改变请重新刷新");
        }

    }

    /**
     * 方法的功能描述: 用户直接退款
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/12 13:52
     *@modifier
     */
    private Map<String,Object > userRefund(OrderPO order,RefundDTO refundDTO,Integer code){
        Map<String,Object>  map = new HashMap<>();
        boolean flag = false;
        String msg = "订单状态发生改变，请刷新";
        OrderPO newOrder = new OrderPO();
        newOrder.setOrderId(order.getOrderId());
        newOrder.setVersion(order.getVersion());
        newOrder.setUpdateTime(new Date());
        //直接退款
        if (code.equals(OrderConstant.OrderStateEnum.TO_PROMISE.getState())){
            newOrder.setRefundState(OrderConstant.RefundStateEnum.REFUND_ING.getState());
            int i = orderMapper.updateOrder(newOrder);
            order.setVersion(newOrder.getVersion()+1);
            if(i == 1){
                //记录申请退款明细
                saveApplyRefundRecord(0,order.getOrderId(),order.getPayPrice(),refundDTO.getCode(),refundDTO.getRefundRemark(),ApplyRefundRecordPO.PROCESS_STATUS_UNTREATED);
                flag = payRefund(order,0,refundDTO.getCode(),refundDTO.getRefundRemark());
                if(flag){
                    msg = "退款成功";
                }else{
                    msg = "退款失败，请联系客服";
                }
            }
        }else{
            newOrder.setRefundState(OrderConstant.RefundStateEnum.APPLY_REFUND_ING.getState());
            int i = orderMapper.updateOrder(newOrder);
            if(i == 1){
                //发送消息
                Map<String,Object> messageMap = new HashMap<>();
                messageMap.put("orderId",order.getOrderNo());
                messageSubService.sendVendormessage(2,order.getVendorId(),messageMap);
                saveApplyRefundRecord(null,order.getOrderId(),order.getPayPrice(),refundDTO.getCode(),refundDTO.getRefundRemark(),ApplyRefundRecordPO.PROCESS_STATUS_UNTREATED);
                flag = true;
                msg = "申请退款成功，请等待";
            }
        }
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }
    /**
     * 方法的功能描述:
     *@methodName payRefund
     * @param: order 订单
     * @param: refundUserId 退款人id
     * @param: code 退款理由code
     * @param: refundRemark 退款原因
     *@return boolean
     *@since 1.4.0
     *@author xiaojiang 2018/1/12 14:33
     *@modifier
     */
    private boolean payRefund(OrderPO order,Integer refundUserId,Integer code,String refundRemark){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Long refundId = KeyGeneratorStrategy.match(KeyGeneratorEnum.REFUND_NO).generateKey();
        Map<String, String> sParaTemp = new HashMap<>();
        sParaTemp.put("appid", WeiXinUtil.APPID);
        sParaTemp.put("mch_id", WeiXinUtil.MCH_ID);
        sParaTemp.put("nonce_str", UUIDHexGenerator.generate());
        sParaTemp.put("sign_type", WeiXinUtil.TRADE_TYPE);
        sParaTemp.put("out_trade_no", order.getOrderId().toString());
        sParaTemp.put("out_refund_no", refundId.toString());
        sParaTemp.put("total_fee",order.getPayPrice().toString());
        sParaTemp.put("refund_fee",order.getPayPrice().toString());
        sParaTemp.put("refund_desc", OrderConstant.RefundRemarkEnum.match(code).getDescription());
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = WeiXinUtil.paraFilter(sParaTemp);
        String preStr = WeiXinUtil.createLinkString(sPara);
        String key = "&key=" + WeiXinUtil.KEY;
        //MD5运算生成签名
        //密钥出来变大写
        String mySign = WeiXinUtil.sign(preStr, key, "utf-8").toUpperCase();
        RefundBO refund  = new RefundBO();
        refund.setAppid(sParaTemp.get("appid"));
        refund.setMch_id(sParaTemp.get("mch_id"));
        refund.setNonce_str(sParaTemp.get("nonce_str"));
        refund.setOut_refund_no(sParaTemp.get("out_refund_no"));
        refund.setOut_trade_no(sParaTemp.get("out_trade_no"));
        refund.setRefund_fee(sParaTemp.get("refund_fee"));
        refund.setTotal_fee(sParaTemp.get("total_fee"));
        refund.setRefund_desc(sParaTemp.get("refund_desc"));
        refund.setSign_type(sParaTemp.get("sign_type"));
        refund.setSign(mySign);
        //保存退款详情
        boolean flag = false;
        try {
            String param = WeiXinUtil.convertToXml(refund);
            saveRefundTradeRecord(TradeRecordPO.TRADE_TYP_REFUND,order.getOrderId(),refundId,param);
            Map<String, String> map = WeiXinUtil.refundHttpRequest(WeiXinUtil.ORDER_REFUND_URL,param);
            // 返回信息
            //返回状态码
            String returnCode = map.get("return_code").toUpperCase();
            //返回信息
            String returnMsg = map.get("return_msg");
            //保存退款详情
            updateRefundTradeRecord(order.getOrderId(),null,map);
            //同意退款
            saveApplyRefundRecord(refundUserId,order.getOrderId(),order.getPayPrice(),code,refundRemark,ApplyRefundRecordPO.PROCESS_STATUS_PROCESSED);
            if (CODE.equals(returnCode)){
                String resultCode = map.get("result_code").toUpperCase();
                //成功与否
                if (CODE.equals(resultCode)){
                    //退款结果
                    //修改订单状态 修改优惠券状态
                    saveRefundOrder(order,1);
                    saveRefundResult(order.getOrderId(),RefundResultPO.RESULT_STATE_SUCCESS,order.getPayPrice());
                    flag = true;
                }
            }else if(returnMsg.equals(MSG)){
                flag = refundquery(order);
            }
            if (!flag){
                //退款结果
                //修改订单状态 修改优惠券状态
                saveRefundOrder(order,0);
                saveRefundResult(order.getOrderId(),RefundResultPO.RESULT_STATE_FAIL,order.getPayPrice());
            }
            //发送退款结果通知
            messageSubService.senUserMessage(1,order);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return flag;
    }

    /**
     * 方法的功能描述:修改记录
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/11 11:04
     *@modifier
     */
    private void updateRefundTradeRecord(Long orderId,Long refundiD,Map<String, String> map) {
        Integer id =  tradeRecordMapper.getTradeRecord(orderId);
        TradeRecordPO tradeRecord = new TradeRecordPO();
        tradeRecord.setId(id);
        tradeRecord.setRefundId(refundiD);
        tradeRecord.setTradeEndRecord(JSON.toJSONString(map));
        String code =null ==  map.get("result_code") || "".equals(map.get("result_code")) ? map.get("return_code"):map.get("result_code");
        tradeRecord.setResultCode(map.get("return_code"));
        tradeRecord.setTradeEndTime(new Date());
        tradeRecordMapper.updateTradeRecord(tradeRecord);
    }
    /**
     * 方法的功能描述: 保存订单发起支付记录
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/11 17:36
     *@modifier
     */
    private void saveRefundTradeRecord(Integer type,Long orderId,Long refundId,String startRecord) {
        TradeRecordPO tradeRecord = new TradeRecordPO();
        tradeRecord.setTradeType(type);
        tradeRecord.setOrderId(orderId);
        tradeRecord.setRefundId(refundId);
        tradeRecord.setTradeStartTime(new Date());
        tradeRecord.setTradeStartRecord(JSON.toJSONString(startRecord));
        tradeRecordMapper.saveTradeRecord(tradeRecord);
    }
    /**
     * 方法的功能描述: 修改保存退款申请单
     *@methodName saveApplyRefundRecord
     * @param: userId 处理人id
     * @param: orderId 订单id
     * @param: code 申请理由code
     * @param: refundRemark 申请原因
     * @param: processStatus 结果
     *@return void
     *@since 1.4.0
     *@author xiaojiang 2018/1/12 14:13
     *@modifier
     */
    private int saveApplyRefundRecord(Integer userId,Long orderId,Long refundFee,Integer code,String refundRemark,Integer processStatus){
        ApplyRefundRecordPO applyRefundRecordPO = new ApplyRefundRecordPO();
        applyRefundRecordPO.setOrderId(orderId);
        applyRefundRecordPO.setProcessStatus(processStatus);
        if(!processStatus.equals(ApplyRefundRecordPO.PROCESS_STATUS_UNTREATED)){
            applyRefundRecordPO.setProcessTime(new Date());
            applyRefundRecordPO.setProcessBy(userId);
            return applyRefundRecordMapper.updateApplyRefundRecord(applyRefundRecordPO);
        }else{
            applyRefundRecordPO.setRefundFee(refundFee);
            applyRefundRecordPO.setRefundReasonCode(code);
            applyRefundRecordPO.setRefundReasonDesc(OrderConstant.RefundRemarkEnum.match(code).getDescription());
            applyRefundRecordPO.setRefundRemark(refundRemark);
            applyRefundRecordPO.setCreateTime(new Date());
            return applyRefundRecordMapper.saveApplyRefundRecord(applyRefundRecordPO);
        }
    }
    private void saveRefundResult(Long orderId,Integer state,Long refundFee){
        RefundResultPO refundResultPO = new RefundResultPO();
        refundResultPO.setResultState(state);
        refundResultPO.setOrderId(orderId);
        refundResultPO.setRefundFee(refundFee);
        refundResultPO.setRefundType(RefundResultPO.REFUND_TYPE_SYSTEM);
        refundResultPO.setRefundChannel(RefundResultPO.REFUND_CHANNEL_WECHAT);
        refundResultPO.setCreateTime(new Date());
        int i =  refundResultMapper.saveRefundResult(refundResultPO);
    }
    /**
     * 方法的功能描述: 修改订单状态 修改优惠券状态
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/12 15:11
     *@modifier
     */
    private void saveRefundOrder(OrderPO order,Integer type){
        OrderPO newOrder = new OrderPO();
        //待接单 订单完成状态 修改为以取消
        if (order.getOrderState().equals(OrderConstant.OrderStateEnum.TO_PROMISE.getState())){
            newOrder.setCompleteState(OrderConstant.CompleteStateEnum.CANCELED.getState());
            //待收货 订单完成状态 修改为以完成
        }else{
            newOrder.setCompleteState(OrderConstant.CompleteStateEnum.FINISHED.getState());
        }
        //成功修改订单
        if (type.equals(1)){
            newOrder.setRefundState(OrderConstant.RefundStateEnum.REFUND_SUCCESS.getState());
//            Integer couponId = orderOtherMapper.getByCouponId(order.getOrderId());
//            if(RStr.isNotEmpty(couponId)){
//                //修改优惠券状态
//                couponInfoService.updateCouponInfo(CouponInfo.COST_STATUS_UNUSED,couponId);
//            }
        }else{
            //退款失败
            newOrder.setRefundState(OrderConstant.RefundStateEnum.REFUND_FAIL.getState());
        }
        newOrder.setCompleteTime(new Date());
        newOrder.setOrderId(order.getOrderId());
        newOrder.setVersion(order.getVersion());
        orderMapper.updateOrder(newOrder);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int refundOrder(Long orderId,Integer userId) {
        OrderPO po=orderMapper.getByOrderId(orderId);
        if (userId!=po.getUserId())return 0;
        po.setRefundState(2);
        int res=orderMapper.updateOrder(po);
        ApplyRefundRecordPO applyR = applyRefundRecordMapper.getApplyRefundRecord(orderId,ApplyRefundRecordPO.PROCESS_STATUS_UNTREATED);
        if (res>0){
            po.setVersion(po.getVersion()+1);
            if(payRefund(po,userId,applyR.getRefundReasonCode(),applyR.getRefundRemark())){
                return 1;
            }
        }
        return 0;
    }
    /**
     * 方法的功能描述: 60分钟未接单退款
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/9 11:07
     *@modifier
     */
    @Override
    public void vendorOrderRufund() {
        List<OrderPO> list = orderMapper.listOrderByVendorNoReceiving();
        if (RStr.isNotEmpty(list) && list.size() >0){
            for (OrderPO orderPO : list) {
                vendorRufund(orderPO,1);
            }
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void vendorRufund(OrderPO order,Integer state){
        OrderPO newOrder = new OrderPO();
        newOrder.setOrderId(order.getOrderId());
        newOrder.setVersion(order.getVersion());
        newOrder.setUpdateTime(new Date());
        newOrder.setRefundState(OrderConstant.RefundStateEnum.REFUND_ING.getState());
        int i = orderMapper.updateOrder(newOrder);
        if(i == 1){
            order.setVersion(newOrder.getVersion()+1);
            if (state.equals(1)){
                int refund = saveApplyRefundRecord(null,order.getOrderId(),order.getPayPrice(), OrderConstant.RefundRemarkEnum.OTHER.getCode(),"商家超时未接单",ApplyRefundRecordPO.PROCESS_STATUS_UNTREATED);
                if (refund != 1){
                    throw new BusinessException("保存退款申请异常");
                }
                payRefund(order,0,OrderConstant.RefundRemarkEnum.OTHER.getCode(),"商家超时未接单");
            }else{
                ApplyRefundRecordPO applyR = applyRefundRecordMapper.getApplyRefundRecord(order.getOrderId(),ApplyRefundRecordPO.PROCESS_STATUS_UNTREATED);
                payRefund(order,order.getUserId(),applyR.getRefundReasonCode(),applyR.getRefundRemark());
            }

        }
    }
    /**
     * 方法的功能描述:查询退款中状态
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/9 11:07
     *@modifier
     */
    @Override
    public void updateOrderByRrundIng() {
        List<OrderPO> list = orderMapper.listOrderByRrundIng();
        if (RStr.isNotEmpty(list) && list.size() >0){
            for (OrderPO orderPO : list) {
                refundquery(orderPO);
            }
        }
    }
    /**
     * 方法的功能描述:3天为接单退款
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/9 11:30
     *@modifier
     */
    @Override
    public void updateOrderByVendorNoRefund() {
        List<OrderPO> list = orderMapper.listOrderByVendorNoRefund();
        if (RStr.isNotEmpty(list) && list.size() >0){
            for (OrderPO orderPO : list) {
                vendorRufund(orderPO,2);
            }
        }
    }
    /**
     * 方法的功能描述: 查询退款状态
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/24 14:10
     *@modifier
     */
    public boolean refundquery(OrderPO order){
        boolean flag = false;
        RefundQueryBO refundQueryBO = new RefundQueryBO();
        Map<String, String> refundM = new HashMap<>();
        refundQueryBO.setAppid(WeiXinUtil.APPID);
        refundQueryBO.setMch_id(WeiXinUtil.MCH_ID);
        refundQueryBO.setNonce_str(UUIDHexGenerator.generate());
        refundQueryBO.setOut_trade_no(order.getOrderId().toString());
        refundM.put("nonce_str",refundQueryBO.getNonce_str());
        refundM.put("out_trade_no",refundQueryBO.getOut_trade_no());
        refundM.put("mch_id",refundQueryBO.getMch_id());
        refundM.put("appid",refundQueryBO.getAppid());
        // 除去数组中的空值和签名参数
        Map<String, String> rm = WeiXinUtil.paraFilter(refundM);
        String rStr = WeiXinUtil.createLinkString(rm);
        String key = "&key=" + WeiXinUtil.KEY;
        //MD5运算生成签名
        //密钥出来变大写
        String rSign = WeiXinUtil.sign(rStr, key, "utf-8").toUpperCase();
        refundQueryBO.setSign(rSign);
        //得到类的xml
        String paramQuery = WeiXinUtil.convertToXml(refundQueryBO);
        saveRefundTradeRecord(TradeRecordPO.TRADE_TYP_REFUND,order.getOrderId(),null,paramQuery);
        String resultQ = WeiXinUtil.httpRequest(WeiXinUtil.ORDER_REFUND_QUERY_URL, "POST", paramQuery);
        // 将解析结果存储在HashMap中
        Map<String, String> mapQ = WeiXinUtil.readStringXmlOut(resultQ);
        String refundId = mapQ.get("out_refund_no_$n");
        Long refundIdL =refundId == null || refundId.equals("") ? null : Long.parseLong(refundId);
        updateRefundTradeRecord(order.getOrderId(),refundIdL,mapQ);
        // 返回信息
        //返回状态码
        String returnCodeQ = mapQ.get("return_code").toUpperCase();
        if (CODE.equals(returnCodeQ)){
            String resultCodeQ = mapQ.get("result_code").toUpperCase();
            //成功与否o
            if (CODE.equals(resultCodeQ)){
                flag = true;
                refundStatus : for (Map.Entry<String, String> entry : mapQ.entrySet()) {
                    if (entry.getKey().startsWith("refund_status_")){
                        String value = entry.getValue();
                        switch (value){
                            //—退款成功
                            case "SUCCESS":
                                saveRefundOrder(order,1);
                                saveRefundResult(order.getOrderId(),RefundResultPO.RESULT_STATE_SUCCESS,order.getPayPrice());
                                break ;
                            //退款关闭。
                            case "REFUNDCLOSE":
                                //退款异常
                            case "CHANGE":
                                saveRefundOrder(order,0);
                                saveRefundResult(order.getOrderId(),RefundResultPO.RESULT_STATE_FAIL,order.getPayPrice());
                                break ;
                            default:
                                break;
                        }
                        break refundStatus;
                    }
                }
            }
        }
        return flag;
    }
}
