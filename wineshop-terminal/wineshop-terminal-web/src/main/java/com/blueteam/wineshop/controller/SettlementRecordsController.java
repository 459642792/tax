package com.blueteam.wineshop.controller;

import com.blueteam.wineshop.mapper.SettlementRecordsMapper;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.wineshop.service.SettlementRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 结算信息
 *
 * @author xiaojiang 2017年4月7日
 * @version 1.0
 * @since 1.0 2017年4月7日
 */

@Controller
@RequestMapping("/settlementRecords")
public class SettlementRecordsController {

    @Autowired
    SettlementRecordsService settlementRecordsService;

    @Autowired
    SettlementRecordsMapper settlementRecordsMapper;

    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

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
     * 方法的功能描述:TODO 根据商家id获取商家结算记录
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/15 17:13
     * @since 1.3.0
     */
    @RequestMapping(value = "/getSettlementRecords", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult getSettlementRecords(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                    @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex, @RequestParam("vendorInfoId") Integer vendorInfoId, HttpServletResponse response) {
        return settlementRecordsService.getSettlementRecords(pageSize, pageIndex, vendorInfoId);
    }

}
