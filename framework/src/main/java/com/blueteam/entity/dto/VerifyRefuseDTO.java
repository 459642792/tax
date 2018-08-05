package com.blueteam.entity.dto;

import com.blueteam.base.util.FieldValidate;

/**
 * Created by  NastyNas on 2017/11/8.
 */
public class VerifyRefuseDTO {
    @FieldValidate(notNull = true)
    String verifyBarCode;
    @FieldValidate(notNull = true)
    Integer verifyRefuseCode;
    String verifyRefuseReason;

    public String getVerifyBarCode() {
        return verifyBarCode;
    }

    public void setVerifyBarCode(String verifyBarCode) {
        this.verifyBarCode = verifyBarCode;
    }

    public Integer getVerifyRefuseCode() {
        return verifyRefuseCode;
    }

    public void setVerifyRefuseCode(Integer verifyRefuseCode) {
        this.verifyRefuseCode = verifyRefuseCode;
    }

    public String getVerifyRefuseReason() {
        return verifyRefuseReason;
    }

    public void setVerifyRefuseReason(String verifyRefuseReason) {
        this.verifyRefuseReason = verifyRefuseReason;
    }
}
