package com.blueteam.entity.vo;


import java.math.BigDecimal;
import java.util.Date;
/**
 * 结算统计列表
 * @author Administrator
 *
 */
public class SettlementListSumInfoVO {
	private String region;//所在地(地区)
	private String address;//地址
	private String phone;//电话
	private Integer vendorId;//商家ID
	private String  image;//图片地址
	private String vendorName;//商家店名
	private BigDecimal allAmounts;//交易总额
	private BigDecimal allCount;//交易笔数
	private BigDecimal allBalancedAmounts;//本次结算后已经结算总额
	private BigDecimal allUnbalancedAmounts;//还未结算总额
	private Date lastTime;//最后一次结算时间
	private Date lastOrderCompleteTime;//最后一次结算时间
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	public BigDecimal getAllCount() {
		return allCount;
	}
	public void setAllCount(BigDecimal allCount) {
		this.allCount = allCount;
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
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public Date getLastOrderCompleteTime() {
		return lastOrderCompleteTime;
	}
	public void setLastOrderCompleteTime(Date lastOrderCompleteTime) {
		this.lastOrderCompleteTime = lastOrderCompleteTime;
	}
	 
}
