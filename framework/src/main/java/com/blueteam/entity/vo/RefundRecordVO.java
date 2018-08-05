package com.blueteam.entity.vo;

import java.io.Serializable;

public class RefundRecordVO implements Serializable{

    private String refundReason;
    private String refundRemark;

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark;
    }
}
