package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BasePageSearch;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.OrderInfo;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.wineshop.service.OrderInfoService;
import com.blueteam.wineshop.service.ScoreInfoService;
import com.blueteam.wineshop.service.VendorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Marx
 * <p>
 * OrderInfoController.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
@Controller
@RequestMapping("/orderInfo")
@AdminApiLogin
public class OrderInfoController extends BaseController {
    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    ScoreInfoService scoreInfoService;
    @Autowired
    VendorInfoService vendorInfoService;

//	private static Logger logger =LoggerFactory.getLogger(OrderInfoController.class);


	/* * 
     * @param Userid
	 * @param response
	 * @return
	 * @throws Exception
	 */

    /**
     * 加载页面
     *
     * @return
     * @throws Exception
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    @RequestMapping(value = "/listOrderInfo", method = RequestMethod.GET)
    public ModelAndView listOrderInfo() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> map = orderInfoService.countPrices(OrderInfo.ORDER_STATUS_FINISH_FINISHED, null, 1, null, sdf.parse(sdf.format(new Date())), sdf.parse(sdf.format(new Date())));
        ModelAndView mav = new ModelAndView("orderinfo/orderinfo_list");
        mav.addObject("counts", map.get("counts"));
        mav.addObject("discountAmounts", map.get("DiscountAmounts"));
        return mav;
    }

    /**
     * 获取数据
     *
     * @param pageSize
     * @param pageIndex
     * @param tradingArea 交易地区
     * @param vendorName
     * @param beginTime
     * @param endTime
     * @param response
     * @return
     * @throws Exception
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    @RequestMapping(value = "/listLimitOrderInfo", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult listLimitOrderInfo(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                  @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                                  @RequestParam(value = "tradingArea", required = false) String tradingArea,
                                  @RequestParam(value = "vendorName", required = false) String vendorName,
                                  @RequestParam(value = "beginTime", required = false) String beginTime,
                                  @RequestParam(value = "endTime", required = false) String endTime, HttpServletResponse response) throws Exception {
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        int type = 0;
        Date beginTimeDate = null;
        if (beginTime != null && !beginTime.isEmpty()) {
            beginTimeDate = SDF.parse(beginTime);
            type = 1;
        }
        Date endTimeDate = null;
        if (endTime != null && !endTime.isEmpty()) {
            endTimeDate = SDF.parse(endTime);
            type = 1;
        }
        String tradingAreas;
        if (tradingArea == null || tradingArea.equals("")) {
            tradingAreas = null;
        } else {
            tradingAreas = tradingArea;
            type = 1;
        }
        String vendorNames;
        if (vendorName == null || vendorName.equals("")) {
            vendorNames = null;
        } else {
            vendorNames = vendorName;
            type = 1;
        }
        BaseResult list = orderInfoService.listLimitOrderInfo(pageSize, pageIndex, tradingAreas, vendorNames, beginTimeDate, endTimeDate, type);
        return list;
    }

    /**
     * 获取交易次数 交易总额
     *
     * @param tradingArea
     * @param vendorName
     * @param beginTime
     * @param endTime
     * @param response
     * @return
     * @throws Exception
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    @RequestMapping(value = "/countPrices", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult countPrices(@RequestParam(value = "tradingArea", required = false) String tradingArea,
                           @RequestParam(value = "vendorName", required = false) String vendorName,
                           @RequestParam(value = "beginTime", required = false) String beginTime,
                           @RequestParam(value = "endTime", required = false) String endTime,
                           HttpServletResponse response) throws Exception {
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        int type = 0;
        Date beginTimeDate = null;
        if (beginTime != null && !beginTime.isEmpty()) {
            beginTimeDate = SDF.parse(beginTime);
            type = 1;
        }
        Date endTimeDate = null;
        if (endTime != null && !endTime.isEmpty()) {
            endTimeDate = SDF.parse(endTime);
            type = 1;
        }
        String tradingAreas;
        if (tradingArea == null || tradingArea.equals("")) {
            tradingAreas = null;
        } else {
            tradingAreas = tradingArea;
            type = 1;
        }
        String vendorNames;
        if (vendorName == null || vendorName.equals("")) {
            vendorNames = null;
        } else {
            vendorNames = vendorName;
            type = 1;
        }
        Map<String, Object> map = orderInfoService.countPrices(OrderInfo.ORDER_STATUS_FINISH_FINISHED,
                tradingAreas, type, vendorNames, beginTimeDate, endTimeDate);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/countPrice", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult countPrice(@RequestParam("ids") String ids, HttpServletResponse response) {
        Map<String, Object> map = orderInfoService.countPrice(ids);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/api/order/getUserOrders", method = RequestMethod.POST)
    public @ResponseBody
    BaseResult countPrice(BasePageSearch search) {
        PageResult<List<OrderInfo>> result = orderInfoService.selectUserOrderRecord(search);
        return ApiResult.success(result.getList(), result.getCount());
    }
}
