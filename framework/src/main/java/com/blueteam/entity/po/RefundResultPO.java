package com.blueteam.entity.po;

import java.util.Date;

/**
 * 退款结果表(TF_M_REFUND_RESULT)
 *
 * @author
 * @version 1.0.0 2018-01-17
 */
public class RefundResultPO implements java.io.Serializable {

    //退款成功
    public static final int RESULT_STATE_SUCCESS = 1;
    //退款失败
    public static final int RESULT_STATE_FAIL = 0;
    //系统退款
    public static final int REFUND_TYPE_SYSTEM = 1;
    //人工退款
    public static final int REFUND_TYPE_REFUND = 2;

    //微信渠道退款
    public static final int REFUND_CHANNEL_WECHAT = 1;
    //支付宝渠道退款
    public static final int REFUND_CHANNEL_ALIPAY = 2;
    //银行卡渠道退款
    public static final int REFUND_CHANNEL_CARD = 3;


    /**
     * 版本号
     */
    private static final long serialVersionUID = 7252329583918912723L;

    /**
     * 退款结果id
     */
    private Integer refundResultId;

    /**
     * 0-退款失败 1-退款成功
     */
    private Integer resultState;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 退款类型:1-系统退款 2-人工退款
     */
    private Integer refundType;

    /**
     * 退款金额 单位：分
     */
    private Long refundFee;

    /**
     * 退款渠道: 1-微信 2-支付宝 3-银行卡转账
     *
     *
     */
    private Integer refundChannel;

    /**
     * 人工退款接收方唯一id:微信号/支付宝账号/银行卡账号
     */
    private String receiveId;

    /**
     * 创建时间:退款成功即退款时间
     */
    private Date createTime;

    /**
     * 操作员工id
     */
    private Integer staffId;

    /**
     * 获取退款结果id
     *
     * @return 退款结果id
     */
    public Integer getRefundResultId() {
        return this.refundResultId;
    }

    /**
     * 设置退款结果id
     *
     * @param refundResultId 退款结果id
     */
    public void setRefundResultId(Integer refundResultId) {
        this.refundResultId = refundResultId;
    }

    /**
     * 获取0-退款失败 1-退款成功
     *
     * @return 0-退款失败 1-退款成功
     */
    public Integer getResultState() {
        return this.resultState;
    }

    /**
     * 设置0-退款失败 1-退款成功
     *
     * @param resultState 0-退款失败 1-退款成功
     */
    public void setResultState(Integer resultState) {
        this.resultState = resultState;
    }

    /**
     * 获取订单id
     *
     * @return 订单id
     */
    public Long getOrderId() {
        return this.orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取退款类型:1-系统退款 2-人工退款
     *
     * @return 退款类型
     */
    public Integer getRefundType() {
        return this.refundType;
    }

    /**
     * 设置退款类型:1-系统退款 2-人工退款
     *
     * @param refundType 退款类型:1-系统退款 2-人工退款
     */
    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public Integer getRefundChannel() {
        return refundChannel;
    }

    public void setRefundChannel(Integer refundChannel) {
        this.refundChannel = refundChannel;
    }

    /**
     * 获取人工退款接收方唯一id:微信号/支付宝账号/银行卡账号
     *
     * @return 人工退款接收方唯一id
     */
    public String getReceiveId() {
        return this.receiveId;
    }

    /**
     * 设置人工退款接收方唯一id:微信号/支付宝账号/银行卡账号
     *
     * @param receiveId 人工退款接收方唯一id:微信号/支付宝账号/银行卡账号
     */
    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    /**
     * 获取创建时间:退款成功即退款时间
     *
     * @return 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建时间:退款成功即退款时间
     *
     * @param createTime 创建时间:退款成功即退款时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取操作员工id
     *
     * @return 操作员工id
     */
    public Integer getStaffId() {
        return this.staffId;
    }

    /**
     * 设置操作员工id
     *
     * @param staffId 操作员工id
     */
    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Long getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Long refundFee) {
        this.refundFee = refundFee;
    }

    public RefundResultPO() {
    }

    @Override
    public String toString() {
        return "RefundResultPO{" +
                "refundResultId=" + refundResultId +
                ", resultState=" + resultState +
                ", orderId=" + orderId +
                ", refundType=" + refundType +
                ", refundChannel=" + refundChannel +
                ", receiveId='" + receiveId + '\'' +
                ", createTime=" + createTime +
                ", staffId=" + staffId +
                '}';
    }
}