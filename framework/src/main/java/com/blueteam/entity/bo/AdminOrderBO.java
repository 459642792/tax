package com.blueteam.entity.bo;

/**
 * 订单列表BO
 * Created by  NastyNas on 2018/1/9.
 */
public class AdminOrderBO {
    Long orderId;
    String orderNo;
    String createTime;
    String shopName;
    Long userId;
    String payPrice;
    String tradeArea;
    OrderStateBO orderStateBO;
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

    public OrderStateBO getOrderStateBO() {
        return orderStateBO;
    }

    public void setOrderStateBO(OrderStateBO orderStateBO) {
        this.orderStateBO = orderStateBO;
    }
}
