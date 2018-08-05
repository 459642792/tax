package com.blueteam.entity.dto.wechatapplet;

import com.blueteam.base.util.FieldValidate;

import java.io.Serializable;
import java.util.List;

/**
 * 订单确认类
 *
 * @author xiaojiang
 * @create 2018-01-08  10:58
 */
public class OrderConfirmationDTO implements Serializable {
    /**  商家id*/
    @FieldValidate(notNull = true)
    private Integer vendorId;

    /**  优惠券id*/
    private Integer couponId;

    /** 收货方式  1-配送  2-自提 */
    @FieldValidate(notNull = true)
    private Integer deliveryType;

    /**  用户收获地址id*/
    @FieldValidate(notNull = true)
    private Integer userAddressId;
    /**  用户地址 或者结构化地址id*/
    private String deliveryAddress;
    /**  商家自提地址id*/
    private Integer vendorAddressId;

    /**  收/货时间(备注：yyyyMMdd-HH:MM-HH:MM  日期加时间区间)*/
    @FieldValidate(notNull = true)
    private String deliveryTime;

    /**  商品集合id 格式（1,2）*/
    @FieldValidate(notNull = true)
    private List<OrderGoodsDTO> listGoods;

    /**  原价格*/
    @FieldValidate(notNull = true)
    private Long price;

    /**  优惠后价格*/
    @FieldValidate(notNull = true)
    private Long total;

    /**  优惠券价格 */
    private Long couponTotal;

    /**  留言 请限制30个字*/
    private String remark;

    /** 下单渠道：1-微信小程序 2-支付宝小程序 3-app 4-web */
    @FieldValidate(notNull = true)
    private Integer orderChannel;

    /** 支付方式id 如 在线支付1 */
    @FieldValidate(notNull = true)
    private Integer payTypeId;

    /** 支付方式名 如 在线支付1  */
    @FieldValidate(notNull = true)
    private String payTypeName;

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

    public Integer getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }

    public Integer getVendorAddressId() {
        return vendorAddressId;
    }

    public void setVendorAddressId(Integer vendorAddressId) {
        this.vendorAddressId = vendorAddressId;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }



    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getCouponTotal() {
        return couponTotal;
    }

    public void setCouponTotal(Long couponTotal) {
        this.couponTotal = couponTotal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(Integer orderChannel) {
        this.orderChannel = orderChannel;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
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

    public List<OrderGoodsDTO> getListGoods() {
        return listGoods;
    }

    public void setListGoods(List<OrderGoodsDTO> listGoods) {
        this.listGoods = listGoods;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public String toString() {
        return "OrderConfirmationDTO{" +
                "vendorId=" + vendorId +
                ", couponId=" + couponId +
                ", deliveryType=" + deliveryType +
                ", userAddressId=" + userAddressId +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", vendorAddressId=" + vendorAddressId +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", listGoods=" + listGoods +
                ", price=" + price +
                ", total=" + total +
                ", couponTotal=" + couponTotal +
                ", remark='" + remark + '\'' +
                ", orderChannel=" + orderChannel +
                ", payTypeId=" + payTypeId +
                ", payTypeName='" + payTypeName + '\'' +
                '}';
    }
}
