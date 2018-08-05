package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.DateUtil;
import com.blueteam.base.util.FileTools;
import com.blueteam.base.util.StringUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.AdvertiseInfo;
import com.blueteam.entity.po.VendorInfo;
import com.blueteam.wineshop.service.AdvertiseInfoService;
import com.blueteam.wineshop.service.VendorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by clam on 2017/4/14.
 */
@Controller
@AdminApiLogin
public class VendorController extends BaseController {

    /**
     * service
     */
    @Autowired
    VendorInfoService service;
    @Autowired
    AdvertiseInfoService advertiseInfoService;

    /**
     * 添加和编辑商家
     *
     * @param id 商家ID
     * @return
     */
    @RequestMapping("/vendor/addOrEditVendor")
    public ModelAndView addOrEditVendor(int id) {
        ModelAndView modelAndView = new ModelAndView("/UserManager/" + (id > 0 ? "EditVendor" : "AddVendor"));
        return modelAndView;
    }

    /**
     * 店铺信息
     *
     * @param id 商家ID
     * @return
     */
    @RequestMapping("/vendor/vendorInfo")
    public ModelAndView vendorInfo(int id) {
        ModelAndView modelAndView = new ModelAndView("/UserManager/VendorInfo");
        modelAndView.getModel().put("id", id);
        return modelAndView;
    }


    /**
     * 查询商家信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/vendor/get", method = RequestMethod.GET)
    public BaseResult get(int id) {
        if (id <= 0)
            return ApiResult.success(new VendorInfo());

        VendorInfo model = service.getVendorById(id);
        return ApiResult.success(model);
    }

    /**
     * @param vendorInfo
     * @return
     */
    @RequestMapping(value = "/api/vendor/post", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult post(VendorInfo vendorInfo) throws Exception {
        if (vendorInfo == null)
            return BaseResult.error("错误的参数");
        if (vendorInfo.getId() != null && vendorInfo.getId() > 0) {

        }

        String createBy = super.getUserName();
        vendorInfo.setCreateBy(createBy);
        vendorInfo.setUpdateBy(createBy);

        return service.adminAddVendor(vendorInfo.getTelephone(), vendorInfo);
    }

    /**
     * 根据区域CODE获取商家
     *
     * @param cityCode 区域CODE
     * @return
     */
    @RequestMapping(value = "/api/vendor/getAreaVendors", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getVendorsByCityCode(String cityCode) {
        String code = cityCode + "%";
        VendorInfo search = new VendorInfo();
        search.setCityCode(code);
        List<VendorInfo> vendorInfoList = service.selectVendorByAreas(search);
        return ApiResult.success(vendorInfoList);
    }

    /**
     * 认证店铺页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/vendor/auth", method = RequestMethod.GET)
    public ModelAndView authVendorPage(int id) {
        ModelAndView modelAndView = new ModelAndView("/UserManager/AuthenticationVendor");
        modelAndView.getModel().put("id", id);
        return modelAndView;
    }


    /**
     * 审核店铺页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/vendor/review", method = RequestMethod.GET)
    public ModelAndView review(int id) {
        ModelAndView modelAndView = new ModelAndView("/UserManager/VendorAuthenticate");
        modelAndView.getModel().put("id", id);
        return modelAndView;
    }

    /**
     * 管理员认证商家
     *
     * @param param 认证信息
     * @return
     */
    @RequestMapping(value = "/api/vendor/adminAuthenticateVendor", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult adminAuthenticateVendor(VendorInfo param) {
        if (param == null || param.getId() == null || param.getId() <= 0)
            return BaseResult.error("参数错误");
        param.setUpdateBy(super.getUserName());
        BaseResult result = service.adminAuthenticateVendor(super.getCurrentUserID(), param);
        ;
        result.setReturnId(param.getId().toString());
        return result;
    }

    /**
     * 认证审核商家，加V
     *
     * @param vendorId   待认证商家ID
     * @param authStatus 认证的目标状态
     * @param reason     认证通过/不通过 的理由
     * @return
     */
    @RequestMapping(value = "/api/vendor/adminAuthenticate", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult adminAuthenticate(int vendorId, int authStatus, String reason) {

        Enums.VendorStatus status = null;
        for (Enums.VendorStatus en : Enums.VendorStatus.values()) {
            if (en.getValue() == authStatus) {
                status = en;
                break;
            }
        }
        if (status == null)
            return BaseResult.error("错误的审核状态");

        if (status == Enums.VendorStatus.CheckNotAccess && StringUtil.IsNullOrEmpty(reason))
            return BaseResult.error("请输入拒绝理由");

        BaseResult result = service.adminAuthenticate(super.getCurrentUserID(), vendorId, status, reason);
        if (result.isSuccess())
            result.setReturnId(String.valueOf(vendorId));
        return result;
        //   return BaseResult.success();
    }

    /**
     * 编辑商家名称
     *
     * @param name 需要编辑的商家名称
     * @param id   商家名称
     * @return
     */
    @RequestMapping(value = "/api/vendor/editVendorName", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult editVendorName(int id, String name) throws Exception {
        if (StringUtil.IsNullOrEmpty(name))
            return BaseResult.error("请输入一个商家名称");
        VendorInfo vendor = service.getVendorById(id);
        if (vendor == null)
            return BaseResult.error("没有找到对应的商家");
        vendor.setName(name);
        vendor.setUpdateBy(super.getUserName());
        vendor.setUpdateDate(DateUtil.formatDate(new Date()));
        int result = service.updateByPrimaryKey(vendor);
        if (result > 0)
            return BaseResult.success();
        return BaseResult.error("编辑商家名称错误");
    }

    /**
     * 获取商家的二维码
     *
     * @return
     */
    @RequestMapping(value = "/api/vendor/getVendorQrcode", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getVendorQrcode(int vendorId) throws Exception {

        String qrcodeImg = new FileTools().getQrcodeToWeb(vendorId, request);
        return ApiResult.success(qrcodeImg);
    }


    /**
     * @param ForeignKey
     * @param TypeCode
     * @return
     */
    @RequestMapping(value = "/vendor/vendorPhotoList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult vendorDetailPhoto(@RequestParam("ForeignKey") int ForeignKey, @RequestParam("TypeCode") String TypeCode, @RequestParam("pageSize") int pageSize, @RequestParam("pageIndex") int pageIndex) {
        //获取商家店铺图片
        List<AdvertiseInfo> objadvertiseInfo = advertiseInfoService.VendorPhotoList(ForeignKey, TypeCode);
        int count = (pageIndex - 1) * pageSize;

        //TODO:这段代码似乎是无必要的哦
        int count2 = pageIndex * pageSize;
        if (objadvertiseInfo.size() - pageIndex * pageSize < pageSize) {
            count2 = objadvertiseInfo.size();
        }

        int skipEnd = pageIndex * pageSize;

        List<AdvertiseInfo> lstRetrunVendor = new ArrayList<AdvertiseInfo>();
        for (int i = count; i < skipEnd && i < objadvertiseInfo.size(); i++) {
            lstRetrunVendor.add(objadvertiseInfo.get(i));
        }
        return ApiResult.success(lstRetrunVendor, objadvertiseInfo.size());
    }
}
