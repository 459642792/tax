package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.ReVendor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Marx
 * <p>
 * CityInfoDao.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface ReVendorMapper {

    /**
     * 新增推荐商家
     *
     * @param reVendor
     * @return
     */
    int insert(ReVendor reVendor);

    /**
     * 修改推荐商家
     *
     * @param reVendor
     * @return
     */
    int update(ReVendor reVendor);

    /**
     * * @param TradingArea
     *
     * @return
     */
    List<ReVendor> listReVendor(@Param("VendorName") String VendorName, @Param("TradingArea") String TradingArea, @Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex);

    /**
     * @param VendorName
     * @param TradingArea
     * @return
     */
    int ReVendorCount(@Param("VendorName") String VendorName, @Param("TradingArea") String TradingArea);


    /**
     * @return
     */
    int MaxOrderField(@Param("AreaAddr") String AreaAddr);


    /**
     * 删除推荐商家
     *
     * @param Id
     * @return
     */
    int DeleteReVendor(@Param("Id") Integer Id);

    /**
     * 按区域查询推荐商家
     *
     * @param AreaAddr
     * @return
     */
    List<ReVendor> listReVendor2(@Param("AreaAddr") String AreaAddr);

    /**
     * 查询该推荐商家的信息
     *
     * @param Id
     * @return
     */
    ReVendor revendorDetail(@Param("Id") int Id);

    /**
     * 修改点击数量
     *
     * @param reVendor
     * @return
     */
    int updateClick(ReVendor reVendor);
}
