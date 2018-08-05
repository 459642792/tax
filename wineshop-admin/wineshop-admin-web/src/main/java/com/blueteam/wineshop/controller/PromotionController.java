package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.entity.po.GoodsInfoPO;
import com.blueteam.entity.po.PromotionCatagoryPO;
import com.blueteam.entity.po.PromotionInfoPO;
import com.blueteam.wineshop.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangqijun on 18/1/13.
 */
@ApiLogin
@Controller
@RequestMapping("/promotion")
public class PromotionController extends BaseController {

    @Autowired
    private PromotionService promotionService;

    //参数Date类型转换器
//    @org.springframework.web.bind.annotation.InitBinder
//    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
//        System.out.println("执行了InitBinder方法");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
//    }

    /**
     * 首页6大活动后台管理页面
     * */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(Model model){
        return new ModelAndView("promotion/index");
    }
    /**
     * 获取首页活动信息列表
     * */
    @ResponseBody
    @RequestMapping(value = "/catagory/list", method = RequestMethod.GET)
    public BaseResult listCatagorys(){
        List<PromotionCatagoryPO> catagoryList = promotionService.listPromotionCatagory();
        return ApiResult.success(catagoryList);
    }
    /**
     * 更新活动分类的文案和banner
     * */
    @ResponseBody
    @RequestMapping(value = "/catagory/update", method = RequestMethod.POST)
    public BaseResult updateCatagoryInfo(Integer promotionCatagoryId,String summary,String banner){
        try {
            PromotionCatagoryPO promotionCatagory = promotionService.getPromotionCatagoryById(promotionCatagoryId);
            if(promotionCatagory == null){
                return BaseResult.error("未找到指定活动，请检查ID是否正确");
            }
            else {
                promotionCatagory.setBanner(banner);
                promotionCatagory.setSummary(summary);
                promotionCatagory.setUpdateStaffId(getIdentify().getUserId());
                if(promotionService.updatePromotionCatagory(promotionCatagory) > 0){
                    return ApiResult.success();
                }
                else {
                    return ApiResult.error("更新失败");
                }
            }
        }
        catch (Exception e){
            return ApiResult.error("更新失败");
        }
    }
    /**
     * 获取指定活动具体信息列表
     * */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public BaseResult listPromotionInfoList(Integer promotionCatagoryId,String cityCode,Integer status) {
        if(cityCode != null){
            cityCode = cityCode+"%";//前缀匹配查询
        }
        Map param  = new HashMap();
        param.put("promotionCatagoryId",promotionCatagoryId);
        param.put("cityCode",cityCode);
        param.put("status",status);
        List<PromotionInfoPO> promotionInfoList = promotionService.listPromotionInfoByCatagory(param);
        return ApiResult.success(promotionInfoList,promotionInfoList.size());
    }
    /**
     * 添加活动分类下添加具体信息
     * */
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseResult addPromotionInfo(PromotionInfoPO promotionInfo) {
        try {
            if(promotionInfo.getWeight() != null && promotionInfo.getWeight() > 0){
                if(promotionService.checkPromotionWeight(promotionInfo) > 0){
                    return ApiResult.error("大于0的权重不能重复设置");
                }
            }
            promotionInfo.setUpdateStaffId(getIdentify().getUserId());
            if(promotionService.addPromotionInfo(promotionInfo) > 0){
                return ApiResult.success();
            }
            else {
                return ApiResult.error("添加失败");
            }
        }
        catch (Exception e){
            return ApiResult.error("后台异常");
        }
    }
    /**
     * 更新活动信息
     * */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public BaseResult updatePromotionInfo(PromotionInfoPO promotionInfo) {
        try {
            promotionInfo.setUpdateStaffId(getIdentify().getUserId());
            if(promotionService.updatePromotionInfo(promotionInfo) > 0){
                return ApiResult.success();
            }
            else {
                return ApiResult.error("更新失败");
            }
        }
        catch (Exception e){
            return ApiResult.error("后台异常");
        }
    }
    /**
     * 活动分类下添加具体信息
     * */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public BaseResult deletePromotionInfo(Integer promotionInfoId) {
        try {
            if(promotionService.deletePromotionInfo(promotionInfoId) > 0){
                return ApiResult.success();
            }
            else {
                return ApiResult.error("删除失败");
            }
        }
        catch (Exception e){
            return ApiResult.error("后台异常");
        }
    }
    /**
     * 查询商家在售商品
     * */
    @ResponseBody
    @RequestMapping(value = "getVendorGoods", method = RequestMethod.GET)
    public BaseResult getVendorGoods(Long vendorId) {
        List<GoodsInfoPO> goodsList = promotionService.selectGoodsByVendorId(vendorId);
        return ApiResult.success(goodsList);
    }
}
