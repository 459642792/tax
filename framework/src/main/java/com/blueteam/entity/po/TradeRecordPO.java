
package com.blueteam.entity.po;


import java.util.Date;

/**
 * (TF_M_TRADE_RECORD)
 * 
 * @author xiaojiang
 * @version 1.0.0 2018-01-11
 */
public class TradeRecordPO implements java.io.Serializable {
    /** 支付 */
    public static final int TRADE_TYP_PAY = 1;
    /** 退款 */
    public static final int TRADE_TYP_REFUND = 2;

    /** 版本号 */
    private static final long serialVersionUID = 8545802398103660246L;
    
    /**  */
    private Integer id;
    
    /** 订单id */
    private Long orderId;
    
    /** 如果是退款 退款id */
    private Long refundId;
    
    /** 业务结果 */
    private String resultCode;
    
    /** 交易类型 1 支付 2 退款 */
    private Integer tradeType;
    
    /** 发起参数 */
    private String tradeStartRecord;
    
    /** 结果详情 */
    private String tradeEndRecord;
    
    /** 开始时间 */
    private Date tradeStartTime;
    
    /** 结束时间 */
    private Date tradeEndTime;
    
    /**
     * 获取
     * 
     * @return 
     */
    public Integer getId() {
        return this.id;
    }
     
    /**
     * 设置
     * 
     * @param id
     *          
     */
    public void setId(Integer id) {
        this.id = id;
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
    
    /**
     * 获取如果是退款 退款id
     * 
     * @return 如果是退款 退款id
     */
    public Long getRefundId() {
        return this.refundId;
    }
     
    /**
     * 设置如果是退款 退款id
     * 
     * @param refundId
     *          如果是退款 退款id
     */
    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }
    
    /**
     * 获取业务结果
     * 
     * @return 业务结果
     */
    public String getResultCode() {
        return this.resultCode;
    }
     
    /**
     * 设置业务结果
     * 
     * @param resultCode
     *          业务结果
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    
    /**
     * 获取交易类型 1 支付 2 退款
     * 
     * @return 交易类型 1 支付 2 退款
     */
    public Integer getTradeType() {
        return this.tradeType;
    }
     
    /**
     * 设置交易类型 1 支付 2 退款
     * 
     * @param tradeType
     *          交易类型 1 支付 2 退款
     */
    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }
    
    /**
     * 获取发起参数
     * 
     * @return 发起参数
     */
    public String getTradeStartRecord() {
        return this.tradeStartRecord;
    }
     
    /**
     * 设置发起参数
     * 
     * @param tradeStartRecord
     *          发起参数
     */
    public void setTradeStartRecord(String tradeStartRecord) {
        this.tradeStartRecord = tradeStartRecord;
    }
    
    /**
     * 获取结果详情
     * 
     * @return 结果详情
     */
    public String getTradeEndRecord() {
        return this.tradeEndRecord;
    }
     
    /**
     * 设置结果详情
     * 
     * @param tradeEndRecord
     *          结果详情
     */
    public void setTradeEndRecord(String tradeEndRecord) {
        this.tradeEndRecord = tradeEndRecord;
    }
    
    /**
     * 获取开始时间
     * 
     * @return 开始时间
     */
    public Date getTradeStartTime() {
        return this.tradeStartTime;
    }
     
    /**
     * 设置开始时间
     * 
     * @param tradeStartTime
     *          开始时间
     */
    public void setTradeStartTime(Date tradeStartTime) {
        this.tradeStartTime = tradeStartTime;
    }
    
    /**
     * 获取结束时间
     * 
     * @return 结束时间
     */
    public Date getTradeEndTime() {
        return this.tradeEndTime;
    }
     
    /**
     * 设置结束时间
     * 
     * @param tradeEndTime
     *          结束时间
     */
    public void setTradeEndTime(Date tradeEndTime) {
        this.tradeEndTime = tradeEndTime;
    }

    public TradeRecordPO() {
    }

    public TradeRecordPO(Integer id, Long orderId, Long refundId, String resultCode, Integer tradeType, String tradeStartRecord, String tradeEndRecord, Date tradeStartTime, Date tradeEndTime) {
        this.id = id;
        this.orderId = orderId;
        this.refundId = refundId;
        this.resultCode = resultCode;
        this.tradeType = tradeType;
        this.tradeStartRecord = tradeStartRecord;
        this.tradeEndRecord = tradeEndRecord;
        this.tradeStartTime = tradeStartTime;
        this.tradeEndTime = tradeEndTime;
    }
}