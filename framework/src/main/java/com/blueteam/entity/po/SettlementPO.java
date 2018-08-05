package com.blueteam.entity.po;


import java.math.BigDecimal;
import java.util.Date;

public class SettlementPO {
	private Integer id;//ID
	private String region;//所在地(地区)
	private Integer vendorId;//商家ID
	private String vendorName;//商家店名
	private BigDecimal allBalancedAmounts;//本次结算后已经结算总额
	private BigDecimal balancedAmounts;//本次结算额度
	private Date createTime;//结算时间
	private String createUsername;//结算者
	private Integer createUserId;//结算者ID
	private Date startTime;//结算开始时间
	private Date endTime;//结算截止时间
	private Integer countType;//结算方式 结算方式  1微信   2 银行卡  3 支付宝 
	private String countNo;//结算方式的卡号
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public BigDecimal getAllBalancedAmounts() {
		return allBalancedAmounts.setScale(2);
	}
	public void setAllBalancedAmounts(BigDecimal allBalancedAmounts) {
		this.allBalancedAmounts = allBalancedAmounts;
	}
	public BigDecimal getBalancedAmounts() {
		return balancedAmounts.setScale(2);
	}
	public void setBalancedAmounts(BigDecimal balancedAmounts) {
		this.balancedAmounts = balancedAmounts;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUsername() {
		return createUsername;
	}
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getCountType() {
		return countType;
	}
	public void setCountType(Integer countType) {
		this.countType = countType;
	}
	public String getCountNo() {
		return countNo;
	}
	public void setCountNo(String countNo) {
		this.countNo = countNo;
	}

}
