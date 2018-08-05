package com.blueteam.wineshop.service;

import com.blueteam.entity.po.Apkset;

/**
 * @author Marx
 * <p>
 * CouponInfoService.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface ApkSetService {

    /**
     * @param apkSet
     * @return
     */
    int insertApkSet(Apkset apkSet);

    /**
     * @param apkSet
     * @return
     */
    int updateapk(Apkset apkSet);

    /**
     * @param apk_id
     * @return
     */
    Apkset getApk(Integer apk_id);

    /**
     * @return
     */
    Apkset SelApk();
}
