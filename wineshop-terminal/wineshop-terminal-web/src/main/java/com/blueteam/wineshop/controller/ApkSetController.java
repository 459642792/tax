package com.blueteam.wineshop.controller;

import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.Apkset;
import com.blueteam.wineshop.service.ApkSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by libra on 2017/4/12.
 */
@Controller
@RequestMapping("/ApkCarrier")
public class ApkSetController extends BaseController {

    @Autowired
    ApkSetService service;

    /**
     * 详情
     */
    @RequestMapping(value = "/getapk", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult getapk(HttpServletResponse response) throws Exception {
        Apkset objApk = service.SelApk();
        if (null == objApk) return ApiResult.error("信息错误");
        return ApiResult.success(objApk);
    }
}
