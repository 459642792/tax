package com.blueteam.wineshop.controller;

import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.VendorBrandInfoPO;
import com.blueteam.wineshop.service.BrandInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 商家的商品列表相关(用户端)
 *
 * @author xiaojiang
 * @create 2017-10-18  10:25
 */
@Controller
@RequestMapping("/shopsByGoods")
public class ShopsByGoodsController extends BaseController {
    @Autowired
    BrandInfoService brandInfoService;

    /**
     * 方法的功能描述:TODO 根据商家id和分类id 获取商品品牌数据
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName listShopsByBrand
     * @param: vendorId 商家id
     * @param: brandGoodsType 类型
     * @author xiaojiang 2017/10/20 10:25
     * @modifier
     * @since 1.4.0
     */
    @RequestMapping(value = "/listShopsByBrand", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult listShopsByBrand(@RequestParam("vendorId") Integer vendorId, @RequestParam(name = "brandGoodsType", required = false) Integer brandGoodsType) {
        return brandInfoService.listVendorByBrand(vendorId, VendorBrandInfoPO.AUTHORITY_TAG_VALID, brandGoodsType);
    }

    /**
     * 方法的功能描述:TODO 根据商家获取所有分类
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName listShopsByTypeBrand
     * @param: vendorId
     * @author xiaojiang 2017/10/24 19:11
     * @modifier
     * @since 1.4.0
     */
    @RequestMapping(value = "/listShopsByTypeBrand", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult listShopsByTypeBrand(@RequestParam("vendorId") Integer vendorId) {
        return brandInfoService.listShopsByTypeBrand(vendorId);
    }

    /**
     * 方法的功能描述:TODO 根据商家id和品牌id 获取所有商家商品列表
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName listBrandByGoods
     * @param: vendorId 商家id
     * @param: brandId 品牌id
     * @param: pageIndex 页数
     * @param: pageSize 条数
     * @author xiaojiang 2017/10/24 16:03
     * @modifier
     * @since 1.4.0
     */
    @RequestMapping(value = "/listBrandByGoods", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult listBrandByGoods(@RequestParam("vendorId") Integer vendorId, @RequestParam("brandId") Integer brandId,
                                @RequestParam("source") Integer source, @RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return brandInfoService.listBrandByGoods(vendorId, brandId, source, pageIndex, pageSize);
    }

    /**
     * 方法的功能描述:TODO 模糊匹配商家商品列表
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName listMatchingGoods
     * @param: vendorId 商家id
     * @param: goodsName 模糊匹配词
     * @param: pageIndex 页数
     * @param: pageSize 条数
     * @author xiaojiang 2017/10/24 17:25
     * @modifier
     * @since 1.4.0
     */
    @RequestMapping(value = "/listMatchingGoods", method = RequestMethod.POST)
    public @ResponseBody
    BaseResult listMatchingGoods(@RequestParam("vendorId") Integer vendorId, @RequestParam("goodsName") String goodsName
            , @RequestParam("source") Integer source, @RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        return brandInfoService.listMatchingGoods(vendorId, goodsName, source, pageIndex, pageSize);
    }

    /**
     * 方法的功能描述:TODO 根据商家id商品id获取商品详情
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName getGoodsDetails
     * @param: vendorId 商家id
     * @param: goodsId 商品id
     * @author xiaojiang 2017/10/24 16:02
     * @modifier
     * @since 1.4.0
     */
    @RequestMapping(value = "/getGoodsDetails", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult getGoodsDetails(@RequestParam("vendorId") Integer vendorId, @RequestParam("goodsId") Long goodsId, @RequestParam("source") Integer source) {
        return brandInfoService.getGoodsDetails(vendorId, goodsId, source);
    }

}
