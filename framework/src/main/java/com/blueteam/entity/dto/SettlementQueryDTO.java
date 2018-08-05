package com.blueteam.entity.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 结算列表分页查询
 */
public class SettlementQueryDTO {
	private int pageIndex = 1;
	private int pageSize = 10;
	private Integer beginIndex;
	private String region;// 交易地区
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;// 结算开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endTime;// 结算截止时间
	private String vendorName;// 商家店名
	private Integer vendorId;// 商家店Id
	private Integer settlementId;//结算Id

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(Integer settlementId) {
		this.settlementId = settlementId;
	}

	public Integer getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(Integer beginIndex) {
		this.beginIndex = beginIndex;
	}
}
