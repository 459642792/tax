package com.blueteam.wineshop.mapper;

import com.blueteam.entity.dto.City;
import com.blueteam.entity.po.CityInfo;
import com.blueteam.entity.vo.UserCityVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Marx
 * <p>
 * CityInfoDao.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface CityInfoMapper {
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
     * 根据搜索条件查询城市 重复的
     *
     * @param search
     * @return
     */
    List<CityInfo> selectListForNotGYS(CityInfo search);

    List<CityInfo> selectListForNotGYSS(CityInfo search);

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

    List<CityInfo> listCityList(String Code);

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

    CityInfo getCityInfo(@Param("code") String code);

    void updateCityInfo(@Param("isExistVendor") String isExistVendor, @Param("code") String code);

    /**
     * 根据城市编码获取城市
     *
     * @param cityCode 城市编码
     * @return
     */
    City selectCityByCode(String cityCode);

    List<UserCityVO> listUserCity();
}
