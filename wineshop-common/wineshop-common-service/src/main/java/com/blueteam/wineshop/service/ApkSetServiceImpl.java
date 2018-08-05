package com.blueteam.wineshop.service;

import com.blueteam.wineshop.mapper.ApkSetMapper;
import com.blueteam.entity.po.Apkset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApkSetServiceImpl implements ApkSetService {

    @Autowired
    ApkSetMapper apkInfoDao;

    @Override
    public Apkset getApk(Integer apk_id) {
        return apkInfoDao.getApk(apk_id);
    }

    @Override
    public int insertApkSet(Apkset apkSet) {
        return apkInfoDao.insertapk(apkSet);
    }

    @Override
    public int updateapk(Apkset apkSet) {
        return apkInfoDao.updateapk(apkSet);
    }


    @Override
    public Apkset SelApk() {
        return apkInfoDao.SelApk();
    }
}

