package com.blueteam.entity.vo;

import java.util.List;

/**
 * Created by  NastyNas on 2018/1/10.
 */
public class AdminOrderDetailGoodsVO {
    //商品id
    Long goodsId;
    //商品名称
    String goodsName;
    //商品图片
    String goodsPhoto;
    //商品数量
    Integer goodsNum;
    //商品价格
    String goodsPrice;
    //商品展示属性列表
    List<AdminGoodsListAttrVO> attrVOList;

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

    public List<AdminGoodsListAttrVO> getAttrVOList() {
        return attrVOList;
    }

    public void setAttrVOList(List<AdminGoodsListAttrVO> attrVOList) {
        this.attrVOList = attrVOList;
    }

    public String getGoodsPhoto() {
        return goodsPhoto;
    }

    public void setGoodsPhoto(String goodsPhoto) {
        this.goodsPhoto = goodsPhoto;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
