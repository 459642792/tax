package com.blueteam.entity.vo;

import com.blueteam.entity.po.CityInfo;

import java.util.List;

/**
 * @author xiaojiang
 * @create 2018-02-06  14:20
 */
public class UserCityVO {
    private  String  initial;
    List<CityInfo> listCityInfo;

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public List<CityInfo> getListCityInfo() {
        return listCityInfo;
    }

    public void setListCityInfo(List<CityInfo> listCityInfo) {
        this.listCityInfo = listCityInfo;
    }

    public UserCityVO() {
    }

    public UserCityVO(String initial, List<CityInfo> listCityInfo) {
        this.initial = initial;
        this.listCityInfo = listCityInfo;
    }
}
