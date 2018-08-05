package com.blueteam.entity.dto;

/**
 * 订单列表页查询dto
 * Created by  NastyNas on 2018/1/8.
 */
public class OrderListSearchDTO extends BasePageSearch {
    //订单状态
    Integer orderBusinessState;
    //订单创建时间from
    String createFrom;
    //订单创建时间to
    String createTo;
    //订单编号
    String orderNo;
    //关键字
    String keyword;
    //交易地区
    String tradeArea;

    //临时数据
    Integer completeState;
    Integer orderState;
    Integer refundState;
    Integer commentState;

    public Integer getOrderBusinessState() {
        return orderBusinessState;
    }

    public void setOrderBusinessState(Integer orderBusinessState) {
        this.orderBusinessState = orderBusinessState;
    }

    public String getCreateFrom() {
        return createFrom;
    }

    public void setCreateFrom(String createFrom) {
        this.createFrom = createFrom;
    }

    public String getCreateTo() {
        return createTo;
    }

    public void setCreateTo(String createTo) {
        this.createTo = createTo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTradeArea() {
        return tradeArea;
    }

    public void setTradeArea(String tradeArea) {
        this.tradeArea = tradeArea;
    }

    public Integer getCompleteState() {
        return completeState;
    }

    public void setCompleteState(Integer completeState) {
        this.completeState = completeState;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
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
}
