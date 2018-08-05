package com.blueteam.entity.vo;

import java.util.List;

/**
 * Created by  NastyNas on 2017/11/1.
 */
public class AdminGoodsEditShowVO {
    Long goodsId;
    Integer goodsState;
    String goodsName;
    String barCode;
    Integer mainBrandId;
    String mainBrandName;
    //品牌列表VO
    List<AdminBrandVO> brandList;
    Integer goodsType;
    String goodsTypeName;
    String goodsDesc;
    String suggestPrice;
    //基础属性列表VO
    List<AdminGoodsAttrVO> baseAttrShowVOList;
    //特有属性列表VO
    List<AdminGoodsAttrVO> specialAttrShowVOList;
    //图片列表VO
    List<AdminPhotoVO> photoList;

    public String getMainBrandName() {
        return mainBrandName;
    }

    public void setMainBrandName(String mainBrandName) {
        this.mainBrandName = mainBrandName;
    }

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

    public List<AdminBrandVO> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<AdminBrandVO> brandList) {
        this.brandList = brandList;
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

    public String getSuggestPrice() {
        return suggestPrice;
    }

    public void setSuggestPrice(String suggestPrice) {
        this.suggestPrice = suggestPrice;
    }

    public List<AdminGoodsAttrVO> getBaseAttrShowVOList() {
        return baseAttrShowVOList;
    }

    public void setBaseAttrShowVOList(List<AdminGoodsAttrVO> baseAttrShowVOList) {
        this.baseAttrShowVOList = baseAttrShowVOList;
    }

    public List<AdminGoodsAttrVO> getSpecialAttrShowVOList() {
        return specialAttrShowVOList;
    }

    public void setSpecialAttrShowVOList(List<AdminGoodsAttrVO> specialAttrShowVOList) {
        this.specialAttrShowVOList = specialAttrShowVOList;
    }

    public List<AdminPhotoVO> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<AdminPhotoVO> photoList) {
        this.photoList = photoList;
    }
}
