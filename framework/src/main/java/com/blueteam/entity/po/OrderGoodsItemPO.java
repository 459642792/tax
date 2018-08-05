
package com.blueteam.entity.po;

import com.blueteam.base.lang.RStr;
import com.blueteam.base.util.StringUtil;

import java.util.List;

/**
 * 订单商品实例表(TF_B_ORER_GOODS_ITEM)
 *
 * @author b
 * @version 1.0.0 2018-01-04
 */
public class OrderGoodsItemPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -5171548917715135679L;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 条形码
     */
    private String barCode;

    /**
     * 商品数量
     */
    private Integer goodsNum;

    /**
     * 商品类型名
     */
    private String goodsTypeName;

    /**
     * 主品牌名称
     */
    private String mainBrandName;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 商品图片 多张以^分隔
     */
    private String goodsPhotos;



    /**
     * 商品原价(单位-分)
     */
    private Long originalPrice;

    /**
     * 实际支付金额(单位-分)
     */
    private Long payPrice;

    /**
     * 优惠金额(单位-分)
     */
    private Long couponAmount;

    /**
     * 商品属性
     */
    private List<OrderAttrPO> orderAttrPOS;

    /**
     * 获取订单id
     *
     * @return 订单id
     */
    public Long getOrderId() {
        return this.orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取商品id
     *
     * @return 商品id
     */
    public Long getGoodsId() {
        return this.goodsId;
    }

    /**
     * 设置商品id
     *
     * @param goodsId 商品id
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取商品名称
     *
     * @return 商品名称
     */
    public String getGoodsName() {
        return this.goodsName;
    }

    /**
     * 设置商品名称
     *
     * @param goodsName 商品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取条形码
     *
     * @return 条形码
     */
    public String getBarCode() {
        return this.barCode;
    }

    /**
     * 设置条形码
     *
     * @param barCode 条形码
     */
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    /**
     * 获取商品数量
     *
     * @return 商品数量
     */
    public Integer getGoodsNum() {
        return this.goodsNum;
    }

    /**
     * 设置商品数量
     *
     * @param goodsNum 商品数量
     */
    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    /**
     * 获取商品类型名
     *
     * @return 商品类型名
     */
    public String getGoodsTypeName() {
        return this.goodsTypeName;
    }

    /**
     * 设置商品类型名
     *
     * @param goodsTypeName 商品类型名
     */
    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    /**
     * 获取主品牌名称
     *
     * @return 主品牌名称
     */
    public String getMainBrandName() {
        return this.mainBrandName;
    }

    /**
     * 设置主品牌名称
     *
     * @param mainBrandName 主品牌名称
     */
    public void setMainBrandName(String mainBrandName) {
        this.mainBrandName = mainBrandName;
    }

    /**
     * 获取品牌名称
     *
     * @return 品牌名称
     */
    public String getBrandName() {
        return this.brandName;
    }

    /**
     * 设置品牌名称
     *
     * @param brandName 品牌名称
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 获取商品图片 多张以^分隔
     *
     * @return 商品图片 多张以^分隔
     */
    public String getGoodsPhotos() {
        return this.goodsPhotos;
    }

    /**
     * 设置商品图片 多张以^分隔
     *
     * @param goodsPhotos 商品图片 多张以^分隔
     */
    public void setGoodsPhotos(String goodsPhotos) {
        this.goodsPhotos = goodsPhotos;
    }



    /**
     * 获取商品原价(单位-分)
     *
     * @return 商品原价(单位-分)
     */
    public Long getOriginalPrice() {
        return this.originalPrice;
    }

    /**
     * 设置商品原价(单位-分)
     *
     * @param originalPrice 商品原价(单位-分)
     */
    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    /**
     * 获取实际支付金额(单位-分)
     *
     * @return 实际支付金额(单位-分)
     */
    public Long getPayPrice() {
        return this.payPrice;
    }

    /**
     * 设置实际支付金额(单位-分)
     *
     * @param payPrice 实际支付金额(单位-分)
     */
    public void setPayPrice(Long payPrice) {
        this.payPrice = payPrice;
    }

    /**
     * 获取优惠金额(单位-分)
     *
     * @return 优惠金额(单位-分)
     */
    public Long getCouponAmount() {
        return this.couponAmount;
    }

    /**
     * 设置优惠金额(单位-分)
     *
     * @param couponAmount 优惠金额(单位-分)
     */
    public void setCouponAmount(Long couponAmount) {
        this.couponAmount = couponAmount;
    }


    public List<OrderAttrPO> getOrderAttrPOS() {
        return orderAttrPOS;
    }

    public void setOrderAttrPOS(List<OrderAttrPO> orderAttrPOS) {
        this.orderAttrPOS = orderAttrPOS;
    }

    /**
     * 价格处理
     * @return
     */
    public String getRealPayPrice(){
        if (RStr.isNotEmpty(this.getPayPrice())){
            return StringUtil.changeF2Y(OrderGoodsItemPO.this.getPayPrice().toString());
        }
        return null;
    }
    public String getGoodsImage() {
        if(RStr.isNotEmpty(this.goodsPhotos)){
            String[] images = this.goodsPhotos.split("\\^");
            return images[0];
        }
        return goodsPhotos;
    }
}