package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.wineshop.mapper.SettlementRecordsMapper;
import com.blueteam.wineshop.service.SettlementRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 结算信息
 *
 * @author xiaojiang 2017年4月7日
 * @version 1.0
 * @since 1.0 2017年4月7日
 */

@Controller
@RequestMapping("/settlementRecords")
@AdminApiLogin
public class SettlementRecordsController {

    @Autowired
    SettlementRecordsService settlementRecordsService;

    @Autowired
    SettlementRecordsMapper settlementRecordsMapper;

    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 跳转页面
     *
     * @return
     * @throws Exception
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    @RequestMapping(value = "/listSettlementRecord", method = RequestMethod.GET)
    public ModelAndView listSettlementRecord() throws Exception {
        ModelAndView mav = new ModelAndView("settlementrecords/settlement_records");
        return mav;
    }

    /**
     * 获取所有技术商家统计数据
     *
     * @param pageSize
     * @param pageIndex
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
    @RequestMapping(value = "/listLimitVendorSettlementRecords", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult listLimitVendorSettlementRecords(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                                                @RequestParam(value = "tradingArea", required = false) String tradingArea,
                                                @RequestParam(value = "vendorName", required = false) String vendorName,
                                                @RequestParam(value = "beginTime", required = false) String beginTime,
                                                @RequestParam(value = "endTime", required = false) String endTime, HttpServletResponse response) throws Exception {
        Date beginTimeDate = null;
        if (beginTime != null && !beginTime.equals("")) {
            beginTimeDate = SDF.parse(beginTime);
        }
        Date endTimeDate = null;
        if (endTime != null && !endTime.equals("")) {
            endTimeDate = SDF.parse(endTime);
        }
        List<Map<String, Object>> list = settlementRecordsService.listLimitVendorSettlementRecords(pageSize, pageIndex, tradingArea, vendorName, beginTimeDate, endTimeDate);
        int count = settlementRecordsService.countVendorSettlementRecords(tradingArea, vendorName, beginTimeDate, endTimeDate);
        return ApiResult.success(list, count);
    }

    /**
     * 根据ID获取商家结算信息
     *
     * @param vendorInfoId
     * @param response
     * @return
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    @RequestMapping(value = "/listSettlementRecords", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult listSettlementRecords(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                     @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex, @RequestParam("vendorInfoId") String vendorInfoId, HttpServletResponse response) {
        Integer id = null;
        if (vendorInfoId != null && !vendorInfoId.isEmpty()) {
            id = Integer.parseInt(vendorInfoId);
        }
        List<Map<String, Object>> list = settlementRecordsService.listSettlementRecords(pageSize, pageIndex, id);
        int counts = settlementRecordsMapper.listCountSettlementRecords(id);
        return ApiResult.success(list, counts);
    }

    /**
     * 保存结算信息
     *
     * @param vendorInfoId
     * @param amounts
     * @param response
     * @return result message
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    @RequestMapping(value = "/insertSettlementRecords", method = RequestMethod.POST)
    public @ResponseBody
    BaseResult insertSettlementRecords(@RequestParam("vendorInfoId") String vendorInfoId, @RequestParam("amounts") String amounts,
                                       @RequestParam("startDate") String startDate,
                                       @RequestParam("endDate") String endDate,
                                       HttpServletResponse response) throws Exception {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date beginTimeDate = null;
        if (startDate != null && !startDate.equals("")) {
            beginTimeDate = sdf1.parse(startDate);
        }
        Date endTimeDate = null;
        if (endDate != null && !endDate.equals("")) {
            endTimeDate = SDF.parse(endDate);
        }
        Integer id = null;
        if (vendorInfoId != null && !vendorInfoId.isEmpty()) {
            id = Integer.parseInt(vendorInfoId);
        }
        BigDecimal amount = null;
        if (amounts != null && !amounts.isEmpty()) {
            amount = new BigDecimal(amounts);
        }
        Map<String, Object> map = settlementRecordsService.insertSettlementRecords(id, amount, beginTimeDate, endTimeDate);
        ApiResult apiResult = new ApiResult();
        apiResult.setSuccess(true);
        apiResult.setMessage("成功");
        apiResult.setStatus("200");
        apiResult.setData(map);
        if (null != map) {
            Object returnId = map.get("returnId");
            apiResult.setReturnId(String.valueOf(null != returnId ? new Integer(returnId.toString()) : 0));
        }
        return apiResult;
    }

    @RequestMapping(value = "/getVendorInfoSettlement", method = RequestMethod.GET)
    /**
     * 方法的功能描述:TODO 根据商家id获取商家结算相关信息 d'd
     *@methodName getVendorInfoSettlement
     *@param [vendorInfoId, response]
     *@return base.dto.BaseResult
     *@since 1.3.0
     *@author xiaojiang 2017/5/12 17:47
     */
    public @ResponseBody
    BaseResult getVendorInfoSettlement(@RequestParam("vendorInfoId") Integer vendorInfoId, HttpServletResponse response) {

        return settlementRecordsService.getVendorInfoSettlement(vendorInfoId);
    }

    /**
     * 方法的功能描述:TODO 获取商家待结算数据
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/15 11:55
     * @since 1.3.0
     */
    @RequestMapping(value = "/listVendorInfoForTheSettlement", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult listVendorInfoForTheSettlement(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                              @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex, @RequestParam("vendorInfoId") Integer vendorInfoId,
                                              @RequestParam("startDate") String startDate,
                                              @RequestParam("endDate") String endDate,
                                              HttpServletResponse response) throws Exception {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date beginTimeDate = null;
        if (startDate != null && !startDate.equals("")) {
            beginTimeDate = sdf1.parse(startDate);
        }
        Date endTimeDate = null;
        if (endDate != null && !endDate.equals("")) {
            endTimeDate = SDF.parse(endDate);
        }
        return settlementRecordsService.listVendorInfoForTheSettlement(pageSize, pageIndex, vendorInfoId, beginTimeDate, endTimeDate);
    }

    /**
     * 方法的功能描述:TODO 获取商家待结算日期
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/15 15:01
     * @since 1.3.0
     */
    @RequestMapping(value = "/getSettlementDate", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult getSettlementDate(@RequestParam("vendorInfoId") Integer vendorInfoId, HttpServletResponse response) {
        return settlementRecordsService.getSettlementDate(vendorInfoId);

    }
}
