package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.VendorApiLogin;
import com.blueteam.base.lang.RStr;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.GoodsVendorDTO;
import com.blueteam.entity.po.GoodsVerifyInfoPO;
import com.blueteam.wineshop.service.VendorByGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 商家端 商品相关接口
 *
 * @author xiaojiang
 * @create 2017-10-28  15:09
 */
@Controller
@RequestMapping("/vendorByGoods")
public class VendorByGoodsController extends BaseController {
    @Autowired
    VendorByGoodsService vendorByGoodsService;

    /**
     * 方法的功能描述:TODO 根据相关参数获取商家商品列表 参数类型多 后面可以封装成javabean
     *
     * @return
     * @methodName
     * @param: brandGoodsType 商品类型
     * @param: sort 排序
     * @author xiaojiang 2017/10/30 15:51
     * @modifier
     * @since 1.4.0
     */
    @VendorApiLogin
    @RequestMapping(value = "/listTypeByGoods", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult listTypeByGoods(@RequestParam("brandGoodsType") Integer brandGoodsType,
                               @RequestParam("order") String order, @RequestParam("orderType") String orderType
            , @RequestParam("source") Integer source, @RequestParam("type") Integer type, @RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return vendorByGoodsService.listGoodsByType(this.getIdentify().getExtendId(), brandGoodsType, order, orderType, source, type, pageIndex, pageSize);
//        return vendorByGoodsService.listGoodsByType( 34,  brandGoodsType, order,orderType,source,type,pageIndex,pageSize);
    }

    /**
     * 方法的功能描述:TODO 新增审核数据
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName insertGoodsVerifyInfo
     * @param: goodsVerifyInfo
     * @author xiaojiang 2017/10/31 10:16
     * @modifier
     * @since 1.4.0
     */
    @VendorApiLogin
    @RequestMapping(value = "/insertGoodsVerifyInfo", method = {RequestMethod.POST},
            params = {"verifyBarCode", "verifyGoodsName", "verifyGoodsState", "verifyGoodsPhoto", "price"})
    public @ResponseBody
    BaseResult insertGoodsVerifyInfo(GoodsVerifyInfoPO model) {
        model.setVerifySalePrice((int) (model.getPrice() * 100));
//        model.setVendorId(34);
        model.setVendorId(this.getIdentify().getExtendId());
        return vendorByGoodsService.insertGoodsVerifyInfo(model);
    }

    /**
     * 方法的功能描述:TODO 获取审核商品数据
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/11/1 14:12
     * @modifier
     * @since 1.4.0
     */
    @VendorApiLogin
    @RequestMapping(value = "/listGoodsByVerify", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult listGoodsByVerify() {
        return vendorByGoodsService.listGoodsByVerify(this.getIdentify().getExtendId());
//        return vendorByGoodsService.listGoodsByVerify(3);
    }

    /**
     * 方法的功能描述:TODO 根据商家id商品id获取商品详情
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName getGoodsDetails
     * @param: vendorId 商家id
     * @param: goodsId 商品id
     * @author xiaojiang 2017/10/31 17:51
     * @modifier
     * @since 1.4.0
     */
    @VendorApiLogin
    @RequestMapping(value = "/getVendorByGoodsDetails", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult getVendorByGoodsDetails(@RequestParam("goodsId") Long goodsId, @RequestParam("source") Integer source) {
        return vendorByGoodsService.getVendorByGoodsDetails(this.getIdentify().getExtendId(), goodsId, source);
//        return   vendorByGoodsService.getVendorByGoodsDetails( 3, goodsId,source);
    }


    /**
     * 方法的功能描述:TODO 模糊匹配商家商品列表
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName listMatchingGoodsByNameAndBarcode
     * @param: vendorId 商家id
     * @param: goodsName 模糊匹配词
     * @param: pageIndex 页数
     * @param: pageSize 条数
     * @author xiaojiang 2017/11/1 17:06
     * @modifier
     * @since 1.4.0
     */
    @RequestMapping(value = "/listMatchingGoodsByNameAndBarcode", method = RequestMethod.POST)
    public @ResponseBody
    BaseResult listMatchingGoodsByNameAndBarcode(@RequestParam("matchingValue") String matchingValue
            , @RequestParam("source") Integer source, @RequestParam(name = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                                                 @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return vendorByGoodsService.listMatchingGoodsByNameAndBarcode(matchingValue, source, pageIndex, pageSize);
    }

    /**
     * 方法的功能描述:TODO 新增 编辑  上下架 删除
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/11/3 9:43
     * @modifier
     * @since 1.4.0
     */
    @VendorApiLogin
    @RequestMapping(value = "/saveChangeVendorByGoods", method = {RequestMethod.POST},
            params = {"goodsId", "goodsVendroStatus", "genre"})
    public @ResponseBody
    BaseResult saveChangeVendorByGoods(GoodsVendorDTO goodsVendorDTO) {
        if (goodsVendorDTO.getGenre().equals(1) && !RStr.isNotEmpty(goodsVendorDTO.getPrice())) {
            return ApiResult.error("编辑商品价格不能为空");
        }
//        goodsVendorDTO.setVendorId( 3);
        goodsVendorDTO.setVendorId(this.getIdentify().getExtendId());
        return vendorByGoodsService.saveChangeVendorByGoods(goodsVendorDTO);
    }

}
