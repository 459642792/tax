package com.blueteam.entity.vo;

/**
 * 后台管理交易数据列表VO
 * Created by  NastyNas on 2018/1/17.
 */
public class AdminTradeListVO {

    //订单编号
    String orderNo;
    //交易时间
    String tradeTime;
    //收款店铺
    String shopName;
    //交易地区
    String tradeArea;
    //付款人
    Long userId;
    //订单来源 1-普通订单 2-面对面付款订单
    Integer orderSource;
    //订单金额
    String originalPrice;
    //优惠券类型 1-平台 2-商家
    Integer couponType;
    //优惠券金额
    String couponFee;
    //实付金额
    String payPrice;
    //交易状态编码  9-退款失败 10-退款成功 null-交易成功
    Integer orderBusinessState;
    //交易状态名称
    String orderBusinessStateName;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public String getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(String couponFee) {
        this.couponFee = couponFee;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public Integer getOrderBusinessState() {
        return orderBusinessState;
    }

    public void setOrderBusinessState(Integer orderBusinessState) {
        this.orderBusinessState = orderBusinessState;
    }

    public String getOrderBusinessStateName() {
        return orderBusinessStateName;
    }

    public void setOrderBusinessStateName(String orderBusinessStateName) {
        this.orderBusinessStateName = orderBusinessStateName;
    }

    public String getTradeArea() {
        return tradeArea;
    }

    public void setTradeArea(String tradeArea) {
        this.tradeArea = tradeArea;
    }
}
