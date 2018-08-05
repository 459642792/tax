package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.VisitTrackInfoPO;
import org.apache.ibatis.annotations.Param;

public interface VisitTrackInfoMapper {
    /**
     * 方法的功能描述:TODO 添加记录
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/19 14:49
     * @modifier
     * @since 1.4.0
     */
    int insertVisitTrackInfo(VisitTrackInfoPO record);

    /**
     * 方法的功能描述:TODO 根据用户id 商家id 获取用户是否访问
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/19 14:32
     * @modifier
     * @since 1.4.0
     */
    Integer getVisitTrackToVendorId(@Param("vendorId") Integer vendorId, @Param("userId") Integer userId);
}