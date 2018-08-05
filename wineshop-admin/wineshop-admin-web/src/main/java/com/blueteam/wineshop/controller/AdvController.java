package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.base.constant.Constants;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.ToutuSearch;
import com.blueteam.entity.po.AdvertiseInfo;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.wineshop.service.AdvertiseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by libra on 2017/4/12.
 */
@AdminApiLogin
@Controller
public class AdvController extends BaseController {

    @Autowired
    AdvertiseInfoService service;

    /**
     * 头图列表
     *
     * @return
     */
    @RequestMapping(value = "/adv/advList/{advType}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable String advType) {
        ModelAndView modelAndView = new ModelAndView("/AdvManager/" + (Constants.CREATE_Sdpp_IMAGELIST.equals(advType) ? "PinPaiTouTu" : "TouTu"));
        modelAndView.getModel().put("typeCode", advType);
        return modelAndView;
    }

    /**
     * 广告列表
     *
     * @param search
     * @return
     */
    @RequestMapping(value = "/api/adv/advList", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult advList(ToutuSearch search) {
        PageResult<List<AdvertiseInfo>> list = service.selectAdvByWhere(search);
        return ApiResult.success(list.getList(), list.getCount());
    }


    /**
     * 广告头图编辑页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/adv/edit", method = RequestMethod.GET)
    public ModelAndView editToutu(int id, String typeCode) {
        ModelAndView modelAndView = new ModelAndView("/AdvManager/AddTouTu");
        modelAndView.getModel().put("id", id);
        modelAndView.getModel().put("postAdmin", super.getUserName());
        modelAndView.getModel().put("typeCode", typeCode);
        modelAndView.getModel().put("title", id > 0 ? "编辑" : "新增");
        return modelAndView;
    }

    /**
     * 获取广告详情
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/api/adv/get", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult get(int id) {
        if (id <= 0)
            return ApiResult.success(new AdvertiseInfo());
        AdvertiseInfo model = service.findAdvByID(id);
        return ApiResult.success(model);
    }

    /**
     * 发布或编辑广告
     *
     * @param adv
     * @return
     */
    @RequestMapping(value = "/api/adv/post", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult post(AdvertiseInfo adv) {
        if (adv == null)
            return BaseResult.error("参数错误");
        String userName = super.getUserName();
        adv.setUpdateBy(userName);
        if (adv.getId() == null || adv.getId() <= 0) {
            adv.setCreateBy(userName);
            return service.adminInsert(adv);
        }

        return service.adminUpdate(adv);
    }


    /**
     * 广告头图编辑页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/adv/pinpai/edit", method = RequestMethod.GET)
    public ModelAndView editPinpaiToutu(int id, String typeCode) {
        ModelAndView modelAndView = new ModelAndView("/AdvManager/AddPinPaiTouTu");
        modelAndView.getModel().put("id", id);
        modelAndView.getModel().put("postAdmin", super.getUserName());
        modelAndView.getModel().put("typeCode", typeCode);
        modelAndView.getModel().put("title", id > 0 ? "编辑" : "新增");
        return modelAndView;
    }
}
