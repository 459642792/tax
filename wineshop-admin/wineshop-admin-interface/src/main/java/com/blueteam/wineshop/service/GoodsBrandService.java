package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.BrandSearchDTO;
import com.blueteam.entity.po.BrandMainInfoPO;
import com.blueteam.entity.po.BrandSubInfoPO;
import com.blueteam.entity.vo.AdminBrandVO;

import java.util.List;
import java.util.Map;

/**
 * Created by  NastyNas on 2017/10/19.
 */
public interface GoodsBrandService {

    /**
     * 根据名称查询主品牌数量
     *
     * @param brandName
     * @return
     */
    Integer countMainBrandByName(String brandName);

    /**
     * 新增主品牌
     *
     * @param brandMainInfoPO
     * @return
     */
    Integer saveMainBrand(BrandMainInfoPO brandMainInfoPO);

    /**
     * 根据主品牌id查询主品牌信息
     *
     * @param mainBrandId
     * @return
     */
    Map getMainBrandInfo(Integer mainBrandId);

    /**
     * 更新主品牌
     *
     * @param brandMainInfoPO
     * @return
     */
    Integer updateMainBrand(BrandMainInfoPO brandMainInfoPO);

    /**
     * 删除主品牌
     *
     * @param mainBrandId
     * @return
     */
    Integer removeMainBrand(Integer mainBrandId);

    /**
     * 查询主品牌是否绑定商品
     *
     * @param mainBrandId
     * @return
     */
    Integer countMainBrandGoods(Integer mainBrandId);

    /**
     * 查询主品牌下是否有子品牌
     *
     * @param mainBrandId
     * @return
     */
    Integer countSubBrand(Integer mainBrandId);

    /**
     * 根据主品牌id和子品牌名称，查询主品牌下是否存在该名称子品牌
     *
     * @param mainBrandId
     * @param brandName
     * @return
     */
    Integer countSubBrandByName(Integer mainBrandId, String brandName);

    /**
     * 新增子品牌
     *
     * @param brandSubInfoPO
     * @return
     */
    void saveSubBrand(BrandSubInfoPO brandSubInfoPO) throws Exception;

    /**
     * 根据品牌id获取品牌信息
     *
     * @param brandId
     * @return
     */
    Map getSubBrandInfo(Integer brandId);

    /**
     * 查询品牌id是否绑定商品
     *
     * @param brandId
     * @return
     */
    Integer countSubBrandGoods(Integer brandId);

    /**
     * 更新子品牌信息
     *
     * @param brandSubInfoPO
     * @return
     */
    void updateSubBrand(BrandSubInfoPO brandSubInfoPO) throws Exception;

    /**
     * 删除子品牌
     *
     * @param brandId
     * @return
     */
    Integer removeSubBrand(Integer brandId);


    /**
     * 查询品牌列表
     *
     * @param brandSearchDTO
     * @return
     */
    List listBrandInfo(BrandSearchDTO brandSearchDTO);

    /**
     * 绑定商品的品牌禁用
     *
     * @param brandSubInfoPO
     * @return
     */
    void disableBrandWithGoods(BrandSubInfoPO brandSubInfoPO) throws Exception;

    /**
     * 未绑定商品的品牌禁用
     *
     * @param brandSubInfoPO
     * @return
     */
    void disableBrandWithoutGoods(BrandSubInfoPO brandSubInfoPO) throws Exception;


    /**
     * 根据主品牌名称模糊查询主品牌列表
     *
     * @param mainBrandName
     * @return
     */
    List<Map> listMainBrand(String mainBrandName);

    /**
     * 根据主品牌id查询子品牌列表
     *
     * @param mainBrandId
     * @return
     */
    List<AdminBrandVO> listSubBrand(Integer mainBrandId);

    /**
     * 启用品牌
     *
     * @param brandSubInfoPO
     * @return
     */
    void enableBrand(BrandSubInfoPO brandSubInfoPO) throws Exception;

    /**
     * 根据主品牌id和商品类别id查询子品牌列表
     *
     * @param mainBrandId
     * @param goodsTypeId
     * @return
     */
    List<AdminBrandVO> listSubBrandTypeLimited(Integer mainBrandId, Integer goodsTypeId);
}
