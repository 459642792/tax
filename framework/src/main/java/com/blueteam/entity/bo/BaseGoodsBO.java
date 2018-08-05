package com.blueteam.entity.bo;

/**
 * 单条商品信息整合对象
 * 涉及TF_G_GOODS TF_G_GOODS_DETAIL  TD_G_GOODS_TYPE TD_G_BRAND_MAIN
 * Created by  NastyNas on 2017/11/2.
 */
public class BaseGoodsBO {

    Long goodsId;
    Integer goodsState;
    String goodsName;
    String barCode;
    Integer mainBrandId;
    String mainBrandName;
    Integer brandId;
    Integer brandStateTag;
    String brandName;
    Integer goodsType;
    String goodsTypeName;
    String goodsDesc;
    Long suggestPrice;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(Integer goodsState) {
        this.goodsState = goodsState;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getMainBrandId() {
        return mainBrandId;
    }

    public void setMainBrandId(Integer mainBrandId) {
        this.mainBrandId = mainBrandId;
    }

    public String getMainBrandName() {
        return mainBrandName;
    }

    public void setMainBrandName(String mainBrandName) {
        this.mainBrandName = mainBrandName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Long getSuggestPrice() {
        return suggestPrice;
    }

    public void setSuggestPrice(Long suggestPrice) {
        this.suggestPrice = suggestPrice;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getBrandStateTag() {
        return brandStateTag;
    }

    public void setBrandStateTag(Integer brandStateTag) {
        this.brandStateTag = brandStateTag;
    }
}

