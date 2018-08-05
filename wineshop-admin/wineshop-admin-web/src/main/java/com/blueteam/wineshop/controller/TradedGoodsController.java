package com.blueteam.wineshop.controller;

import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.TradedGoods;
import com.blueteam.wineshop.service.GoodsCashRecordService;
import com.blueteam.wineshop.service.TradedGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 平台商品管理
 * <p>
 * 商品管理
 *
 * @author xiaojiang 2017年4月19日
 * @version 1.0
 * @since 1.0 2017年4月19日
 */

/**
 * 商品管理
 *
 * @author xiaojiang 2017年4月19日
 * @version 1.0
 * @since 1.0 2017年4月19日
 */

@Controller
@RequestMapping("/tradedGoods")
public class TradedGoodsController extends BaseController {
    @Autowired
    TradedGoodsService tradedGoodsService;
    @Autowired
    GoodsCashRecordService goodsCashRecordservice;

    /**
     * @return
     * @throws Exception
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    @RequestMapping(value = "/pageTradedGoods", method = RequestMethod.GET)
    public ModelAndView listOrderInfo() throws Exception {
        ModelAndView mav = new ModelAndView("tradedgoods/traded_goods_list");
        return mav;
    }

    /**
     * 查询列表
     *
     * @param pageSize
     * @param pageIndex
     * @param goodsName
     * @param brandName
     * @param goodsStatus
     * @param response
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    @RequestMapping(value = "/listTradedGoods", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult listTradedGoods(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                               @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                               @RequestParam(value = "goodsName", required = false) String goodsName,
                               @RequestParam(value = "brandName", required = false) String brandName,
                               @RequestParam(value = "goodsStatus", required = false) String goodsStatus, HttpServletResponse response) {
        return tradedGoodsService.listTradedGoods(pageSize, pageIndex, goodsName, brandName, null == goodsStatus || goodsStatus.isEmpty() ? null : Integer.valueOf(goodsStatus));
    }

    /**
     * 保存商品名称
     *
     * @param response
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    @RequestMapping(value = "/saveEditTradedGoods", method = RequestMethod.POST, params = {"goodsName", "brandName", "goodsPrice"})
    //	@ApiLogin
    public @ResponseBody
    BaseResult saveEditTradedGoods(@RequestParam("imageUrl") String imageUrl, @RequestParam(value = "tradedGoodsId", required = false) Integer tradedGoodsId, TradedGoods tradedGoods, HttpServletResponse response) {
        if (null == tradedGoodsId || tradedGoodsId.equals("")) {
            tradedGoods.setCreateBy(this.getUserName());
            tradedGoods.setCreateDate(new Date());
            tradedGoods.setUpdateBy(this.getUserName());
            tradedGoods.setUpdateDate(new Date());
        } else {
            tradedGoods.setUpdateBy(this.getUserName());
            tradedGoods.setUpdateDate(new Date());
        }
        return tradedGoodsService.saveEditTradedGoods(tradedGoodsId, imageUrl, tradedGoods);
    }

    /**
     * 修改状态
     *
     * @param goodsStatus
     * @param tradedGoodsId
     * @param response
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    @RequestMapping(value = "/updateStatusTradedGoods", method = RequestMethod.POST)
    //	@ApiLogin
    public @ResponseBody
    BaseResult updateStatusTradedGoods(@RequestParam("goodsStatus") Integer goodsStatus, @RequestParam("tradedGoodsId") Integer tradedGoodsId, HttpServletResponse response) {
        TradedGoods tradedGoods = new TradedGoods();
        tradedGoods.setUpdateBy(this.getUserName());
        tradedGoods.setUpdateDate(new Date());
        tradedGoods.setId(tradedGoodsId);
        tradedGoods.setGoodsStatus(goodsStatus);
        return tradedGoodsService.updateStatusTradedGoods(tradedGoods);
    }

    /**
     * 获取详情
     *
     * @param tradedGoodsId
     * @param response
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    @RequestMapping(value = "/getTradedGoods", method = RequestMethod.GET)
    //	@ApiLogin
    public @ResponseBody
    BaseResult getTradedGoods(@RequestParam("tradedGoodsId") Integer tradedGoodsId, HttpServletResponse response) {
        return tradedGoodsService.getTradedGoods(tradedGoodsId);
    }


    /**
     * 获取商品兑换记录
     *
     * @param pageSize
     * @param pageIndex
     * @param tradedGoodsId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    @RequestMapping(value = "listTradedGoodsGoodsCashRecord", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult listTradedGoodsGoodsCashRecord(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                              @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex, @RequestParam("tradedGoodsId") Integer tradedGoodsId) {
        return goodsCashRecordservice.listGoodsCashRecord(pageSize, pageIndex, null, null, null, null, null, null, tradedGoodsId);
    }
}
