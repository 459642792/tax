package com.blueteam.wineshop.service;

import com.blueteam.base.constant.Constants;
import com.blueteam.base.constant.OrderConstant;
import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.help.order.OrderStateHelp;
import com.blueteam.base.lang.RDbTrans;
import com.blueteam.base.lang.RStr;
import com.blueteam.entity.bo.*;
import com.blueteam.entity.dto.ManualRefundDTO;
import com.blueteam.entity.dto.OrderListSearchDTO;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.entity.po.OrderPO;
import com.blueteam.entity.po.RefundResultPO;
import com.blueteam.entity.po.StatePO;
import com.blueteam.entity.vo.AdminGoodsListAttrVO;
import com.blueteam.entity.vo.AdminOrderDetailGoodsVO;
import com.blueteam.entity.vo.AdminOrderDetailVO;
import com.blueteam.entity.vo.AdminOrderListVO;
import com.blueteam.wineshop.mapper.OrderAdminMapper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.blueteam.entity.po.RefundResultPO.REFUND_TYPE_REFUND;
import static com.blueteam.entity.po.RefundResultPO.RESULT_STATE_SUCCESS;
import static com.blueteam.entity.po.StatePO.ORDER_STATE_TO_CANCEL;
import static com.blueteam.entity.po.StatePO.STATE_TYPE_ORDER;
import static com.blueteam.entity.vo.AdminOrderDetailVO.SHOW_MANUAL_REFUND;

/**
 * Created by  NastyNas on 2018/1/8.
 */
@Service
public class OrderAdminServiceImpl implements OrderAdminService {

    private static final Logger logger = LoggerFactory.getLogger(OrderAdminServiceImpl.class);

    @Autowired
    OrderAdminMapper orderAdminMapper;

    @Autowired
    HttpServletRequest request;


    @Override
    public PageResult<List<AdminOrderListVO>> listOrderInfo(OrderListSearchDTO orderListSearchDTO) {
        //封装请求
        wrapSearchDTO(orderListSearchDTO);
        //未分页订单总数
        Integer count = orderAdminMapper.countOrderList(orderListSearchDTO);
        //分页订单列表
        List<AdminOrderBO> boList = orderAdminMapper.listOrderInfo(orderListSearchDTO);
        //封装返回
        List<AdminOrderListVO> voList = wrapOrderVOList(boList);
        PageResult pageResult = new PageResult();
        pageResult.setCount(count);
        pageResult.setList(voList);
        return pageResult;
    }

    @Override
    public AdminOrderDetailVO getOrderDetail(String orderIdString) {
        //获取订单id @TODO 通过解密算法获取
        Long orderId = Long.parseLong(orderIdString);
        //获取订单基本信息
        AdminOrderDetailBO orderDetailBO = orderAdminMapper.getOrderDetail(orderId);
        //获取订单商品属性信息
        List<AdminOrderGoodsBO> attrBOList = orderAdminMapper.getOrderGoodsDetail(orderId);
        //退款申请信息默认为空
        AdminOrderApplyRefundBO applyRefundBO = null;
        //退款结果信息默认为空
        AdminOrderRefundResultBO refundResultBO = null;

        //查询订单退款申请信息
        if (hasApplyRefundInfo(orderDetailBO.getOrderStateBO())) {
            //获取有效的退款申请信息
            List<AdminOrderApplyRefundBO> refundBOList = orderAdminMapper.listApplyRefundInfo(orderId);
            if (refundBOList == null || refundBOList.size() > 1) {
                logger.error("订单退款申请信息异常orderId={}", orderId);
                throw new BusinessException("订单退款申请信息异常");
            }
            //订单退款信息:未申请退款或取消申请退款时为空
            applyRefundBO = refundBOList.get(0);
        }
        //查询订单退款结果信息
        if (hasRefundResultInfo(orderDetailBO.getOrderStateBO())) {
            List<AdminOrderRefundResultBO> refundResultBOList = orderAdminMapper.listRefundResultInfo(orderId);
            if (refundResultBOList == null) {
                logger.error("订单退款结果信息异常orderId={}", orderId);
                throw new BusinessException("订单退款结果信息异常");
            }
            refundResultBO = refundResultBOList.get(0);
        }
        //封装订单详情VO
        AdminOrderDetailVO orderDetailVO = wrapOrderDetailVO(orderDetailBO, attrBOList, applyRefundBO, refundResultBO);
        return orderDetailVO;
    }


    @Override
    public void manualRefund(ManualRefundDTO manualRefundDTO) {
        //校验手动退款传参
        checkManualRefundInfo(manualRefundDTO);
        //获取订单id
        Long orderId = Long.parseLong(manualRefundDTO.getOrderId());
        //获取订单基本信息
        AdminOrderDetailBO orderDetailBO = orderAdminMapper.getOrderDetail(orderId);
        //退款失败和退款中可以进行人工退款
        if (orderDetailBO == null || (!OrderStateHelp.isDrawbackFail(orderDetailBO.getOrderStateBO()) && !OrderStateHelp.isDrawbackIng(orderDetailBO.getOrderStateBO()))) {
            throw new BusinessException("订单信息异常，无法人工退款");
        }
        //查询退款发生在接单前后
        StatePO statePO = orderAdminMapper.getStatePO(orderDetailBO.getOrderStateBO().getOrderState(), STATE_TYPE_ORDER);
        //订单更新信息
        OrderPO orderPO = new OrderPO();
        orderPO.setOrderId(orderId);
        orderPO.setCompleteState(ORDER_STATE_TO_CANCEL == statePO.getStateBusinessType() ? OrderConstant.CompleteStateEnum.CANCELED.getState() : OrderConstant.CompleteStateEnum.FINISHED.getState());
        orderPO.setRefundState(OrderConstant.RefundStateEnum.REFUND_SUCCESS.getState());
        //退款结果信息
        RefundResultPO refundResultPO = new RefundResultPO();
        refundResultPO.setResultState(RESULT_STATE_SUCCESS);
        refundResultPO.setOrderId(orderId);
        refundResultPO.setRefundType(REFUND_TYPE_REFUND);
        refundResultPO.setRefundChannel(manualRefundDTO.getRefundChannel());
        refundResultPO.setReceiveId(manualRefundDTO.getReceiveId());
        refundResultPO.setRefundFee(RDbTrans.asDbPrice(manualRefundDTO.getRefundFee()));
        refundResultPO.setStaffId(getCurrentUserID(request));
        //退款操作
        doManualRefund(orderPO, refundResultPO);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void doManualRefund(OrderPO orderPO, RefundResultPO refundResultPO) {
        Integer updateResult = orderAdminMapper.updateOrderInfo(orderPO);
        Integer saveResult = orderAdminMapper.saveManualRefundResult(refundResultPO);
        if (!(updateResult == 1 && saveResult == 1)) {
            logger.error("人工退款入库异常,orderPO={},refundResultPO={}", orderPO, refundResultPO);
            throw new BusinessException("人工退款入库异常");
        }
    }

    /**
     * 人工退款参数校验
     *
     * @param manualRefundDTO
     */
    private void checkManualRefundInfo(ManualRefundDTO manualRefundDTO) {
        if (manualRefundDTO == null || manualRefundDTO.getRefundChannel() == null || RStr.isEmpty(manualRefundDTO.getOrderId())
                || RStr.isEmpty(manualRefundDTO.getReceiveId()) || RStr.isEmpty(manualRefundDTO.getRefundFee())) {
            throw new BusinessException("人工退款提交参数异常");
        }
    }

    /**
     * 判断订单是否具有退款结果信息
     *
     * @param orderStateBO
     * @return
     */
    private boolean hasRefundResultInfo(OrderStateBO orderStateBO) {
        //退款成功
        if (OrderConstant.OrderBusinessStateEnum.DRAWBACK_SUCCESS.getState().equals(OrderStateHelp.transBusinessOrderState(orderStateBO))) {
            return true;
        }
        return false;
    }

    /**
     * 判断订单是否具有有效退款申请信息
     *
     * @param orderStateBO
     * @return
     */
    private boolean hasApplyRefundInfo(OrderStateBO orderStateBO) {
        //退款申请中、退款中、退款失败、退款成功
        if (OrderStateHelp.isApplyDrawbackIng(orderStateBO) || OrderStateHelp.isDrawbackIng(orderStateBO) || OrderStateHelp.isDrawbackFail(orderStateBO) || OrderStateHelp.isDrawbackSuccess(orderStateBO)) {
            return true;
        }
        return false;
    }

    /**
     * 封装订单详情VO
     *
     * @param orderDetailBO
     * @param attrBOList
     * @return
     */
    private AdminOrderDetailVO wrapOrderDetailVO(AdminOrderDetailBO orderDetailBO, List<AdminOrderGoodsBO> attrBOList, AdminOrderApplyRefundBO orderRefundBO, AdminOrderRefundResultBO refundResultBO) {
        //订单详情返回VO
        AdminOrderDetailVO orderDetailVO = new AdminOrderDetailVO();
        //订单状态BO
        OrderStateBO orderStateBO = orderDetailBO.getOrderStateBO();
        //订单id
        orderDetailVO.setOrderId(orderDetailBO.getOrderId().toString());
        //订单no
        orderDetailVO.setOrderNo(orderDetailBO.getOrderNo());
        //订单业务状态名
        orderDetailVO.setOrderBusinessState(OrderStateHelp.transBusinessOrderState(orderStateBO).getState());
        //订单业务状态编码
        orderDetailVO.setOrderBusinessStateName(OrderStateHelp.transBusinessOrderState(orderStateBO).getDescription());
        //订单交易类型
        orderDetailVO.setTradeArea(orderDetailBO.getTradeArea());
        //订单来源
        orderDetailVO.setOrderChannelName(orderDetailBO.getOrderChannelName());
        //订单金额
        orderDetailVO.setPayPrice(RDbTrans.asShowPrice(orderDetailBO.getPayPrice()));
        //配送方式id
        orderDetailVO.setDeliveryType(orderDetailBO.getDeliveryType());
        //配送时间
        orderDetailVO.setDeliveryTime(orderDetailBO.getDeliveryTime());
        //配送地址
        orderDetailVO.setDeliveryAddress(orderDetailBO.getDeliveryAddress());
        //用户id
        orderDetailVO.setUserId(orderDetailBO.getUserId());
        //用户电话
        orderDetailVO.setUserPhone(orderDetailBO.getUserPhone());
        //用户昵称(微信)
        orderDetailVO.setUserNickName(orderDetailBO.getUserNickName());
        //酒行id
        orderDetailVO.setShopId(orderDetailBO.getShopId());
        //酒行名称
        orderDetailVO.setShopName(orderDetailBO.getShopName());
        //酒行电话
        orderDetailVO.setShopPhone(orderDetailBO.getShopPhone());
        //商品列表
        orderDetailVO.setGoodsVOList(wrapGoodsVOList(attrBOList));
        //商品总价，当前业务商品合计金额为订单应付金额
        orderDetailVO.setTotalGoodsFee(RDbTrans.asShowPrice(orderDetailBO.getOriginalPrice()));
        //优惠券信息
        orderDetailVO.setCouponInfo(wrapCouponInfo(orderDetailBO));
        //订单创建时间
        orderDetailVO.setCreateTime(RDbTrans.asShowDate(orderDetailBO.getCreateTime()));
        //订单支付时间
        orderDetailVO.setPayTime(RDbTrans.asShowDate(orderDetailBO.getPayTime()));
        //订单接单时间
        orderDetailVO.setPromiseTime(RDbTrans.asShowDate(orderDetailBO.getPromiseTime()));
        //订单收货时间
        orderDetailVO.setReceiveTime(RDbTrans.asShowDate(orderDetailBO.getReceiveTime()));
        //订单评价时间
        orderDetailVO.setCommentTime(RDbTrans.asShowDate(orderDetailBO.getCommentTime()));
        //订单取消时间
        orderDetailVO.setCancelTime(wrapCancelTime(orderDetailBO));
        //留言批注
        orderDetailVO.setRemark(orderDetailBO.getRemark());
        //有效退款申请信息不为空时展示
        if (hasApplyRefundInfo(orderStateBO)) {
            //订单申请退款时间
            orderDetailVO.setApplyDrawbackTime(RDbTrans.asShowDate(orderRefundBO.getCreateTime()));
            //退款理由
            orderDetailVO.setDrawbackDesc(orderRefundBO.getRefundDesc());
            //退款理由备注
            orderDetailVO.setDrawbackRemark(orderRefundBO.getRefundRemark());
        }
        //退款成功展示退款结果信息
        if (OrderStateHelp.isDrawbackSuccess(orderStateBO)) {
            //退款类型
            orderDetailVO.setDrawbackType(refundResultBO.getRefundType());
            //退款时间
            orderDetailVO.setDrawbackTime(RDbTrans.asShowDate(refundResultBO.getRefundTime()));
            //退款渠道
            orderDetailVO.setDrawbackChannel(refundResultBO.getRefundChannel());
            //退款接受方id
            orderDetailVO.setDrawbackReceiveId(refundResultBO.getReceiveId());
            //退款费用
            orderDetailVO.setDrawbackFee(RDbTrans.asShowPrice(refundResultBO.getRefundFee()));
        }
        //退款中、退款失败订单展示人工退款按钮
        if (OrderStateHelp.isDrawbackIng(orderStateBO) || OrderStateHelp.isDrawbackFail(orderStateBO)) {
            orderDetailVO.setShowManualRefund(SHOW_MANUAL_REFUND);
        }
        return orderDetailVO;
    }

    /**
     * 封装订单取消时间
     *
     * @param orderDetailBO
     * @return
     */
    private String wrapCancelTime(AdminOrderDetailBO orderDetailBO) {
        //订单完成状态为已取消，完成时间即订单取消时间
        if (OrderConstant.CompleteStateEnum.CANCELED.getState().equals(orderDetailBO.getOrderStateBO().getCommentState())) {
            return orderDetailBO.getCommentTime();
        }
        return null;
    }

    /**
     * 封装优惠券展示信息
     *
     * @param orderDetailBO
     * @return
     */
    private String wrapCouponInfo(AdminOrderDetailBO orderDetailBO) {
        //未使用优惠券
        if (RStr.isEmpty(orderDetailBO.getCondition()) && RStr.isEmpty(orderDetailBO.getCostLimitMoney()) && RStr.isEmpty(orderDetailBO.getMoney())) {
            return null;
        }
        //使用优惠券
        if (RStr.isEmpty(orderDetailBO.getCondition())) {
            return "满" + mathRoundDouble(orderDetailBO.getCostLimitMoney()) + "元减" + mathRoundDouble(orderDetailBO.getMoney());
        } else {
            return "无门槛直减" + mathRoundDouble(orderDetailBO.getMoney());
        }
    }

    public String mathRoundDouble(Object o) {
        Double d = Double.parseDouble(o.toString());
        if (Math.round(d) - d == 0D) {
            return String.valueOf(Math.round(d));
        }
        return String.valueOf(d);
    }

    /**
     * 封装订单商品VO列表
     *
     * @param attrBOList
     * @return
     */
    private List<AdminOrderDetailGoodsVO> wrapGoodsVOList(List<AdminOrderGoodsBO> attrBOList) {
        List<AdminOrderDetailGoodsVO> detailGoodsVOS = Lists.newArrayList();
        for (AdminOrderGoodsBO adminOrderGoodsBO : attrBOList) {
            if (checkIfGoodsExist(detailGoodsVOS, adminOrderGoodsBO)) {
                AdminOrderDetailGoodsVO goodsVO = obtainDetailGoodsVO(detailGoodsVOS, adminOrderGoodsBO);
                List<AdminGoodsListAttrVO> attrVOList = goodsVO.getAttrVOList();
                AdminGoodsListAttrVO attrVO = new AdminGoodsListAttrVO();
                attrVO.setAttrCode(adminOrderGoodsBO.getAttrCode());
                attrVO.setAttrValueShowName(adminOrderGoodsBO.getAttrValueShowName());
                attrVOList.add(attrVO);
                goodsVO.setAttrVOList(attrVOList);
            } else {
                AdminOrderDetailGoodsVO goodsVO = new AdminOrderDetailGoodsVO();
                List<AdminGoodsListAttrVO> goodsAttrVOList = Lists.newArrayList();
                AdminGoodsListAttrVO attrVO = new AdminGoodsListAttrVO();
                attrVO.setAttrCode(adminOrderGoodsBO.getAttrCode());
                attrVO.setAttrValueShowName(adminOrderGoodsBO.getAttrValueShowName());
                goodsAttrVOList.add(attrVO);
                goodsVO.setGoodsId(adminOrderGoodsBO.getGoodsId());
                goodsVO.setGoodsNum(adminOrderGoodsBO.getGoodsNum());
                goodsVO.setGoodsPrice(RDbTrans.asShowPrice(adminOrderGoodsBO.getGoodsPrice()));
                goodsVO.setGoodsName(adminOrderGoodsBO.getGoodsName());
                goodsVO.setGoodsPhoto(obtainSingerPhoto(adminOrderGoodsBO.getGoodsPhotos()));
                goodsVO.setAttrVOList(goodsAttrVOList);
                detailGoodsVOS.add(goodsVO);
            }
        }
        return detailGoodsVOS;
    }


    private String obtainSingerPhoto(String goodsPhotos) {
        if (RStr.isNotEmpty(goodsPhotos)) {
            String[] photos = goodsPhotos.split("\\^");
            return photos.length > 0 ? photos[0] : null;
        }
        return null;
    }


    private AdminOrderDetailGoodsVO obtainDetailGoodsVO(List<AdminOrderDetailGoodsVO> detailGoodsVOS, AdminOrderGoodsBO adminOrderGoodsBO) {
        for (AdminOrderDetailGoodsVO goodsVO : detailGoodsVOS) {
            if (goodsVO.getGoodsId().equals(adminOrderGoodsBO.getGoodsId())) {
                return goodsVO;
            }
        }
        return null;
    }

    private boolean checkIfGoodsExist(List<AdminOrderDetailGoodsVO> detailGoodsVOS, AdminOrderGoodsBO adminOrderGoodsBO) {
        boolean isExist = false;
        for (AdminOrderDetailGoodsVO goodsVO : detailGoodsVOS) {
            if (goodsVO.getGoodsId().equals(adminOrderGoodsBO.getGoodsId())) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    /**
     * 封装请求参数作为查询参数
     *
     * @param orderListSearchDTO
     */
    private void wrapSearchDTO(OrderListSearchDTO orderListSearchDTO) {
        if (orderListSearchDTO.getOrderBusinessState() != null) {
            OrderStateBO orderStateBO = OrderStateHelp.transOrderStateBO(orderListSearchDTO.getOrderBusinessState());
            orderListSearchDTO.setCompleteState(orderStateBO.getCompleteState());
            orderListSearchDTO.setOrderState(orderStateBO.getOrderState());
            orderListSearchDTO.setRefundState(orderStateBO.getRefundState());
            orderListSearchDTO.setCommentState(orderStateBO.getCommentState());
        }
    }

    /**
     * 封装order列表VO
     *
     * @param orderInfoList
     * @return
     */
    private List<AdminOrderListVO> wrapOrderVOList(List<AdminOrderBO> orderInfoList) {
        List<AdminOrderListVO> OrderVOList = Lists.newArrayList();
        for (AdminOrderBO adminOrderBO : orderInfoList) {
            AdminOrderListVO adminOrderListVO = new AdminOrderListVO();
            adminOrderListVO.setOrderId(adminOrderBO.getOrderId().toString());
            adminOrderListVO.setOrderNo(adminOrderBO.getOrderNo());
            adminOrderListVO.setCreateTime(RDbTrans.asShowDate(adminOrderBO.getCreateTime()));
            adminOrderListVO.setShopName(adminOrderBO.getShopName());
            adminOrderListVO.setUserId(adminOrderBO.getUserId());
            adminOrderListVO.setPayPrice(RDbTrans.asShowPrice(adminOrderBO.getPayPrice()));
            adminOrderListVO.setTradeArea(adminOrderBO.getTradeArea());
            adminOrderListVO.setOrderBusinessState(OrderStateHelp.transBusinessOrderState(adminOrderBO.getOrderStateBO()).getState());
            adminOrderListVO.setOrderBusinessStateName(OrderStateHelp.transBusinessOrderState(adminOrderBO.getOrderStateBO()).getDescription());
            OrderVOList.add(adminOrderListVO);
        }
        return OrderVOList;
    }

    /**
     * 登录id
     *
     * @param request
     * @return
     */
    public static int getCurrentUserID(HttpServletRequest request) {
        UserIdentify identify = (UserIdentify) request.getAttribute(Constants.LOGINIDENTIFY_KEY);
        if (identify == null)
            return 0;
        return identify.getUserId();
    }
}
