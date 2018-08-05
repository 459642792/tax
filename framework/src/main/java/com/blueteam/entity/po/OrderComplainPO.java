package com.blueteam.entity.po;

import com.blueteam.base.util.DateUtil;

import java.util.Date;

public class OrderComplainPO {
    private Long id;

    private Date createdTime;

    private Date updateTime;

    private Integer userId;

    private Long orderId;

    private Long vendorId;

    private String complainReason;

    private Integer updateStaffId;

    private String disposeResult;

    private Integer state;

    private String reply;

    private String complainPicture;

    private String orderNo;

    private Integer payPrice;

    public Integer getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Integer payPrice) {
        this.payPrice = payPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getComplainReason() {
        return complainReason;
    }

    public void setComplainReason(String complainReason) {
        this.complainReason = complainReason == null ? null : complainReason.trim();
    }

    public Integer getUpdateStaffId() {
        return updateStaffId;
    }

    public void setUpdateStaffId(Integer updateStaffId) {
        this.updateStaffId = updateStaffId;
    }

    public String getDisposeResult() {
        return disposeResult;
    }

    public void setDisposeResult(String disposeResult) {
        this.disposeResult = disposeResult == null ? null : disposeResult.trim();
    }


    public String getComplainPicture() {
        return complainPicture;
    }

    public void setComplainPicture(String complainPicture) {
        this.complainPicture = complainPicture == null ? null : complainPicture.trim();
    }

    public String getDisposeTime() {
        if (state != 0) {
            return DateUtil.format(this.getUpdateTime(), "yyyy-MM-dd HH:mm");
        }
        return null;
    }

    public String getRealCreatedTime() {
        return DateUtil.format(this.getCreatedTime(), "yyyy-MM-dd HH:mm");
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}