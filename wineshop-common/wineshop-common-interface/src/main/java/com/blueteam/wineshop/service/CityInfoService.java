package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.City;
import com.blueteam.entity.po.CityInfo;

import java.util.List;

/**
 * @author Marx
 * <p>
 * CityInfoService.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface CityInfoService {

    /**
     * @param PinYin
     * @return
     */
    List<CityInfo> CityInfoList(String PinYin);


    /**
     * 根据搜索条件查询城市
     *
     * @param search
     * @return
     */
    List<CityInfo> selectList(CityInfo search);

    /**
     * @param PinYin
     * @return
     */
    List<CityInfo> queryCity(String PinYin);

    /**
     * @param Code
     * @return
     */
    List<CityInfo> queryCityList(String Code);

    /**
     * @param Name
     * @return
     */
    CityInfo selectCityCode(String Name);

    /**
     * @param Code
     * @return
     */
    CityInfo selectCityName(String Code);

    /**
     * 根据城市编码获取城市
     *
     * @param cityCode 城市编码
     * @return
     */
    City selectCityByCode(String cityCode);

    /**
     * 根据搜索条件查询城市 重复的
     *
     * @param search
     * @return
     */
    List<CityInfo> selectListForNotGYS(CityInfo search);

    List<CityInfo> selectListForNotGYSS(CityInfo search);
}
