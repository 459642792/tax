package com.blueteam.wineshop.service;

import com.blueteam.base.constant.Constants;
import com.blueteam.base.util.MathUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.dto.VendorBrandSearchDTO;
import com.blueteam.entity.po.CouponInfo;
import com.blueteam.entity.po.VendorBrandInfoPO;
import com.blueteam.entity.vo.GoodsDetailVO;
import com.blueteam.entity.vo.GoodsVO;
import com.blueteam.wineshop.mapper.BrandInfoMapper;
import com.blueteam.wineshop.mapper.CouponInfoMapper;
import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌终端相关接口
 *
 * @author xiaojiang
 * @create 2017-10-18  10:15
 */
@Service
public class BrandInfoServiceImpl implements BrandInfoService {
    @Autowired
    BrandInfoMapper brandInfoMapper;


    @Autowired
    CouponInfoMapper couponInfoMapper;

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
    @Override
    public BaseResult listVendorByBrand(Integer vendorId, Integer authorityTag, Integer brandGoodsType) {
        return ApiResult.success(brandInfoMapper.listVendorByBrand(vendorId, authorityTag, brandGoodsType.equals(-1) ? null : brandGoodsType));
    }

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
    @Override
    public BaseResult listShopsByTypeBrand(@RequestParam(value = "vendorId", required = false) Integer vendorId) {
        List<Map<String, Object>> list;
        if (null != vendorId && !vendorId.equals("")) {
            list = brandInfoMapper.listShopsByTypeBrand(vendorId);
        } else {
            list = brandInfoMapper.listVendorByTypeBrand(1100);
        }
        if (null != list) {
            return ApiResult.success(list);
        } else {
            return BaseResult.error("暂时没有数据");
        }
    }

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
    @Override
    public BaseResult listBrandByGoods(Integer vendorId, Integer brandId, Integer source, Integer pageIndex, Integer pageSize) {
        PageResult<List<GoodsVO>> result = brandInfoMapper.listBrandByGoods(vendorId, brandId, source, pageIndex, pageSize);
        if (null != result) {
            return ApiResult.success(result.getList(), result.getCount());
        } else {
            return ApiResult.success();
        }
    }

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
    @Override
    public BaseResult getGoodsDetails(Integer vendorId, Long goodsId, Integer source) {
        GoodsDetailVO result = brandInfoMapper.getGoodsDetails(vendorId, goodsId, source);
        return null == result ? ApiResult.error("商品已下架") : ApiResult.success(result);
    }

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
    @Override
    public BaseResult listMatchingGoods(Integer vendorId, String goodsName, Integer source, Integer pageIndex, Integer pageSize) {
        PageResult<List<GoodsVO>> result = brandInfoMapper.listMatchingGoods(vendorId, goodsName, source, pageIndex, pageSize);
        if (null != result) {
            return ApiResult.success(result.getList(), result.getCount());
        } else {
            return ApiResult.error("没有匹配到该商品");
        }
    }
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
    @Override
    public BaseResult listVendorsByMainBrand(String mainBrand, Double longitude, Double latitude,int pageIndex,int pageSize){
        Map<String,Object> param = new HashedMap();
        param.put("mainBrand","%"+mainBrand+"%");
        param.put("longitude",longitude);
        param.put("latitude",latitude);
        param.put("startIndex",(pageIndex-1)*pageSize);
        param.put("count",pageSize);
        List<VendorBrandSearchDTO> vendorBrandSearchDTOList = brandInfoMapper.searchVendorByMainBrand(param);
        if (null != vendorBrandSearchDTOList && vendorBrandSearchDTOList.size() > 0) {
            for (VendorBrandSearchDTO vendorBrandSearchDTO : vendorBrandSearchDTOList){
                //计算距离
                double distance = MathUtil.GetDistance(longitude, latitude, Double.valueOf(vendorBrandSearchDTO.getLongitude()), Double.valueOf(vendorBrandSearchDTO.getLatitude()));
                vendorBrandSearchDTO.setDistance(String.valueOf(distance));
                //优惠劵列表
                List<CouponInfo> objCouponInfo = couponInfoMapper.CouponInfoListDetail(vendorBrandSearchDTO.getVendorId(),"", Constants.CREATE_COUPON_CODE_VENDOR);
                vendorBrandSearchDTO.setCouponList(objCouponInfo);
            }
            return ApiResult.success(vendorBrandSearchDTOList, vendorBrandSearchDTOList.size());
        } else {
            return ApiResult.error("暂无店铺经营该品牌");
        }
    }
}
