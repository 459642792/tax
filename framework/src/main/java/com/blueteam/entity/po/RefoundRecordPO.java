package com.blueteam.entity.po;

import java.util.Date;

/**
 * 订单退款申请表(REFOUND_RECORD)
 *
 * @author
 * @version 1.0.0 2018-01-04
 */
public class RefoundRecordPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 5145085816656751909L;

    /**
     * 申请id
     */
    private Integer refoundRecordId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 退款理由
     */
    private String refoundReason;

    /**
     * 处理状态 0-未处理 1-已处理
     */
    private String processStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 负责处理的平台管理员id
     */
    private Integer processBy;

    /**
     * 处理时间
     */
    private Date processTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 获取申请id
     *
     * @return 申请id
     */
    public Integer getRefoundRecordId() {
        return this.refoundRecordId;
    }

    /**
     * 设置申请id
     *
     * @param refoundRecordId 申请id
     */
    public void setRefoundRecordId(Integer refoundRecordId) {
        this.refoundRecordId = refoundRecordId;
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
     * 获取退款理由
     *
     * @return 退款理由
     */
    public String getRefoundReason() {
        return this.refoundReason;
    }

    /**
     * 设置退款理由
     *
     * @param refoundReason 退款理由
     */
    public void setRefoundReason(String refoundReason) {
        this.refoundReason = refoundReason;
    }

    /**
     * 获取处理状态 0-未处理 1-已处理
     *
     * @return 处理状态 0-未处理 1-已处理
     */
    public String getProcessStatus() {
        return this.processStatus;
    }

    /**
     * 设置处理状态 0-未处理 1-已处理
     *
     * @param processStatus 处理状态 0-未处理 1-已处理
     */
    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     * @param processBy 负责处理的平台管理员id
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
     * @param processTime 处理时间
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
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}