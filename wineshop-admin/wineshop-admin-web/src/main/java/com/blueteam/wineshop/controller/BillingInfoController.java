package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.wineshop.mapper.BillingInfoMapper;
import com.blueteam.wineshop.service.BillingInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 结算信息
 *
 * @author xiaojiang    2017年3月1日
 * @version 1.0
 * @since 1.0 2017年3月1日
 */
@Controller
@RequestMapping("/billingInfo")
@ApiLogin
public class BillingInfoController extends BaseController {
    @Autowired
    BillingInfoService billingInfoService;
    @Autowired
    BillingInfoMapper billingInfoMapper;
    private static Logger logger = LoggerFactory.getLogger(BillingInfoController.class);


}
