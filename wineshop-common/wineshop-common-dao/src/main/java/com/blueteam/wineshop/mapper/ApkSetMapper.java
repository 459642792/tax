package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.Apkset;
import org.apache.ibatis.annotations.Param;

/**
 * @author Marx
 * <p>
 * CityInfoDao.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface ApkSetMapper {

    /**
     * apk新增
     */
    int insertapk(Apkset apkSet);

    /**
     * 修改apk
     */
    int updateapk(Apkset apkSet);

    /**
     * 查询apk详情
     */
    Apkset getApk(@Param("apk_id") int apk_id);

    /**
     * @return
     */
    Apkset SelApk();
}
