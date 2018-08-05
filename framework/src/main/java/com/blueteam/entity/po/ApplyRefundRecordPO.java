
package com.blueteam.entity.po;

import java.util.Date;

/**
 * 订单退款申请表(TF_M_APPLY_REFUND_RECORD)
 * 
 * @author xiaojiang
 * @version 1.0.0 2018-01-11
 */
public class ApplyRefundRecordPO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1566877555941145084L;
    /** 未处理 */
    public static final int PROCESS_STATUS_UNTREATED = 0;
    /** -同意退款 */
    public static final int PROCESS_STATUS_PROCESSED= 1;
    /** 拒绝退款 */
    public static final int PROCESS_STATUS_DECLINE = 2;
    /** 已取消 */
    public static final int PROCESS_STATUS_CANCELED = 3;
    /** 退款申请id */
    private Integer applyRefundId;
    
    /** 订单id */
    private Long orderId;
    /** 退款金额 */
    private Long refundFee;
    /** 退款理由编码 0-商品与描述不符 1-不想买了 2-收取额外费用 9-其他 */
    private Integer refundReasonCode;
    
    /** 退款理由描述 */
    private String refundReasonDesc;
    
    /** 退款备注 */
    private String refundRemark;
    
    /** 处理状态 0-未处理 1-已处理 2-已取消 */
    private Integer processStatus;
    
    /** 负责处理的平台管理员id */
    private Integer processBy;
    
    /** 处理时间 */
    private Date processTime;
    
    /** 创建时间 */
    private Date createTime;
    
    /**
     * 获取退款申请id
     * 
     * @return 退款申请id
     */
    public Integer getApplyRefundId() {
        return this.applyRefundId;
    }
     
    /**
     * 设置退款申请id
     * 
     * @param applyRefundId
     *          退款申请id
     */
    public void setApplyRefundId(Integer applyRefundId) {
        this.applyRefundId = applyRefundId;
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
     * @param orderId
     *          订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Long refundFee) {
        this.refundFee = refundFee;
    }

    /**
     * 获取退款理由编码 0-商品与描述不符 1-不想买了 2-收取额外费用 9-其他
     * 
     * @return 退款理由编码 0-商品与描述不符 1-不想买了 2-收取额外费用 9-其他
     */
    public Integer getRefundReasonCode() {
        return this.refundReasonCode;
    }
     
    /**
     * 设置退款理由编码 0-商品与描述不符 1-不想买了 2-收取额外费用 9-其他
     * 
     * @param refundReasonCode
     *          退款理由编码 0-商品与描述不符 1-不想买了 2-收取额外费用 9-其他
     */
    public void setRefundReasonCode(Integer refundReasonCode) {
        this.refundReasonCode = refundReasonCode;
    }
    
    /**
     * 获取退款理由描述
     * 
     * @return 退款理由描述
     */
    public String getRefundReasonDesc() {
        return this.refundReasonDesc;
    }
     
    /**
     * 设置退款理由描述
     * 
     * @param refundReasonDesc
     *          退款理由描述
     */
    public void setRefundReasonDesc(String refundReasonDesc) {
        this.refundReasonDesc = refundReasonDesc;
    }
    
    /**
     * 获取退款备注
     * 
     * @return 退款备注
     */
    public String getRefundRemark() {
        return this.refundRemark;
    }
     
    /**
     * 设置退款备注
     * 
     * @param refundRemark
     *          退款备注
     */
    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark;
    }
    
    /**
     * 获取处理状态 0-未处理 1-已处理 2-已取消
     * 
     * @return 处理状态 0-未处理 1-已处理 2-已取消
     */
    public Integer getProcessStatus() {
        return this.processStatus;
    }
     
    /**
     * 设置处理状态 0-未处理 1-已处理 2-已取消
     * 
     * @param processStatus
     *          处理状态 0-未处理 1-已处理 2-已取消
     */
    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }
    
    /**
     * 获取负责处理的平台管理员id
     * 
     * @return 负责处理的平台管理员id
     */
    public Integer getProcessBy() {
        return this.processBy;
    }
     
    /**
     * 设置负责处理的平台管理员id
     * 
     * @param processBy
     *          负责处理的平台管理员id
     */
    public void setProcessBy(Integer processBy) {
        this.processBy = processBy;
    }
    
    /**
     * 获取处理时间
     * 
     * @return 处理时间
     */
    public Date getProcessTime() {
        return this.processTime;
    }
     
    /**
     * 设置处理时间
     * 
     * @param processTime
     *          处理时间
     */
    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }
    
    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }
     
    /**
     * 设置创建时间
     * 
     * @param createTime
     *          创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public ApplyRefundRecordPO() {
    }

    public ApplyRefundRecordPO(Integer applyRefundId, Long orderId, Long refundFee, Integer refundReasonCode, String refundReasonDesc, String refundRemark, Integer processStatus, Integer processBy, Date processTime, Date createTime) {
        this.applyRefundId = applyRefundId;
        this.orderId = orderId;
        this.refundFee = refundFee;
        this.refundReasonCode = refundReasonCode;
        this.refundReasonDesc = refundReasonDesc;
        this.refundRemark = refundRemark;
        this.processStatus = processStatus;
        this.processBy = processBy;
        this.processTime = processTime;
        this.createTime = createTime;
    }
}