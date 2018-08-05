package com.blueteam.entity.dto;

import com.blueteam.entity.po.CityInfo;

import java.util.List;


/**
 * @author Marx
 * <p>
 * ExclusiveCityInfo.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public class ExclusiveCityInfo {

    private String pinyin;

    private List<CityInfo> lstCityInfo;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public List<CityInfo> getLstCityInfo() {
        return lstCityInfo;
    }

    public void setLstCityInfo(List<CityInfo> lstCityInfo) {
        this.lstCityInfo = lstCityInfo;
    }
}
