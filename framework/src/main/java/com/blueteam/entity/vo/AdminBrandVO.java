package com.blueteam.entity.vo;

/**
 * 后台管理，子品牌VO
 * Created by  NastyNas on 2017/10/23.
 */
public class AdminBrandVO {
    Integer brandId;
    String brandName;
    String brandUpdateTime;
    Integer brandStateTag;
    Integer selectedFlag;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandUpdateTime() {
        return brandUpdateTime;
    }

    public void setBrandUpdateTime(String brandUpdateTime) {
        this.brandUpdateTime = brandUpdateTime;
    }

    public Integer getBrandStateTag() {
        return brandStateTag;
    }

    public void setBrandStateTag(Integer brandStateTag) {
        this.brandStateTag = brandStateTag;
    }

    public Integer getSelectedFlag() {
        return selectedFlag;
    }

    public void setSelectedFlag(Integer selectedFlag) {
        this.selectedFlag = selectedFlag;
    }
}
