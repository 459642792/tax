package com.blueteam.entity.dto.wechatapplet;

import com.blueteam.base.util.FieldValidate;

/**
 * 退款申请
 *
 * @author xiaojiang
 * @create 2018-01-12  10:26
 */
public class RefundDTO {
    /** 退款单号 */
    @FieldValidate(notNull = true)
    private String orderNo;
    /** 退款理由编码 0-商品与描述不符 1-不想买了 2-收取额外费用 9-其他*/
    @FieldValidate(notNull = true)
    private Integer code;
    /** 退款理由 */
    private String refundRemark;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark;
    }
}
