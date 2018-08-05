package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.BaseResult;

import java.util.List;
import java.util.Map;

/**
 * 品牌终端相关接口
 *
 * @author xiaojiang
 * @create 2017-10-18  10:14
 */
public interface BrandInfoService {
    /**
     * 方法的功能描述:TODO 根据商家id获取所有商家关联品牌（可根据状态删选 如 全部请传null）
     *
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @methodName listVendorByBrand
     * @param: vendorId 商家id
     * @param: authorityTag 是否有效
     * @param: brandGoodsType 分类编号 如 白酒1101
     * @author xiaojiang 2017/10/20 10:12
     * @modifier
     * @since 1.4.0
     */
    BaseResult listVendorByBrand(Integer vendorId, Integer authorityTag, Integer brandGoodsType);

    /**
     * 方法的功能描述:TODO 根据商家获取所有分类和所有品牌列表
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName listShopsByTypeBrand
     * @param: vendorId
     * @author xiaojiang 2017/10/28 16:30
     * @modifier
     * @since 1.4.0
     */
    BaseResult listShopsByTypeBrand(Integer vendorId);

    /**
     * 方法的功能描述:TODO 根据商家id和品牌id 获取所有商家商品列表
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/23 10:19
     * @modifier
     * @since 1.4.0
     */

    BaseResult listBrandByGoods(Integer vendorId, Integer brandId, Integer source, Integer pageIndex, Integer pageSize);

    /**
     * 方法的功能描述:TODO 根据商家id商品id获取商品详情
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/24 15:57
     * @modifier
     * @since 1.4.0
     */
    BaseResult getGoodsDetails(Integer vendorId, Long goodsId, Integer source);

    /**
     * 方法的功能描述:TODO 模糊匹配商家商品列表
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/24 17:23
     * @modifier
     * @since 1.4.0
     */
    BaseResult listMatchingGoods(Integer vendorId, String goodsName, Integer source, Integer pageIndex, Integer pageSize);

    /**
     * 方法的功能描述:TODO 根据主品牌查询附件经营该品牌的商家（20大品牌页面）
     *
     * @return
     * @methodName
     * @param: mainBrand 搜索的品牌名称
     * @param: Longitude 用户当前经度
     * @param: Latitude 用户当前纬度
     * @author huangqijun 2018/1/8 14:10
     * @modifier
     * @since 1.4.0
     */
    BaseResult listVendorsByMainBrand(String mainBrand, Double Longitude, Double Latitude,int pageIndex,int pageSize);
}


