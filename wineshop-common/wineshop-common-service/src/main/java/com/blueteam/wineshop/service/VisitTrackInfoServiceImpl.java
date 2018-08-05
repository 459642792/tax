package com.blueteam.wineshop.service;

import com.blueteam.entity.po.VisitTrackInfoPO;
import com.blueteam.wineshop.mapper.VisitTrackInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VisitTrackInfoServiceImpl implements VisitTrackInfoService {

    @Autowired
    VisitTrackInfoMapper visitInfoDao;

    /**
     * 方法的功能描述:TODO 保存浏览记录 ps改动之前代码
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/19 14:27
     * @modifier
     * @since 1.4.0
     */
    @Override
    public int insertVisitTrackInfo(Integer vendorId, Integer userId) {
        VisitTrackInfoPO visitTrackInfo = new VisitTrackInfoPO();
        visitTrackInfo.setForeignid(vendorId);
        visitTrackInfo.setUserId(userId);
        visitTrackInfo.setVisittype(10);
        visitTrackInfo.setVisittime(new Date());
        Integer count = getVisitTrackToVendorId(visitTrackInfo.getForeignid(), visitTrackInfo.getUserId());
        return count == 0 ? visitInfoDao.insertVisitTrackInfo(visitTrackInfo) : 0;
    }

    /**
     * 方法的功能描述:TODO 根据用户id 商家id 获取用户是否访问
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/19 14:27
     * @modifier
     * @since 1.4.0
     */
    public Integer getVisitTrackToVendorId(Integer vendorId, Integer userId) {
        return visitInfoDao.getVisitTrackToVendorId(vendorId, userId);
    }
}
