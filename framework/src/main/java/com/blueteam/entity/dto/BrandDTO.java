package com.blueteam.entity.dto;

import com.blueteam.base.util.FieldValidate;

import java.io.Serializable;

import static com.blueteam.base.constant.FieldValidateConstants.*;

/**
 * 品牌信息DTO
 * Created by  NastyNas on 2017/10/20.
 */
public class BrandDTO implements Serializable {
    //校验类别
    String validateType;
    @FieldValidate(notNullValidateTypes = {MAIN_BRAND_EDIT_DTO, MAIN_BRAND_REMOVE_DTO, BRAND_ADD_DTO})
    Integer mainBrandId;
    @FieldValidate(notNullValidateTypes = {MAIN_BRAND_ADD_DTO, MAIN_BRAND_EDIT_DTO})
    String mainBrandName;
    @FieldValidate(notNullValidateTypes = {MAIN_BRAND_ADD_DTO, MAIN_BRAND_EDIT_DTO})
    String mainBrandPhoto;
    @FieldValidate(notNullValidateTypes = {MAIN_BRAND_ADD_DTO, MAIN_BRAND_EDIT_DTO})
    String mainBrandDesc;
    @FieldValidate(notNullValidateTypes = {BRAND_EDIT_DTO, BRAND_REMOVE_DTO, BRAND_ENABLE_DTO, BRAND_DISABLE_DTO})
    Integer brandId;
    @FieldValidate(notNullValidateTypes = {BRAND_ADD_DTO, BRAND_EDIT_DTO})
    String brandName;
    @FieldValidate(notNullValidateTypes = {BRAND_ADD_DTO, BRAND_EDIT_DTO})
    String brandPhoto;
    @FieldValidate(notNullValidateTypes = {BRAND_ADD_DTO, BRAND_EDIT_DTO})
    String brandDesc;
    @FieldValidate(notNullValidateTypes = {BRAND_ADD_DTO, BRAND_EDIT_DTO})
    Integer brandGoodsType;

    public String getMainBrandName() {
        return mainBrandName;
    }

    public void setMainBrandName(String mainBrandName) {
        this.mainBrandName = mainBrandName;
    }


    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getBrandGoodsType() {
        return brandGoodsType;
    }

    public void setBrandGoodsType(Integer brandGoodsType) {
        this.brandGoodsType = brandGoodsType;
    }

    public String getBrandPhoto() {
        return brandPhoto;
    }

    public void setBrandPhoto(String brandPhoto) {
        this.brandPhoto = brandPhoto;
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    public String getMainBrandPhoto() {
        return mainBrandPhoto;
    }

    public void setMainBrandPhoto(String mainBrandPhoto) {
        this.mainBrandPhoto = mainBrandPhoto;
    }

    public String getMainBrandDesc() {
        return mainBrandDesc;
    }

    public void setMainBrandDesc(String mainBrandDesc) {
        this.mainBrandDesc = mainBrandDesc;
    }

    public Integer getMainBrandId() {
        return mainBrandId;
    }

    public void setMainBrandId(Integer mainBrandId) {
        this.mainBrandId = mainBrandId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getValidateType() {
        return validateType;
    }

    public void setValidateType(String validateType) {
        this.validateType = validateType;
    }

    @Override
    public String toString() {
        return "BrandDTO{" +
                "validateType='" + validateType + '\'' +
                ", mainBrandId=" + mainBrandId +
                ", mainBrandName='" + mainBrandName + '\'' +
                ", brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", brandGoodsType=" + brandGoodsType +
                ", mainBrandPhoto='" + mainBrandPhoto + '\'' +
                ", mainBrandDesc='" + mainBrandDesc + '\'' +
                ", brandPhoto='" + brandPhoto + '\'' +
                ", brandDesc='" + brandDesc + '\'' +
                '}';
    }
}
