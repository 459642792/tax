package com.blueteam.entity.vo;

/**
 * 后台管理举报列表页VO
 * Created by  NastyNas on 2018/1/15.
 */
public class AdminReportListVO {
    //举报id
    String reportId;
    //举报时间
    String reportTime;
    //订单编号
    String orderNo;
    //店铺名称
    String shopName;
    //用户账号
    String userId;
    //举报内容
    String reportContext;
    //举报状态
    Integer reportState;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReportContext() {
        return reportContext;
    }

    public void setReportContext(String reportContext) {
        this.reportContext = reportContext;
    }

    public Integer getReportState() {
        return reportState;
    }

    public void setReportState(Integer reportState) {
        this.reportState = reportState;
    }
}
