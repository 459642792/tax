package com.blueteam.wineshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by clam on 2017/5/3.
 */
@Controller
public class OtherController {

    @RequestMapping("/Scripts/uEdiotr/jsp/ueditor")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/Scripts/uEdiotr/jsp/controller");
        return modelAndView;
    }
}
