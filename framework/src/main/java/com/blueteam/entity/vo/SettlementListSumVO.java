package com.blueteam.entity.vo;


import java.math.BigDecimal;
import java.util.Date;
/**
 * 结算统计列表
 * @author Administrator
 *
 */
public class SettlementListSumVO {
	private String region;//所在地(地区)
	private Integer vendorId;//商家ID
	private String vendorName;//商家店名
	private BigDecimal allAmounts;//交易总额
	private BigDecimal allBalancedAmounts;//本次结算后已经结算总额
	private BigDecimal allUnbalancedAmounts;//还未结算总额
	private BigDecimal balancedAmounts;//本次结算结算总额
	private Date createTime;//结算时间
 
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
	public BigDecimal getAllAmounts() {
		return allAmounts;
	}
	public void setAllAmounts(BigDecimal allAmounts) {
		this.allAmounts = allAmounts;
	}
	public BigDecimal getAllBalancedAmounts() {
		return allBalancedAmounts;
	}
	public void setAllBalancedAmounts(BigDecimal allBalancedAmounts) {
		this.allBalancedAmounts = allBalancedAmounts;
	}
	public BigDecimal getAllUnbalancedAmounts() {
		return allUnbalancedAmounts;
	}
	public void setAllUnbalancedAmounts(BigDecimal allUnbalancedAmounts) {
		this.allUnbalancedAmounts = allUnbalancedAmounts;
	}
	public BigDecimal getBalancedAmounts() {
		return balancedAmounts;
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
	
}
