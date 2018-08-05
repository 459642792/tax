package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.VendorApiLogin;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.wineshop.service.GoodsCashRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 酒币结算表
 *
 * @author xiaojiang 2017年4月19日
 * @version 1.0
 * @since 1.0 2017年4月19日
 */
@Controller
@RequestMapping("/goodsCashRecord")
public class GoodsCashRecordController extends BaseController {
    @Autowired
    GoodsCashRecordService goodsCashRecordservice;

    /**
     * 兑换商品
     *
     * @param tradedGoodsId
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    @VendorApiLogin
    @RequestMapping(value = "/cashGoods", method = RequestMethod.GET, params = {"tradedGoodsId", "vendorInfoId"})
    public @ResponseBody
    BaseResult cashGoods(Integer tradedGoodsId, Integer vendorInfoId) {
        return goodsCashRecordservice.cashGoods(tradedGoodsId, vendorInfoId);
    }

    /**
     * 获取商家剩余酒币
     *
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    @RequestMapping(value = "/countVendorInfoCurrencyRecord", method = RequestMethod.GET, params = {"vendorInfoId"})
    public @ResponseBody
    BaseResult countVendorInfoCurrencyRecord(Integer vendorInfoId) {
        return goodsCashRecordservice.countVendorInfoCurrencyRecord(vendorInfoId);
    }

    /**
     * 获取商家酒币记录
     *
     * @param pageSize
     * @param pageIndex
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    @RequestMapping(value = "/listVendorInfoCurrencyRecord", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult listVendorInfoCurrencyRecord(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex, @RequestParam("vendorInfoId") Integer vendorInfoId) {
        return goodsCashRecordservice.listVendorInfoCurrencyRecord(pageSize, pageIndex, vendorInfoId);
    }

    /**
     * 获取商家兑换列表
     *
     * @param pageSize
     * @param pageIndex
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    @RequestMapping(value = "/listGoodsCashRecord", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult listGoodsCashRecord(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex, @RequestParam("vendorInfoId") Integer vendorInfoId) {
        if (null != vendorInfoId && !"".equals(vendorInfoId)) {
            return goodsCashRecordservice.listGoodsCashRecord(pageSize, pageIndex, null, null, null, null, null, vendorInfoId, null);
        } else {
            return ApiResult.error("商家id不能为null！");
        }
    }

}
