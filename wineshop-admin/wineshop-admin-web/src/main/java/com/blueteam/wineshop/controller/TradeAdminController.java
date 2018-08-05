package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.TradeListSearchDTO;
import com.blueteam.entity.vo.AdminTradeVO;
import com.blueteam.wineshop.service.TradeAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 后台管理 交易数据
 * Created by  NastyNas on 2018/1/15.
 */
@Controller
@RequestMapping("/tradeAdmin")
@AdminApiLogin
public class TradeAdminController extends BaseController {


    @Autowired
    TradeAdminService tradeAdminService;

    /**
     * 获取交易数据
     *
     * @param tradeListSearchDTO
     * @return
     */
    @RequestMapping(value = "/getTradeInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getTradeInfo(TradeListSearchDTO tradeListSearchDTO) {
        AdminTradeVO adminTradeVO = tradeAdminService.getTradeInfo(tradeListSearchDTO);
        return ApiResult.success(adminTradeVO);
    }


    /**
     * 页面跳转
     *
     * @return
     */
    @RequestMapping(value = "/showTradeList", method = RequestMethod.GET)
    public ModelAndView showTradeList() {
        return new ModelAndView("tradeinfo/tradeadmin/trade_admin_list");
    }


}
