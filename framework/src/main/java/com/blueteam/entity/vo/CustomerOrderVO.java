package com.blueteam.entity.vo;

import com.blueteam.base.constant.OrderConstant;
import com.blueteam.base.help.order.OrderStateHelp;
import com.blueteam.base.lang.RStr;
import com.blueteam.base.util.StringUtil;
import com.blueteam.entity.bo.OrderStateBO;
import com.blueteam.entity.bo.wechatapplet.CustomerOrderBO;

import java.util.List;

/**
 * 用户订单列表
 *
 * @author xiaojiang
 * @create 2018-01-13  10:14
 */
public class CustomerOrderVO {
    /** 订单no*/
    private String orderNo;
    /** 订单no*/
    private Long orderId;
    private Integer orderSource;
    /** 商家名称*/
    private String vendorName;
    /** 商家ID*/
    private Integer vendorId;
    /**  支付金额1*/
    private String payPrice;
    /** 退款金额*/
    private String refundPrice;
    /** 当前状态名称 */
    private String showStatus;
    /** 当前状态 */
    private Integer showType;
    /** 有效状态 是否可以删除 2代表可以取消订单 0代表删除订单 */
    private Integer orderValidity;
    /** 订单状态 0  去支付 1 去退款 2 去评价 3 取消申请退款 */
    private Integer showState;
    /** 当前状态名称 */
    private List<OrderGoodsVO> listGoods;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(String refundPrice) {
        this.refundPrice = refundPrice;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public Integer getOrderValidity() {
        return orderValidity;
    }

    public void setOrderValidity(Integer orderValidity) {
        this.orderValidity = orderValidity;
    }


    public Integer getShowState() {
        return showState;
    }

    public void setShowState(Integer showState) {
        this.showState = showState;
    }

    public List<OrderGoodsVO> getListGoods() {
        return listGoods;
    }

    public void setListGoods(List<OrderGoodsVO> listGoods) {
        this.listGoods = listGoods;
    }

    public CustomerOrderVO() {
    }

    public CustomerOrderVO(CustomerOrderBO customerOrder) {
        this.orderNo = customerOrder.getOrderNo();
        this.orderId = customerOrder.getOrderId();
        this.orderSource = customerOrder.getOrderSource();
        this.vendorName = customerOrder.getVendorName();
        this.vendorId = customerOrder.getVendorId();
        this.payPrice =  StringUtil.changeF2Y(customerOrder.getPayPrice().toString());
        this.refundPrice = RStr.isNotEmpty(customerOrder.getRefundPrice()) ? StringUtil.changeF2Y(customerOrder.getRefundPrice().toString()): null;
        this.listGoods = customerOrder.getListGoods();
        OrderStateBO orderStateBO = new OrderStateBO();
        orderStateBO.setCommentState(customerOrder.getCommentState());
        orderStateBO.setCompleteState(customerOrder.getCompleteState());
        orderStateBO.setOrderState(customerOrder.getOrderState());
        orderStateBO.setRefundState(customerOrder.getRefundState());
        OrderConstant.OrderBusinessStateEnum state = OrderStateHelp.transBusinessOrderState(orderStateBO);
        this.showStatus = state.getDescription();
        this.showType = state.getState();
        this.orderValidity = OrderStateHelp.transOrderValidityState(orderStateBO).getState().equals(OrderConstant.OrderValidityStateEnum.OTHER.getState()) ? null : OrderStateHelp.transOrderValidityState(orderStateBO).getState();
        this.showState = OrderStateHelp.transOrderOperateState(orderStateBO).getState().equals(OrderConstant.OrderOperateStateEnum.OTHER.getState()) ? null : OrderStateHelp.transOrderOperateState(orderStateBO).getState();
    }

    @Override
    public String toString() {
        return "CustomerOrderVO{" +
                "orderNo='" + orderNo + '\'' +
                ", orderId='" + orderId + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", vendorId=" + vendorId +
                ", payPrice='" + payPrice + '\'' +
                ", refundPrice='" + refundPrice + '\'' +
                ", showStatus='" + showStatus + '\'' +
                ", showType=" + showType +
                ", orderValidity=" + orderValidity +
                ", showState=" + showState +
                ", listGoods=" + listGoods +
                '}';
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getShowStatus() {
        return showStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }
}
