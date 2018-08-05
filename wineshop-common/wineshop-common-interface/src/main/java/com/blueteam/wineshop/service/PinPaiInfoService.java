package com.blueteam.wineshop.service;


import com.blueteam.entity.po.PinPaiInfo;

import java.util.List;

/**
 * @author Marx
 * <p>
 * PinPaiInfoService.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface PinPaiInfoService {

    /**
     * @param Status
     * @return
     */
    List<PinPaiInfo> PinPaiInfoList(String Status);

    List<PinPaiInfo> listPinPaiInfo(String Status);


    /**
     * @param
     * @return
     */
    PinPaiInfo PinPaiName(int Id);

    /**
     * 对平台后台管理的数据进行列表性查询
     */
    List<PinPaiInfo> adminPinpaiList(Integer pageSize, Integer pageIndex, String Name);

    /**
     * 获取平台后台管理数据中品牌管理的总条数
     */
    List<PinPaiInfo> adminPinpaiCount(String Name);

    /**
     * 新增平台后台管理数据中品牌管理的数据
     */
    int insertpinpai(PinPaiInfo objInfo);

    /**
     * 修改平台后台管理数据中品牌管理的数据
     */
    int updatepinpai(PinPaiInfo objInfo);
}
