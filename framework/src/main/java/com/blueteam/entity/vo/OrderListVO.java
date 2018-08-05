package com.blueteam.entity.vo;

import java.util.Date;

/**
 * 商品属性
 *
 * @author xiaojiang
 * @create 2018-01-26  11:15
 */
public class OrderListVO {
	
	private Long orderId;
	  /**
     * 订单编号，用于平台对用户展示
     */
    private String orderNo;
    /**
     * 商家id
     */
    private Integer vendorId;
    
    private Integer userId;
    private String userName;

    /**
     * 订单原价(单位-分)
     */
    private Long originalPrice;

    /**
     * 支付金额(单位-分)
     */
    private Long payPrice;
    /**
     * 优惠金额(单位-分)
     */
    private Long couponAmount;
    /**
     * 订单完成时间
     */
    private Date completeTime;
    
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(Long originalPrice) {
		this.originalPrice = originalPrice;
	}
	public Long getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(Long payPrice) {
		this.payPrice = payPrice;
	}
	public Long getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(Long couponAmount) {
		this.couponAmount = couponAmount;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
    
 
}
