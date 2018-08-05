package com.blueteam.wineshop.mapper;

import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.po.GoodsVendorInfoPO;
import com.blueteam.entity.po.GoodsVerifyInfoPO;
import com.blueteam.entity.po.VendorBrandInfoPO;
import com.blueteam.entity.vo.GoodsDetailVO;
import com.blueteam.entity.vo.GoodsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家 商品相关
 *
 * @author xiaojiang
 * @create 2017-10-28  15:14
 */
@Repository
public interface VendorByGoodsMapper {
    /**
     * 方法的功能描述:TODO 根据相关参数获取商家商品列表
     *
     * @return
     * @methodName
     * @param: vendorId 商家id
     * @param: brandGoodsType 商品类型
     * @param: sort 排序
     * @author xiaojiang 2017/10/30 15:51
     * @modifier
     * @since 1.4.0
     */
    PageResult<List<GoodsVO>> listGoodsByType(@Param("vendorId") Integer vendorId, @Param("brandGoodsType") Integer brandGoodsType, @Param("order") String order, @Param("orderType") String orderType
            , @Param("source") Integer source, @Param("vendorGoodsState") Integer vendorGoodsState, @Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize);

    /**
     * 方法的功能描述:TODO 新增审核数据
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/30 19:25
     * @modifier
     * @since 1.4.0
     */
    int insertGoodsVerifyInfo(GoodsVerifyInfoPO goodsVerifyInf);

    int updateGoodsVerifyByBarCode(GoodsVerifyInfoPO goodsVerifyInf);

    /**
     * 方法的功能描述: 查询商品是否在模板库中存在
     *
     * @return
     * @methodName
     * @ * @param: null
     * @author xiaojiang 2017/12/5 16:06
     * @modifier
     * @since 1.4.0
     */
    Map<String, Object> getGoodsByBrandId(@Param("barCode") String barCode, @Param("vendorId") Integer vendorId);

    /**
     * 方法的功能描述:TODO 根据商家id查询所有的审核商品列表
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/31 16:07
     * @modifier
     * @since 1.4.0
     */
    List<GoodsVO> listGoodsByVerify(@Param("vendorId") Integer vendorId);

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
    GoodsDetailVO getVendorByGoodsDetails(@Param("vendorId") Integer vendorId, @Param("goodsId") Long goodsId, @Param("source") Integer source);

    /**
     * 方法的功能描述:TODO 检索商品列表
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/11/1 16:58
     * @modifier
     * @since 1.4.0
     */
    PageResult<List<GoodsVO>> listMatchingGoodsByNameAndBarcode(@Param("matchingValue") String matchingValue, @Param("source") Integer source,
                                                                @Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize);

    /**
     * 方法的功能描述:TODO 保存商家商品
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/11/1 17:51
     * @modifier
     * @since 1.4.0
     */
    int saveGoodsVendorInfo(GoodsVendorInfoPO goodsVendorInfo);

    /**
     * 方法的功能描述:TODO 保存商家品牌
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/11/1 17:51
     * @modifier
     * @since 1.4.0
     */
    int saveVndorBrandInfo(VendorBrandInfoPO vendorBrandInfo);

    /**
     * 方法的功能描述:TODO 查询当前商品状态
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/11/1 17:51
     * @modifier
     * @since 1.4.0
     */
    Map<String, Object> getGoodsByStatusAndBrandId(Long goodsId);

    /**
     * 方法的功能描述:TODO 获取当前商家商品的状态
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/11/2 15:50
     * @modifier
     * @since 1.4.0
     */
    Map<String, Object> getVendorGoodsByStatus(@Param("goodsId") Long goodsId, @Param("vendorId") Integer vendorId);

    /**
     * 方法的功能描述:TODO 修改商家商品状态
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/11/2 18:00
     * @modifier
     * @since 1.4.0
     */
    int updateVendorGoodsByStatus(@Param("goodsId") Long goodsId, @Param("vendorId") Integer vendorId, @Param("vendorGoodsStatus") Integer vendorGoodsStatus);

    /**
     * 是否是主品牌
     * @param brandId
     * @return
     */
    int getIsMajor(@Param("brandId") Integer brandId,@Param("vendorId") Integer vendorId);
}