package com.blueteam.entity.dto;

/**
 * ljc 2018年1月4日 17:04:40
 */
public class VendorOrderDTO {

    private Integer pageIndex;

    private Integer pageSize;

    private Integer beginIndex;

    private Long orderId;

    /**
     * 订单编号，用于平台对用户展示
     */
    private String orderNo;

    private Integer vendorId;

    private Integer orderState;

    /**
     * 订单完成状态 0-未完成  1-已完成  2-已取消
     */
    private Integer completeState;

    /**
     * 退款状态 关联字典值表 tf_d_state
     */
    private Integer refundState;

    /**
     * 评价状态  0-未评价  1-已评价
     */
    private Integer commentState;

    private Integer commentScore;

    private String keyword;

    private Integer version;



    public String getKeyword() {
        if (keyword=="")keyword=null;
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(Integer commentScore) {
        this.commentScore = commentScore;
    }

    public Integer getPageIndex() {
        return pageIndex == null ? 1 : pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 :pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(Integer beginIndex) {
        this.beginIndex = beginIndex;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
