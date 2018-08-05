package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Enums;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.Apkset;
import com.blueteam.wineshop.service.ApkSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by libra on 2017/4/12.
 */
@AdminApiLogin
@Controller
@RequestMapping("/ApkManager")
public class ApkSetController extends BaseController {

    @Autowired
    ApkSetService service;


    /**
     * 初始化页面权限控制
     *
     * @return
     */
    @RequestMapping(value = "/apkMain", method = RequestMethod.GET)
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public ModelAndView Index() {
        ModelAndView modelAndView = new ModelAndView("/pinpaiManage/ApkMain");
        return modelAndView;
    }

    /**
     * 新增和修改
     *
     * @param obj
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/apkAddOrEdit", method = RequestMethod.POST)
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public @ResponseBody
    BaseResult apkAddOrEdit(Apkset obj, HttpServletResponse response) throws Exception {
        if (obj.getApk_id() < 0) {
            return ApiResult.error("传入参数不正确");
        }
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = dateFormat.format(now);
        if (obj.getApk_id() == 0) {
            obj.setCreateby(this.getNickName());
            obj.setCreatedate(date);
            obj.setUpdateBy(this.getNickName());
            obj.setUpdateDate(date);
            service.insertApkSet(obj);
        } else {
            obj.setUpdateBy(this.getNickName());
            obj.setUpdateDate(date);
            service.updateapk(obj);
        }
        return ApiResult.success();
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/getapk", method = RequestMethod.GET)
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public @ResponseBody
    BaseResult getapk(HttpServletResponse response) throws Exception {
        Apkset objApk = service.SelApk();
        return ApiResult.success(objApk);
    }
}
