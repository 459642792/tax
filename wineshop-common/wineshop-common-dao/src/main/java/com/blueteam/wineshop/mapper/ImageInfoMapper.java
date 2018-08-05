package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.ImageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Marx
 * <p>
 * ImageInfoDao.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface ImageInfoMapper {
    /**
     * @param Type
     * @param ForeignKey
     * @return
     */
    List<ImageInfo> ImageInfoList(@Param("Type") String Type, @Param("ForeignKey") int ForeignKey);

    /**
     * @param imageInfo
     * @return
     */
    int insertImage(ImageInfo imageInfo);

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

    ImageInfo findMaxSortNumber(@Param("vendorId") Integer vendorId,
                                @Param("imageType") String imageType);

    /**
     * 根据图片类型和外键查询图片信息
     *
     * @param vendorId   图片外键
     * @param vendorType 图片类型
     * @return
     */
    List<ImageInfo> getImagesByType(@Param("vendorId") int vendorId,
                                    @Param("vendorType") String vendorType);

    /**
     * 根据图片Id,Type查询某种类型图片信息,并按照SortNumber升序排序
     * 此方法用于对图片的前置操作
     *
     * @param id
     * @param type
     * @return
     */
    List<ImageInfo> getImagesByIdAndType(@Param("id") Integer id, @Param("type") String type);

    int removeHeadImage(Integer Id);

    /**
     * 删除图片信息
     *
     * @param Type
     * @return
     */
    int deleteByPrimaryKey(@Param("ExtendId") int ExtendId, @Param("Type") String Type);
}
