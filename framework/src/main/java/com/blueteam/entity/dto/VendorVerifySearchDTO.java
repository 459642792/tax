package com.blueteam.entity.dto;

/**
 * 商家审核信息DTO
 * Created by  NastyNas on 2017/11/8.
 */
public class VendorVerifySearchDTO extends BasePageSearch {
    //审核二维码
    String verifyBarCode;

    public String getVerifyBarCode() {
        return verifyBarCode;
    }

    public void setVerifyBarCode(String verifyBarCode) {
        this.verifyBarCode = verifyBarCode;
    }
}
