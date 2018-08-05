package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.PinPaiInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Marx
 * <p>
 * PinPaiInfoDao.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface PinPaiInfoMapper {
    /**
     * @param Status
     * @return
     */
    List<PinPaiInfo> PinPaiInfoList(@Param("Status") String Status);

    List<PinPaiInfo> listPinPaiInfo(List<Integer> Status);

    /**
     * @return
     */
    PinPaiInfo PinPaiName(@Param("Id") int Id);

    /**
     * 查询管理后台品牌库的数据
     *
     * @param Name
     * @param pageSize
     * @param pageIndex
     * @return
     */
    List<PinPaiInfo> adminPinpaiList(@Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex, @Param("Name") String Name);

    /**
     * 对查询管理后台品牌库的数据进行统计
     */
    List<PinPaiInfo> adminPinpaiCount(@Param("Name") String Name);


    /**
     * 管理后台品牌库的数据进行新增
     *
     * @return
     */
    int insertpinpai(PinPaiInfo objInfo);

    /**
     * 管理后台品牌库的数据进行修改操作
     */
    int updatepinpai(PinPaiInfo objInfo);

    /**
     * 方法的功能描述:TODO
     *
     * @param
     * @return
     * @methodName 后去所有品牌
     * @author xiaojiang 2017/7/27 20:09
     * @since 1.4.0
     */
    List<Map<String, Object>> listPinpai();
}
