package com.blueteam.entity.bo;

/**
 * Created by  NastyNas on 2018/1/10.
 */
public class AdminOrderGoodsBO {
    Long goodsId;
    Integer goodsNum;
    String goodsPrice;
    String goodsName;
    String goodsPhotos;
    String attrCode;
    String attrValueShowName;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrValueShowName() {
        return attrValueShowName;
    }

    public void setAttrValueShowName(String attrValueShowName) {
        this.attrValueShowName = attrValueShowName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPhotos() {
        return goodsPhotos;
    }

    public void setGoodsPhotos(String goodsPhotos) {
        this.goodsPhotos = goodsPhotos;
    }
}
