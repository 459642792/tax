package com.blueteam.wineshop.service;

import com.blueteam.entity.po.VisitTrackInfoPO;
import org.omg.CORBA.INTERNAL;

/**
 * @author Marx
 * <p>
 * VisitTrackInfoService.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface VisitTrackInfoService {

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
    int insertVisitTrackInfo(Integer vendorId, Integer userId);

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
    Integer getVisitTrackToVendorId(Integer vendorId, Integer userId);
}
