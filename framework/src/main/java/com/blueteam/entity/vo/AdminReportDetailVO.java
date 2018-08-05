package com.blueteam.entity.vo;

import java.util.List;

/**
 * 后台管理举报详情页VO
 * Created by  NastyNas on 2018/1/15.
 */
public class AdminReportDetailVO {
    //举报状态
    Integer reportState;
    //订单编号
    String orderNo;
    //订单金额
    String payPrice;
    //店铺名称
    String shopName;
    //用户账号
    String userId;
    //举报内容
    String reportContext;
    //举报图片字符串
    String reportPhotoString;
    //举报图片
    List<String> reportPhotoList;
    //客服回复
    String reply;
    //处理结果
    String result;

    public Integer getReportState() {
        return reportState;
    }

    public void setReportState(Integer reportState) {
        this.reportState = reportState;
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

    public String getReportPhotoString() {
        return reportPhotoString;
    }

    public void setReportPhotoString(String reportPhotoString) {
        this.reportPhotoString = reportPhotoString;
    }

    public List<String> getReportPhotoList() {
        return reportPhotoList;
    }

    public void setReportPhotoList(List<String> reportPhotoList) {
        this.reportPhotoList = reportPhotoList;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }
}
