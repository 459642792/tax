
package com.blueteam.entity.po;

import java.util.Date;
/**
 * 预支付记录表(TF_M_PREPAID_RECORD)
 * 
 * @author xiaojiang
 * @version 1.0.0 2018-01-25
 */
public class PrepaidRecordPO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 8096748453227611071L;
    
    /** 主键id */
    private Integer prepaidRecordId;
    
    /** 时间戳 */
    private String timeStamp;
    
    /** 随机串 */
    private String nonceStr;
    
    /** 数据包 */
    private String prepaidPackage;
    
    /** 签名类型 */
    private String signType;
    
    /** 签名 */
    private String paySign;
    
    /** 订单no */
    private String orderNo;
    
    /** 创建时间 */
    private Date createTime;
    
    /**
     * 获取主键id
     * 
     * @return 主键id
     */
    public Integer getPrepaidRecordId() {
        return this.prepaidRecordId;
    }
     
    /**
     * 设置主键id
     * 
     * @param prepaidRecordId
     *          主键id
     */
    public void setPrepaidRecordId(Integer prepaidRecordId) {
        this.prepaidRecordId = prepaidRecordId;
    }
    
    /**
     * 获取时间戳
     * 
     * @return 时间戳
     */
    public String getTimeStamp() {
        return this.timeStamp;
    }
     
    /**
     * 设置时间戳
     * 
     * @param timeStamp
     *          时间戳
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    /**
     * 获取随机串
     * 
     * @return 随机串
     */
    public String getNonceStr() {
        return this.nonceStr;
    }
     
    /**
     * 设置随机串
     * 
     * @param nonceStr
     *          随机串
     */
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
    
    /**
     * 获取数据包
     * 
     * @return 数据包
     */
    public String getPrepaidPackage() {
        return this.prepaidPackage;
    }
     
    /**
     * 设置数据包
     * 
     * @param prepaidPackage
     *          数据包
     */
    public void setPrepaidPackage(String prepaidPackage) {
        this.prepaidPackage = prepaidPackage;
    }
    
    /**
     * 获取签名类型
     * 
     * @return 签名类型
     */
    public String getSignType() {
        return this.signType;
    }
     
    /**
     * 设置签名类型
     * 
     * @param signType
     *          签名类型
     */
    public void setSignType(String signType) {
        this.signType = signType;
    }
    
    /**
     * 获取签名
     * 
     * @return 签名
     */
    public String getPaySign() {
        return this.paySign;
    }
     
    /**
     * 设置签名
     * 
     * @param paySign
     *          签名
     */
    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
    
    /**
     * 获取订单no
     * 
     * @return 订单no
     */
    public String getOrderNo() {
        return this.orderNo;
    }
     
    /**
     * 设置订单no
     * 
     * @param orderNo
     *          订单no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
}