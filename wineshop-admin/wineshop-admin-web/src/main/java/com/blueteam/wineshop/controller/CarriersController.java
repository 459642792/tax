package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.CarriersInfo;
import com.blueteam.wineshop.service.CarriersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by libra o   n 2017/4/13.
 */
@AdminApiLogin
@Controller
public class CarriersController extends BaseController {

    /**
     * 运营商服务
     */
    @Autowired
    private CarriersService service;

    /**
     * 添加或者编辑运营商
     *
     * @param id 运营商ID
     * @return
     */
    @RequestMapping(value = "/carriers/edit")
    public ModelAndView addOrEdit(int id) {
        ModelAndView modelAndView = new ModelAndView("/UserManager/" + (id > 0 ? "EditCarriers" : "AddCarriers"));
        modelAndView.getModel().put("id", id);
        return modelAndView;
    }

    /**
     * 获取运营商实体接口
     *
     * @param id 运营商ID
     * @return
     */
    @RequestMapping(value = "/api/carriers/get")
    @ResponseBody
    public BaseResult getCarriers(int id) {
        if (id <= 0)
            return ApiResult.success(new CarriersInfo());
        CarriersInfo item = service.getCarriersByID(id);
        return ApiResult.success(item);
    }

    /**
     * 添加运营商或者编辑运营商
     *
     * @param carriersInfo 运营商信息
     * @return
     */
    @RequestMapping(value = "/api/carriers/post", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addOrEditCarriers(CarriersInfo carriersInfo) {

        if (carriersInfo.getId() != null && carriersInfo.getId() > 0) {
            carriersInfo.setUpdateby(super.getUserName());
            return service.edit(carriersInfo);
        }

        carriersInfo.setCreateby(super.getUserName());
        carriersInfo.setUpdateby(super.getUserName());
        return service.add(carriersInfo);
    }
}
