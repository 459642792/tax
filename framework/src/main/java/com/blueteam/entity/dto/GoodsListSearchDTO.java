package com.blueteam.entity.dto;

/**
 * 商品列表搜索dto
 * Created by  NastyNas on 2017/11/6.
 */
public class GoodsListSearchDTO extends BasePageSearch {
    /*
     *基础查询
     */
    Integer goodsType;
    String suggestPriceFrom;
    String suggestPriceTo;
    String searchKey;
    /*
     *特定属性值查询
     */
    String alcoholAttrCode;
    String alcoholValueNameFrom;
    String alcoholValueNameTo;

    String packageAttrCode;
    String packageAttrValueCode;
    /*
     *排序：0-降序  1-升序
     */
    Integer suggestPriceTag;
    Integer goodsStateTag;


    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getSuggestPriceFrom() {
        return suggestPriceFrom;
    }

    public void setSuggestPriceFrom(String suggestPriceFrom) {
        this.suggestPriceFrom = suggestPriceFrom;
    }

    public String getSuggestPriceTo() {
        return suggestPriceTo;
    }

    public void setSuggestPriceTo(String suggestPriceTo) {
        this.suggestPriceTo = suggestPriceTo;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getAlcoholAttrCode() {
        return alcoholAttrCode;
    }

    public void setAlcoholAttrCode(String alcoholAttrCode) {
        this.alcoholAttrCode = alcoholAttrCode;
    }

    public String getAlcoholValueNameFrom() {
        return alcoholValueNameFrom;
    }

    public void setAlcoholValueNameFrom(String alcoholValueNameFrom) {
        this.alcoholValueNameFrom = alcoholValueNameFrom;
    }

    public String getAlcoholValueNameTo() {
        return alcoholValueNameTo;
    }

    public void setAlcoholValueNameTo(String alcoholValueNameTo) {
        this.alcoholValueNameTo = alcoholValueNameTo;
    }

    public String getPackageAttrCode() {
        return packageAttrCode;
    }

    public void setPackageAttrCode(String packageAttrCode) {
        this.packageAttrCode = packageAttrCode;
    }

    public String getPackageAttrValueCode() {
        return packageAttrValueCode;
    }

    public void setPackageAttrValueCode(String packageAttrValueCode) {
        this.packageAttrValueCode = packageAttrValueCode;
    }

    public Integer getSuggestPriceTag() {
        return suggestPriceTag;
    }

    public void setSuggestPriceTag(Integer suggestPriceTag) {
        this.suggestPriceTag = suggestPriceTag;
    }

    public Integer getGoodsStateTag() {
        return goodsStateTag;
    }

    public void setGoodsStateTag(Integer goodsStateTag) {
        this.goodsStateTag = goodsStateTag;
    }
}
