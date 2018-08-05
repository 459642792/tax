package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.GoodsVendorDTO;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.po.GoodsVerifyInfoPO;
import com.blueteam.entity.vo.GoodsVO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 商家商品相关
 *
 * @author xiaojiang
 * @create 2017-10-28  15:12
 */
public interface VendorByGoodsService {
    /**
     * 方法的功能描述:TODO 根据相关参数获取商家商品列表
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName listValidTypeByGoods
     * @param: vendorId
     * @param: brandGoodsType 类型id
     * @param: order
     * @param: orderType
     * @param: source 来源
     * @param: type 状态
     * @param: pageIndex
     * @param: pageSize
     * @author xiaojiang 2017/10/30 18:53
     * @modifier
     * @since 1.4.0
     */
    BaseResult listGoodsByType(Integer vendorId, Integer brandGoodsType, String order, String orderType, Integer source, Integer type, Integer pageIndex, Integer pageSize);

    /**
     * 方法的功能描述:TODO 新增审核数据
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/30 19:26
     * @modifier
     * @since 1.4.0
     */
    BaseResult insertGoodsVerifyInfo(GoodsVerifyInfoPO goodsVerifyInf);

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
    BaseResult listGoodsByVerify(Integer vendorId);

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
    BaseResult getVendorByGoodsDetails(Integer vendorId, Long goodsId, Integer source);

    /**
     * 方法的功能描述:TODO 商家检索商品列表
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/11/1 16:59
     * @modifier
     * @since 1.4.0
     */
    BaseResult listMatchingGoodsByNameAndBarcode(String matchingValue, Integer source, Integer pageIndex, Integer pageSize);

    /**
     * 方法的功能描述:TODO 商家新增商品 商家商品 修改商品  下架商品
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/11/2 11:36
     * @modifier
     * @since 1.4.0
     */
    BaseResult saveChangeVendorByGoods(GoodsVendorDTO goodsVendorDTO);
}
