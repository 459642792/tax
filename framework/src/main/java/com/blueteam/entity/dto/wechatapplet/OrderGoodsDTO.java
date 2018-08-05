package com.blueteam.entity.dto.wechatapplet;

/**
 * 商品信息表
 *
 * @author xiaojiang
 * @create 2018-01-09  14:56
 */
public class OrderGoodsDTO {
    /** 商品id*/
    private Long goodsId;
    /** 商品价格*/
    private Long goodsPrice;
    /** 商品数量 */
    private Integer sum;
    /** 针对该商品使用的特定优惠券 目前不传*/
    private Integer couponId;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    @Override
    public String toString() {
        return "OrderGoodsDTO{" +
                "goodsId=" + goodsId +
                ", goodsPrice=" + goodsPrice +
                ", sum=" + sum +
                ", couponId=" + couponId +
                '}';
    }
}
