package com.blueteam.entity.dto;

import com.blueteam.entity.po.*;

import java.util.List;

/**
 * 商品新增DTO
 * Created by  NastyNas on 2017/10/30.
 */
public class GoodsAddDTO {
    GoodsInfoPO goodsPO;
    List<GoodsAttrInfoPO> goodsAttrPOList;
    List<GoodsPhotoInfoPO> goodsPhotoPOList;
    GoodsDetailInfoPO goodsDetailPO;
    List<GoodsVendorInfoPO> goodsVendorPOList;
    List<VendorBrandInfoPO> vendorBrandPOList;
    List<VendorBrandInfoPO> oldVendorBrandPOList;
    GoodsVerifyInfoPO goodsVerifyPO;

    public GoodsVerifyInfoPO getGoodsVerifyPO() {
        return goodsVerifyPO;
    }

    public void setGoodsVerifyPO(GoodsVerifyInfoPO goodsVerifyPO) {
        this.goodsVerifyPO = goodsVerifyPO;
    }

    public GoodsInfoPO getGoodsPO() {
        return goodsPO;
    }

    public void setGoodsPO(GoodsInfoPO goodsPO) {
        this.goodsPO = goodsPO;
    }

    public List<GoodsAttrInfoPO> getGoodsAttrPOList() {
        return goodsAttrPOList;
    }

    public void setGoodsAttrPOList(List<GoodsAttrInfoPO> goodsAttrPOList) {
        this.goodsAttrPOList = goodsAttrPOList;
    }

    public List<GoodsPhotoInfoPO> getGoodsPhotoPOList() {
        return goodsPhotoPOList;
    }

    public void setGoodsPhotoPOList(List<GoodsPhotoInfoPO> goodsPhotoPOList) {
        this.goodsPhotoPOList = goodsPhotoPOList;
    }

    public GoodsDetailInfoPO getGoodsDetailPO() {
        return goodsDetailPO;
    }

    public void setGoodsDetailPO(GoodsDetailInfoPO goodsDetailPO) {
        this.goodsDetailPO = goodsDetailPO;
    }

    public List<GoodsVendorInfoPO> getGoodsVendorPOList() {
        return goodsVendorPOList;
    }

    public void setGoodsVendorPOList(List<GoodsVendorInfoPO> goodsVendorPOList) {
        this.goodsVendorPOList = goodsVendorPOList;
    }

    public List<VendorBrandInfoPO> getVendorBrandPOList() {
        return vendorBrandPOList;
    }

    public void setVendorBrandPOList(List<VendorBrandInfoPO> vendorBrandPOList) {
        this.vendorBrandPOList = vendorBrandPOList;
    }

    public List<VendorBrandInfoPO> getOldVendorBrandPOList() {
        return oldVendorBrandPOList;
    }

    public void setOldVendorBrandPOList(List<VendorBrandInfoPO> oldVendorBrandPOList) {
        this.oldVendorBrandPOList = oldVendorBrandPOList;
    }

    @Override
    public String toString() {
        return "GoodsAddDTO{" +
                "goodsPO=" + goodsPO +
                ", goodsAttrPOList=" + goodsAttrPOList +
                ", goodsPhotoPOList=" + goodsPhotoPOList +
                ", goodsDetailPO=" + goodsDetailPO +
                ", goodsVendorPOList=" + goodsVendorPOList +
                ", vendorBrandPOList=" + vendorBrandPOList +
                ", oldVendorBrandPOList=" + oldVendorBrandPOList +
                '}';
    }
}
