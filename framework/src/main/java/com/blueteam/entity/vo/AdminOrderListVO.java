package com.blueteam.entity.vo;

/**
 * 后台管理订单列表页VO
 * Created by  NastyNas on 2018/1/9.
 */
public class AdminOrderListVO {
    //订单id
    String orderId;
    //订单编号
    String orderNo;
    //订单创建时间
    String createTime;
    //店铺名称
    String shopName;
    //用户账号
    Long userId;
    //订单金额
    String payPrice;
    //交易地区
    String tradeArea;
    //订单状态
    Integer orderBusinessState;
    //订单状态名称
    String orderBusinessStateName;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getTradeArea() {
        return tradeArea;
    }

    public void setTradeArea(String tradeArea) {
        this.tradeArea = tradeArea;
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
}
