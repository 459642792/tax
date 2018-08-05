package com.blueteam.entity.dto;

/**
 * Created by libra on 2017/4/12.
 */
public class ToutuSearch extends BasePageSearch {
    /**
     * 显示地区
     */
    private String cityName;

    /**
     * 类型
     */
    private String typeCode;

    /**
     * 品牌ID
     */
    private Integer pinpaiId;

    public Integer getPinpaiId() {
        return pinpaiId;
    }

    public void setPinpaiId(Integer pinpaiId) {
        this.pinpaiId = pinpaiId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
