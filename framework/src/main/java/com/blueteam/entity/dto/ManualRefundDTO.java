package com.blueteam.entity.dto;

/**
 * 手动退款dto
 * Created by  NastyNas on 2018/1/17.
 */
public class ManualRefundDTO {
    //订单id
    String orderId;
    //退款渠道 1-微信 2-支付宝 3-银行卡转账
    Integer refundChannel;
    //退款金额
    String refundFee;
    //接受方账号
    String receiveId;

    public Integer getRefundChannel() {
        return refundChannel;
    }

    public void setRefundChannel(Integer refundChannel) {
        this.refundChannel = refundChannel;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
