package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Enums;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/Index")
public class IndexController extends BaseController {
    @RequestMapping("/index")
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public ModelAndView Index() {
        ModelAndView modelAndView = new ModelAndView("index");
        if (super.isUserType(super.getIdentify(), Enums.UserType.Admin)) {
            modelAndView.addObject("Power", "admin");
        } else {
            modelAndView.addObject("Power", "operators");
        }
        return modelAndView;
    }
}
