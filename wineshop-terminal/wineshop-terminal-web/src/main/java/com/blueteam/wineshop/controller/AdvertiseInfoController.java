package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Constants;
import com.blueteam.base.constant.EnabledOrDisabled;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.StringUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.entity.po.AdvertiseInfo;
import com.blueteam.wineshop.service.AdvertiseInfoService;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marx
 * <p>
 * AdvertiseInfoController.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
@Controller
@RequestMapping("/advert")
public class AdvertiseInfoController extends BaseController {
    @Autowired
    AdvertiseInfoService advertiseInfoService;

    /**
     * @param
     * @param CityCode
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/advertList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult advertiseInfoList(@RequestParam("CityCode") String CityCode,
                                        HttpServletResponse response) throws Exception {
        List<AdvertiseInfo> objadvertiseInfos = new ArrayList<AdvertiseInfo>();
        //平台方的
        List<AdvertiseInfo> objadvertiseInfo = advertiseInfoService.AdvertiseInfoList(Constants.CREATE_MANAGER_LISTIMAGE
                , CityCode);
        if (objadvertiseInfo.size() > 0) objadvertiseInfos.add(objadvertiseInfo.get(objadvertiseInfo.size() - 1));
        //商家的
        List<AdvertiseInfo> Lstadvertise = advertiseInfoService.AdvertiseInfoList(Constants.CREATE_VENDOR_LISTIMAGE
                , CityCode);
        for (int i = 0; i < Lstadvertise.size(); i++) {
            objadvertiseInfos.add(Lstadvertise.get(i));
        }
        return ApiResult.success(objadvertiseInfos);
    }

    /**
     * 获取运营商的广告
     *
     * @param cityCode 运营商管理区域下面的区域编码
     * @return
     */
    @ApiLogin
    @RequestMapping(value = "/carriers/list", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult carriersAdvList(String cityCode) {
        UserIdentify identify = getIdentify();
        if (!isUserType(identify, Enums.EnumUserType.Carriers.getValue()))
            return BaseResult.error("您不是运营商");

        List<AdvertiseInfo> list = advertiseInfoService.selectAdvByYYS(identify.getExtendId(), Constants.CREATE_VENDOR_LISTIMAGE, cityCode);
        if (list == null)
            list = ListUtils.EMPTY_LIST;
        for (AdvertiseInfo ad : list) {
            if (StringUtil.IsNullOrEmpty(ad.getForeignKey()))
                ad.setForeignKey("0");
        }
        return ApiResult.success(list);
    }

    /**
     * 创建运营商广告
     *
     * @param param
     * @return
     */
    @ApiLogin
    @RequestMapping(value = "/carriers/post", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postAdv(AdvertiseInfo param) {
        UserIdentify identify = getIdentify();
        if (!isUserType(identify, Enums.EnumUserType.Carriers.getValue()))
            return BaseResult.error("您不是运营商");

        if (param == null)
            return BaseResult.error("参数错误");

        if (StringUtil.IsNullOrEmpty(param.getImg()))
            return BaseResult.error("必须传入图片");

//		if(StringUtil.IsNullOrEmpty(param.getCityCode()))
//			return BaseResult.error("必须指定区域");

        param.setEnableFlag(EnabledOrDisabled.ENABLED);
        param.setTypeCode(Constants.CREATE_VENDOR_LISTIMAGE);
        param.setUpdateBy(identify.getUserName());
        param.setCreateBy(identify.getUserName());

        return advertiseInfoService.yysInsert(identify.getUserId(), param);
    }

    /**
     * 编辑广告
     *
     * @param param
     * @return
     */
    @ApiLogin
    @RequestMapping(value = "/carriers/edit", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult editAdv(AdvertiseInfo param) {
        UserIdentify identify = getIdentify();
        if (!isUserType(identify, Enums.EnumUserType.Carriers.getValue()))
            return BaseResult.error("您不是运营商");

        if (param == null)
            return BaseResult.error("参数错误");

        if (param.getId() <= 0)
            return BaseResult.error("必须指定编辑广告的ID");

        if (StringUtil.IsNullOrEmpty(param.getImg()))
            return BaseResult.error("必须传入图片");

//		if(StringUtil.IsNullOrEmpty(param.getCityCode()))
//			return BaseResult.error("必须指定区域");

        param.setUpdateBy(identify.getUserName());

        return advertiseInfoService.yysUpdate(identify.getUserId(), param);
    }

    /**
     * 交换广告位置
     *
     * @param advId 广告ID
     * @return
     */
    @ApiLogin
    @RequestMapping(value = "/carriers/switchSort")
    @ResponseBody
    public BaseResult switchSortNum(int advId) {
        UserIdentify identify = getIdentify();
        if (!isUserType(identify, Enums.EnumUserType.Carriers.getValue()))
            return BaseResult.error("您不是运营商");
        advertiseInfoService.switchSortNum(advId, Constants.CREATE_VENDOR_LISTIMAGE, identify.getExtendId(), null);
        return BaseResult.success();

    }

    /**
     * 删除运营商广告
     *
     * @param advId 广告ID
     * @return
     */
    @ApiLogin
    @RequestMapping(value = "/carriers/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult deleteAdv(int advId) {
        UserIdentify identify = getIdentify();
        if (!isUserType(identify, Enums.EnumUserType.Carriers.getValue()))
            return BaseResult.error("您不是运营商");

        return advertiseInfoService.deleteYysAdv(identify.getUserId(), advId);
    }

    @RequestMapping(value = "/clickCount", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult clickCount(int Id, HttpServletResponse response) {
        if (Id <= 0) {
            return BaseResult.error("传入参数不正确");
        }
        AdvertiseInfo objInfo = advertiseInfoService.findAdvByID(Id);
        if (null != objInfo) {
            objInfo.setClickCount(objInfo.getClickCount() + 1);
            advertiseInfoService.update(objInfo);
        }
        return ApiResult.success();
    }
}
