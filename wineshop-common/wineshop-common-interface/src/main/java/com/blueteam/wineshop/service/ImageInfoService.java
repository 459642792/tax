package com.blueteam.wineshop.service;

import com.blueteam.entity.po.ImageInfo;

import java.util.List;

/**
 * @author Marx
 * <p>
 * ImageInfoService.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface ImageInfoService {

    /**
     * @param Type
     * @param ForeignKey
     * @return
     */
    List<ImageInfo> ImageInfoList(String Type, int ForeignKey);

    /**
     * @param imageInfo
     * @return
     */
    int insertImage(ImageInfo imageInfo);

    /**
     * 顶部头图前置
     *
     * @param Id 图片Id
     * @return
     * @throws Exception
     */
    int raiseUpHeadImage(Integer Id) throws Exception;

    List<ImageInfo> getImagesByType(int vendorId, String type);

    //删除某张头图
    int removeHeadImage(Integer Id);

    int findMaxSortNumberOfHeadImg(Integer vendorId);

    /**
     * 删除图片的相关信息
     *
     * @param
     * @return
     */
    int deleteByPrimaryKey(int ExtendId, String Type);
}

