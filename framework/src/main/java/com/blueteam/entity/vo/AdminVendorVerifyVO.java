package com.blueteam.entity.vo;

import java.util.List;

/**
 * 商家信息vo
 * Created by  NastyNas on 2017/11/8.
 */
public class AdminVendorVerifyVO {
    String verifyBarCode;
    String vendorName;
    String verifyGoodsName;
    String submitTime;
    String verifySalePrice;
    Integer verifyGoodsState;
    String verifyGoodsPhoto;
    List<String> verifyPhotoList;

    public String getVerifyBarCode() {
        return verifyBarCode;
    }

    public void setVerifyBarCode(String verifyBarCode) {
        this.verifyBarCode = verifyBarCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVerifyGoodsName() {
        return verifyGoodsName;
    }

    public void setVerifyGoodsName(String verifyGoodsName) {
        this.verifyGoodsName = verifyGoodsName;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getVerifySalePrice() {
        return verifySalePrice;
    }

    public void setVerifySalePrice(String verifySalePrice) {
        this.verifySalePrice = verifySalePrice;
    }

    public Integer getVerifyGoodsState() {
        return verifyGoodsState;
    }

    public void setVerifyGoodsState(Integer verifyGoodsState) {
        this.verifyGoodsState = verifyGoodsState;
    }

    public String getVerifyGoodsPhoto() {
        return verifyGoodsPhoto;
    }

    public void setVerifyGoodsPhoto(String verifyGoodsPhoto) {
        this.verifyGoodsPhoto = verifyGoodsPhoto;
    }

    public List<String> getVerifyPhotoList() {
        return verifyPhotoList;
    }

    public void setVerifyPhotoList(List<String> verifyPhotoList) {
        this.verifyPhotoList = verifyPhotoList;
    }
}
