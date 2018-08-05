package com.blueteam.wineshop.service;


import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.ToutuSearch;
import com.blueteam.entity.po.AdvertiseInfo;
import com.blueteam.entity.dto.PageResult;

import java.util.List;

/**
 * @author Marx
 * <p>
 * AdvertiseInfoService.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface AdvertiseInfoService {

    /**
     * @param TypeCode
     * @param CityCode
     * @return
     */
    List<AdvertiseInfo> AdvertiseInfoList(String TypeCode, String CityCode);

    /**
     * @param ForeignKey
     * @param TypeCode
     * @return
     */
    List<AdvertiseInfo> VendorTopList(int ForeignKey, String TypeCode);

    /**
     * @param ForeignKey
     * @param TypeCode
     * @return
     */
    List<AdvertiseInfo> SdppList(int ForeignKey, String TypeCode);

    /**
     * @param ForeignKey
     * @param TypeCode
     * @return
     */
    List<AdvertiseInfo> VendorPhotoList(int ForeignKey, String TypeCode);

    /**
     * 获取运营商管辖区域内的所有广告
     *
     * @param carriersId 运营商ID
     * @param typeCode   广告类型编码
     * @param cityCode   区域Code 主要为以后管理多地区准备
     * @return
     */
    List<AdvertiseInfo> selectAdvByYYS(int carriersId, String typeCode, String cityCode);

    /**
     * 运营商广告插入
     *
     * @param adv
     * @param userId 运营商用户ID
     * @return
     */
    BaseResult yysInsert(int userId, AdvertiseInfo adv);

    /**
     * 运营商广告编辑
     *
     * @param adv
     * @param userId 运营商用户ID
     * @return
     */

    BaseResult yysUpdate(int userId, AdvertiseInfo adv);

    /**
     * 交换SortNumber 升序交换
     * 指和比自己小的sortNumber进行交换，没有则不交换
     *
     * @param id         待交换的广告ID
     * @param typeCode
     * @param carriersId 运营商ID
     * @param cityCode   区域code    这里先用云运营商的区域Code,后面会使用传入的cityCode
     * @return
     */
    int switchSortNum(int id, String typeCode, int carriersId, String cityCode);

    /**
     * 删除运营商广告
     *
     * @param userId 运营商用户ID
     * @param advId  要删除的广告ID
     * @return
     */
    BaseResult deleteYysAdv(int userId, int advId);

    int save(AdvertiseInfo model);

    public int findMaxSortNumberOfHeadImg(Integer vendorId);

    List<AdvertiseInfo> getImagesByType(int vendorId, String type);

    //删除某张头图
    int removeHeadImage(Integer Id);

    int raiseUpHeadImage(Integer id);

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

    /**
     * @param adv
     * @return
     */
    int update(AdvertiseInfo adv);

    /**
     * 管理员编辑广告
     *
     * @param adv 广告信息
     * @return
     */
    BaseResult adminUpdate(AdvertiseInfo adv);

    /**
     * 管理员广告插入
     *
     * @param adv
     * @return
     */
    BaseResult adminInsert(AdvertiseInfo adv);


    /**
     * 获取图片列表
     * @return
     */
    BaseResult getVendorImagesByForeignKey(String foreignKey);

}