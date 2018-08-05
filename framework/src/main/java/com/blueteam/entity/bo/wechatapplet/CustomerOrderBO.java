package com.blueteam.entity.bo.wechatapplet;

import com.blueteam.entity.vo.OrderGoodsVO;

import java.util.List;

/**
 * 用户订单列表
 *
 * @author xiaojiang
 * @create 2018-01-13  13:53
 */
public class CustomerOrderBO {
    /** 订单编号，用于平台对用户展示*/
    private String orderNo;
    /** 订单id，用于平台对用户展示*/
    private Long orderId;
    /** 订单来源 1 普通订单 2面对面付款*/
    private Integer orderSource;
    /** 商家名称*/
    private String vendorName;
    /** 商家ID*/
    private Integer vendorId;
    /** 支付金额(单位-分)*/
    private Long payPrice;
    /** 退款金额(单位-分)*/
    private Long refundPrice;
    /**订单状态，关联字典值表tf_d_state*/
    private Integer orderState;

    /** 订单完成状态 0-未完成  1-已完成  2-已取消*/
    private Integer completeState;

    /**退款状态 关联字典值表 tf_d_state*/
    private Integer refundState;

    /**评价状态  0-未评价  1-已评价*/
    private Integer commentState;

    /** 有效状态  0-无效  1-有效*/
    private Integer validityState;
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

    public Long getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Long payPrice) {
        this.payPrice = payPrice;
    }

    public Long getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Long refundPrice) {
        this.refundPrice = refundPrice;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getCompleteState() {
        return completeState;
    }

    public void setCompleteState(Integer completeState) {
        this.completeState = completeState;
    }

    public Integer getRefundState() {
        return refundState;
    }

    public void setRefundState(Integer refundState) {
        this.refundState = refundState;
    }

    public Integer getCommentState() {
        return commentState;
    }

    public void setCommentState(Integer commentState) {
        this.commentState = commentState;
    }

    public Integer getValidityState() {
        return validityState;
    }

    public void setValidityState(Integer validityState) {
        this.validityState = validityState;
    }

    public List<OrderGoodsVO> getListGoods() {
        return listGoods;
    }

    public void setListGoods(List<OrderGoodsVO> listGoods) {
        this.listGoods = listGoods;
    }

    public CustomerOrderBO() {

    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public CustomerOrderBO(String orderNo, Long orderId, String vendorName, Integer vendorId, Long payPrice, Long refundPrice, Integer orderState, Integer completeState, Integer refundState, Integer commentState, Integer validityState, List<OrderGoodsVO> listGoods) {
        this.orderNo = orderNo;
        this.orderId = orderId;
        this.vendorName = vendorName;
        this.vendorId = vendorId;
        this.payPrice = payPrice;
        this.refundPrice = refundPrice;
        this.orderState = orderState;
        this.completeState = completeState;
        this.refundState = refundState;
        this.commentState = commentState;
        this.validityState = validityState;
        this.listGoods = listGoods;
    }

    @Override
    public String toString() {
        return "CustomerOrderBO{" +
                "orderNo='" + orderNo + '\'' +
                ", orderId=" + orderId +
                ", orderSource=" + orderSource +
                ", vendorName='" + vendorName + '\'' +
                ", vendorId=" + vendorId +
                ", payPrice=" + payPrice +
                ", refundPrice=" + refundPrice +
                ", orderState=" + orderState +
                ", completeState=" + completeState +
                ", refundState=" + refundState +
                ", commentState=" + commentState +
                ", validityState=" + validityState +
                ", listGoods=" + listGoods +
                '}';
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
