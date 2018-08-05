package com.blueteam.wineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 交易信息-结算
 * Created by  NastyNas on 2018/1/18.
 */
@Controller
@RequestMapping("/settlementAdmin")
//@AdminApiLogin
public class SettlementAdminController {
    @Autowired
//    SettlementAdminService settlementAdminService;

    /**
     * 结算列表页分页查询
     *
     * @return
     */
//    @RequestMapping(value = "/listSettlement", method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResult listOrder(SettlementListSearchDTO settlementListSearchDTO) {
//        PageResult<List<AdminSettlementListVO>> orderInfoList = settlementAdminService.listSettlement(settlementListSearchDTO);
//        return ApiResult.success(orderInfoList.getList(), orderInfoList.getCount());
//
//        return null;
//    }


    /**
     * 页面跳转
     *
     * @return
     */
    @RequestMapping(value = "/showSettlementList", method = RequestMethod.GET)
    public ModelAndView showSettlementList() {
        return new ModelAndView("tradeinfo/settlementadmin/settlement_admin_list");
    }

}
