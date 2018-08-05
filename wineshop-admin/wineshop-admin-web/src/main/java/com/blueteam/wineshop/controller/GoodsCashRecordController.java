package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.wineshop.service.GoodsCashRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 酒币结算表
 *
 * @author xiaojiang 2017年4月19日
 * @version 1.0
 * @since 1.0 2017年4月19日
 */
@Controller
@RequestMapping("/goodsCashRecord")
@AdminApiLogin
public class GoodsCashRecordController extends BaseController {
    @Autowired
    GoodsCashRecordService goodsCashRecordservice;

//	private static 		SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd ");

    /**
     * 控制跳转
     *
     * @return
     * @throws Exception
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    @RequestMapping(value = "/pageGoodsCashRecord", method = RequestMethod.GET)
    public ModelAndView listOrderInfo() throws Exception {
        ModelAndView mav = new ModelAndView("goodsCashRecord/goods_cash_record");
        return mav;
    }


    /**
     * 获取兑换记录列表
     *
     * @param pageSize
     * @param pageIndex
     * @param orderNumber
     * @param vendorInfoName
     * @param beginTime
     * @param endTime
     * @param cashStatus
     * @param response
     * @return
     * @throws NumberFormatException
     * @throws ParseException
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    @RequestMapping(value = "/listGoodsCashRecord", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult listGoodsCashRecord(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                                   @RequestParam(value = "orderNumber", required = false) String orderNumber,
                                   @RequestParam(value = "vendorInfoName", required = false) String vendorInfoName,
                                   @RequestParam(value = "beginTime", required = false) String beginTime,
                                   @RequestParam(value = "endTime", required = false) String endTime,
                                   @RequestParam(value = "cashStatus", required = false) String cashStatus, HttpServletResponse response) throws ParseException {
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        Date beginTimes = null;
        if (beginTime != null && !beginTime.equals("")) {
            beginTimes = SDF.parse(beginTime);
        }
        Date endTimes = null;
        if (endTime != null && !endTime.equals("")) {
            endTimes = SDF.parse(endTime);
        }
        return goodsCashRecordservice.listGoodsCashRecord(pageSize, pageIndex, orderNumber,
                vendorInfoName, Integer.valueOf(cashStatus) == 9 ? null : Integer.valueOf(cashStatus), beginTimes, endTimes, null, null);
    }


    /**
     * 获取兑换商品详情
     *
     * @param goodsCashRecordId
     * @return
     * @author xiaojiang 2017年4月22日
     * @version 1.0
     * @since 1.0 2017年4月22日
     */
    @RequestMapping(value = "/getGoodsCashRecord", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult getGoodsCashRecord(@RequestParam("goodsCashRecordId") Integer goodsCashRecordId) {
        return goodsCashRecordservice.getGoodsCashRecord(goodsCashRecordId);
    }


    /**
     * 兑换商品发货
     *
     * @param expressCompany
     * @param expressNumbers
     * @return
     * @author xiaojiang 2017年4月22日
     * @version 1.0
     * @since 1.0 2017年4月22日
     */
    @RequestMapping(value = "/expressGoods", method = RequestMethod.POST, params = {"goodsCashRecordId", "expressCompany", "expressNumbers"})
    public @ResponseBody
    BaseResult expressGoods(Integer goodsCashRecordId, String expressCompany, String expressNumbers) {
        return goodsCashRecordservice.expressGoods(goodsCashRecordId, this.getUserName(), expressCompany, expressNumbers);
    }

    ;


}
