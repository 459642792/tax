package com.blueteam.wineshop.mapper;

import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.dto.VendorBrandSearchDTO;
import com.blueteam.entity.po.VendorInfo;
import com.blueteam.entity.vo.GoodsDetailVO;
import com.blueteam.entity.vo.GoodsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 品牌相关
 *
 * @author xiaojiang
 * @create 2017-10-18  10:12
 */
@Repository
public interface BrandInfoMapper {

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
    List<Map<String, Object>> listVendorByBrand(@Param("vendorId") Integer vendorId, @Param("authorityTag") Integer authorityTag, @Param("brandGoodsType") Integer brandGoodsType);

    /**
     * 方法的功能描述:TODO 根据商家获取所有分类和所有品牌列表
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/20 10:10
     * @modifier =======
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/20 10:10
     * @modifier
     * @since 1.4.0
     */
    List<Map<String, Object>> listShopsByTypeBrand(@Param("vendorId") Integer vendorId);

    List<Map<String, Object>> listVendorByTypeBrand(@Param("goodTypeId") Integer goodTypeId);

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
    PageResult<List<GoodsVO>> listBrandByGoods(@Param("vendorId") Integer vendorId, @Param("brandId") Integer brandId, @Param("source") Integer source,
                                               @Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize);

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
    GoodsDetailVO getGoodsDetails(@Param("vendorId") Integer vendorId, @Param("goodsId") Long goodsId, @Param("source") Integer source);

    /**
     * 方法的功能描述:TODO 模糊匹配商家商品列表
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/24 17:18
     * @modifier
     * @since 1.4.0
     */
    PageResult<List<GoodsVO>> listMatchingGoods(@Param("vendorId") Integer vendorId, @Param("goodsName") String goodsName, @Param("source") Integer source,
                                                @Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize);

    /**
     * 方法的功能描述:TODO 根据主品牌查询附件经营该品牌的商家（20大品牌页面）
     *
     * @return
     * @methodName
     * @param: mainBrand 搜索的品牌名称
     * @param: Longitude 用户当前经度
     * @param: Latitude 用户当前纬度
     * @author huangqijun 2018/1/9 10:10
     * @modifier
     * @since 1.4.0
     */
    List<VendorBrandSearchDTO> searchVendorByMainBrand(Map map);
}
