package com.blueteam.entity.dto.wechatapplet;

import com.blueteam.base.util.FieldValidate;

/**
 * 面对面付款
 *
 * @author xiaojiang
 * @create 2018-01-15  15:35
 */
public class FaceToFaceOrderDTO {
    /** 用户id*/
    private Integer userId;
    /** 商家ID*/
    @FieldValidate(notNull = true)
    private Integer vendorId;
    /** 优惠券IDid*/
    private Integer couponId;
    /** 优惠券价格 纯数字 单位分*/
    private String couponTotal;
    /** 支付价格（优惠后的价格） 纯数字 单位分*/
    @FieldValidate(notNull = true)
    private String payPrice;
    /** 总价 原价 单位分*/
    @FieldValidate(notNull = true)
    private String totalMoney;
    /** 备注*/
    @FieldValidate(notNull = true)
    private String body;
    /** 下单渠道：1-微信小程序 2-支付宝小程序 3-app 4-web */
    @FieldValidate(notNull = true)
    private Integer orderChannel;
    /** 支付方式id 如 在线支付1 */
    @FieldValidate(notNull = true)
    private Integer payTypeId;
    /** 支付方式名 如 在线支付1  */
    @FieldValidate(notNull = true)
    private String payTypeName;
    /**  用户地址 或者结构化地址id*/
    @FieldValidate(notNull = true)
    private String deliveryAddress;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getCouponTotal() {
        return couponTotal;
    }

    public void setCouponTotal(String couponTotal) {
        this.couponTotal = couponTotal;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(Integer orderChannel) {
        this.orderChannel = orderChannel;
    }

    public Integer getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(Integer payTypeId) {
        this.payTypeId = payTypeId;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
