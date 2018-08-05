package com.blueteam.entity.bo;

/**
 * 后台管理订单退款结果信息
 * Created by  NastyNas on 2018/1/16.
 */
public class AdminOrderRefundResultBO {
    //退款结果:0-退款失败 1-退款成功
    Integer resultState;
    //退款类型:1-系统退款 2-人工退款
    Integer refundType;
    //退款渠道
    Integer refundChannel;
    //退款接受号
    String receiveId;
    //退款金额
    String refundFee;
    //退款时间
    String refundTime;

    public Integer getResultState() {
        return resultState;
    }

    public void setResultState(Integer resultState) {
        this.resultState = resultState;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public Integer getRefundChannel() {
        return refundChannel;
    }

    public void setRefundChannel(Integer refundChannel) {
        this.refundChannel = refundChannel;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

}
