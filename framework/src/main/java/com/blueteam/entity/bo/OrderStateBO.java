package com.blueteam.entity.bo;

/**
 * 订单状态相关字段bo
 * Created by  NastyNas on 2018/1/9.
 */
public class OrderStateBO {
    Integer completeState;
    Integer orderState;
    Integer refundState;
    Integer commentState;

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
