package com.blueteam.wineshop.mapper;

import com.blueteam.entity.dto.ToutuSearch;
import com.blueteam.entity.po.AdvertiseInfo;
import com.blueteam.entity.dto.PageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Marx
 * <p>
 * AdvertiseInfoDao.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface AdvertiseInfoMapper {
    /**
     * @param TypeCode
     * @param CityCode
     * @return
     */
    List<AdvertiseInfo> AdvertiseInfoList(@Param("TypeCode") String TypeCode, @Param("CityCode") String CityCode);

    /**
     * @param ForeignKey
     * @param TypeCode
     * @return
     */
    List<AdvertiseInfo> VendorTopList(@Param("ForeignKey") int ForeignKey, @Param("TypeCode") String TypeCode);

    /**
     * @param TypeCode
     * @return
     */
    List<AdvertiseInfo> SdppList(@Param("ForeignKey") int ForeignKey, @Param("TypeCode") String TypeCode);

    /**
     * @param ForeignKey
     * @param TypeCode
     * @return
     */
    List<AdvertiseInfo> VendorPhotoList(@Param("ForeignKey") int ForeignKey, @Param("TypeCode") String TypeCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AdvertiseInfo
     *
     * @mbg.generated Sat Feb 18 11:02:07 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AdvertiseInfo
     *
     * @mbg.generated Sat Feb 18 11:02:07 CST 2017
     */
    int insert(AdvertiseInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AdvertiseInfo
     *
     * @mbg.generated Sat Feb 18 11:02:07 CST 2017
     */
    AdvertiseInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AdvertiseInfo
     *
     * @mbg.generated Sat Feb 18 11:02:07 CST 2017
     */
    List<AdvertiseInfo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AdvertiseInfo
     *
     * @mbg.generated Sat Feb 18 11:02:07 CST 2017
     */
    int updateByPrimaryKey(AdvertiseInfo record);

    /**
     * 获取运营商管辖区域内的所有广告
     *
     * @param carriersId 运营商ID
     * @param typeCode   广告类型编码
     * @param cityCode   地区编码 主要为以后管理多地区准备
     * @return
     */
    List<AdvertiseInfo> selectAdvByYYS(@Param("carriersId") int carriersId, @Param("typeCode") String typeCode, @Param("cityCode") String cityCode);

    /**
     * 交换SortNumber 升序交换
     * 指和比自己小的sortNumber进行交换，没有则不交换
     *
     * @param id       待交换的广告ID
     * @param typeCode
     * @param
     * @return
     */
    int switchSortNum(@Param("id") int id, @Param("typeCode") String typeCode, @Param("carriersId") int carriersId, @Param("cityCode") String cityCode);

    AdvertiseInfo findMaxSortNumber(@Param("vendorId") Integer vendorId, @Param("imageType") String imageType);

    List<AdvertiseInfo> getImagesByType(@Param("vendorId") Integer vendorId, @Param("type") String type);

    int removeHeadImage(Integer id);

    List<AdvertiseInfo> getImagesByIdAndType(@Param("id") Integer id, @Param("type") String type);

    /**
     * 交换两条记录的SortNumber
     *
     * @param firstId          第一张图片的Id
     * @param secondId         第二张图片的Id
     * @param firstSortNumber  第一张图片的SortNumber
     * @param secondSortNumber 第二张图片的SortNumber
     * @return
     */
    int swapSortNumber(@Param("firstId") Integer firstId,
                       @Param("secondId") Integer secondId,
                       @Param("firstSortNumber") Integer firstSortNumber,
                       @Param("secondSortNumber") Integer secondSortNumber);

    /**
     * 根据搜索条件分页查询广告
     *
     * @param search 搜索条件
     * @return
     */
    PageResult<List<AdvertiseInfo>> selectAdvByWhere(ToutuSearch search);

    /**
     * 根据广告ID获取广告详情
     *
     * @param id
     * @return
     */
    AdvertiseInfo findAdvByID(int id);

    int updates(AdvertiseInfo adv);

    /**
     * 获取店铺门脸照和周围环境图
     * @param foreignKey
     * @param types
     * @return
     */
    List<AdvertiseInfo> getVendorImagesByForeignKey(@Param("foreignKey") String foreignKey,@Param("types") String[] types);
}