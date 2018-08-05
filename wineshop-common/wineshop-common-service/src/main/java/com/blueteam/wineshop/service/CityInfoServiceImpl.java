package com.blueteam.wineshop.service;

import com.blueteam.wineshop.mapper.CityInfoMapper;
import com.blueteam.entity.dto.City;
import com.blueteam.entity.po.CityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityInfoServiceImpl implements CityInfoService {

    @Autowired
    CityInfoMapper cityInfoMapper;

    @Override
    public List<CityInfo> CityInfoList(String PinYin) {
        return cityInfoMapper.CityInfoList(PinYin);
    }

    @Override
    public List<CityInfo> queryCity(String PinYin) {
        return cityInfoMapper.queryCity(PinYin);
    }

    @Override
    public List<CityInfo> queryCityList(String Code) {
        return cityInfoMapper.queryCityList(Code);
    }

    @Override
    public CityInfo selectCityCode(String Name) {
        return cityInfoMapper.selectCityCode(Name);
    }

    @Override
    public CityInfo selectCityName(String Code) {
        return cityInfoMapper.selectCityName(Code);
    }

    /**
     * 根据搜索条件查询城市
     *
     * @param search
     * @return
     */
    @Override
    public List<CityInfo> selectList(CityInfo search) {
        if (search == null)
            search = new CityInfo();
        return cityInfoMapper.selectList(search);
    }

    /**
     * 根据搜索条件查询城市 重复的
     *
     * @param search
     * @return
     */
    @Override
    public List<CityInfo> selectListForNotGYS(CityInfo search) {
        if (search == null)
            search = new CityInfo();
        return cityInfoMapper.selectListForNotGYS(search);
    }

    @Override
    public List<CityInfo> selectListForNotGYSS(CityInfo search) {
        if (search == null)
            search = new CityInfo();
        return cityInfoMapper.selectListForNotGYSS(search);
    }

    /**
     * 根据城市编码获取城市
     *
     * @param cityCode 城市编码
     * @return
     */
    @Override
    public City selectCityByCode(String cityCode) {
        return cityInfoMapper.selectCityByCode(cityCode);
    }
}
