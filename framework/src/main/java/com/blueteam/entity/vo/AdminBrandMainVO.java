package com.blueteam.entity.vo;

import java.util.List;

/**
 * 后台管理系统 主品牌VO
 * Created by  NastyNas on 2017/10/23.
 */
public class AdminBrandMainVO {
    Integer mainBrandId;
    String mainBrandName;
    String mainBrandUpdateTime;
    List<AdminBrandVO> brandVOList;

    public Integer getMainBrandId() {
        return mainBrandId;
    }

    public void setMainBrandId(Integer mainBrandId) {
        this.mainBrandId = mainBrandId;
    }

    public String getMainBrandName() {
        return mainBrandName;
    }

    public void setMainBrandName(String mainBrandName) {
        this.mainBrandName = mainBrandName;
    }

    public String getMainBrandUpdateTime() {
        return mainBrandUpdateTime;
    }

    public void setMainBrandUpdateTime(String mainBrandUpdateTime) {
        this.mainBrandUpdateTime = mainBrandUpdateTime;
    }

    public List<AdminBrandVO> getBrandVOList() {
        return brandVOList;
    }

    public void setBrandVOList(List<AdminBrandVO> brandVOList) {
        this.brandVOList = brandVOList;
    }
}
