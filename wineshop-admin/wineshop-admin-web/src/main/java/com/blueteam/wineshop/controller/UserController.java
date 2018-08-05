package com.blueteam.wineshop.controller;

import com.blueteam.base.cache.redis.Redis;
import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Constants;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.JsonUtil;
import com.blueteam.base.util.RandomUtils;
import com.blueteam.base.util.VerificationUtil;
import com.blueteam.base.util.aliyun.SmsUtil;
import com.blueteam.base.util.weixin.WeiXinUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.ApiV2Result;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.CarriersLoginResult;
import com.blueteam.entity.dto.CarriersSearch;
import com.blueteam.entity.dto.UserInfoSearch;
import com.blueteam.entity.dto.VendorSearch;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.po.CarriersInfo;
import com.blueteam.entity.po.ThirdPartyUserInfo;
import com.blueteam.entity.po.UserInfo;
import com.blueteam.entity.po.VendorInfo;
import com.blueteam.wineshop.mapper.ThirdPartyUserInfoMapper;
import com.blueteam.wineshop.service.CarriersService;
import com.blueteam.wineshop.service.UserService;
import com.blueteam.wineshop.service.VendorInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.JedisCommands;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by libra on 2017/4/5.
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    VendorInfoService vendorService;

    @Autowired
    CarriersService carriersService;

    @Autowired
    ThirdPartyUserInfoMapper thirdPartyUserInfoMapper;
    private Logger logger = LogManager.getLogger(UserController.class);

    /**
     * 普通用户列表页面
     *
     * @return
     */
    @AdminApiLogin
    @RequestMapping("/user/userList")
    public ModelAndView index() {
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
        ModelAndView modelAndView = new ModelAndView("/UserManager/UserList");
        return modelAndView;
    }

    /**
     * 普通用户列表查询接口
     *
     * @param search
     * @return
     */
    @AdminApiLogin
    @RequestMapping(value = "/api/user/userList", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult selectUser(UserInfoSearch search) {
        if (search == null)
            search = new UserInfoSearch();

        search.setPageIndex(search.getPageIndex() <= 0 ? Constants.DEFAULT_ONE_PAGE_INDEX : search.getPageIndex());
        search.setPageSize(search.getPageSize() <= 0 ? Constants.DEFAULT_PAGE_SIZE : search.getPageSize());

        PageResult<List<UserInfo>> users = userService.selectUserStaticsByWhere(search);
        return ApiResult.success(users.getList(), users.getCount());
    }

    /**
     * 用户详情页
     *
     * @param id
     * @return
     */
    @AdminApiLogin
    @RequestMapping("/user/userInfo")
    public ModelAndView userInfo(int id) {
        ModelAndView modelAndView = new ModelAndView("/UserManager/UserInfo");
        modelAndView.getModel().put("userId", id);
//        UserInfo user = userService.getCityUserInfo(id);
        List<ThirdPartyUserInfo> listT = thirdPartyUserInfoMapper.listThirdPartyUserInfo(id, Enums.UserType.Every, Enums.ThirdPartyUserInfo.WEI_XIN, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
        if (null != listT && listT.size() != 0) {
            modelAndView.getModel().put("nikeName", listT.get(0).getThirdPartyNickName());
            modelAndView.getModel().put("headImage", listT.get(0).getThirdPartyHeadImage());
        } else {
            modelAndView.getModel().put("nikeName", "");
            modelAndView.getModel().put("headImage", "");
        }
        return modelAndView;
    }

    @AdminApiLogin
    @RequestMapping(value = "/api/user/userinfo", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getUserInfo(int id) {
        UserInfo ui = userService.selectByPrimaryKey(id);
        return ApiResult.success(ui);
    }


    /**
     * 商家列表
     *
     * @return
     */
    @AdminApiLogin
    @RequestMapping("/user/vendorList")
    public ModelAndView vendorList() {
        ModelAndView modelAndView = new ModelAndView("/UserManager/VendorList");
        return modelAndView;
    }


    /**
     * 商家列表查询接口
     *
     * @param search
     * @return
     */
    @AdminApiLogin
    @RequestMapping(value = "/api/user/vendorList", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult selectUser(VendorSearch search) {
        PageResult<List<VendorInfo>> list = vendorService.selectVendorListByWhere(search);
        if (list == null)
            return ApiResult.success(new ArrayList<VendorInfo>(), 0);
        return ApiResult.success(list.getList(), list.getCount());
    }


    /**
     * 运营商列表
     *
     * @return
     */
    @AdminApiLogin
    @RequestMapping("/user/carriersList")
    public ModelAndView carriersList() {
        ModelAndView modelAndView = new ModelAndView("/UserManager/CarriersList");
        return modelAndView;
    }

    /**
     * 运营商查询接口
     *
     * @param search
     * @return
     */
    @AdminApiLogin
    @RequestMapping(value = "/api/user/carriersList", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult carriersUsers(CarriersSearch search) {
        PageResult<List<CarriersInfo>> list = carriersService.selectCarriersByWhere(search);
        return ApiResult.success(list.getList(), list.getCount());
    }


    /**
     * 发送验证码
     *
     * @param phone 电话号码
     * @return
     */
    @RequestMapping("/user/sendCode")
    @ResponseBody
    @AdminApiLogin
    public BaseResult sendCode(@RequestParam String phone, HttpSession session) {
        String jsonText = WeiXinUtil.httpRequest(Constants.API_V2_WEBSITE + "/api/common/SendSMSCode?mobile=" + phone, "GET", null);
        ApiV2Result<Boolean> result = JsonUtil.deserialize(jsonText, ApiV2Result.class);
        if (result == null)
            return BaseResult.error("发送验证码失败");
        if (!result.isIsSucceed() && !result.getData())
            return BaseResult.error(result.getMsg());

        return BaseResult.success();
    }

    /**
     * 登录
     * <p>
     * 账号
     *
     * @param code 校验码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/user/carriers/login", "/login"}, method = RequestMethod.POST)
    public BaseResult login(HttpSession session, @RequestParam String phone, @RequestParam String code) {
        if (phone == null || code == null)
            return ApiResult.error("参数错误");

        phone = phone.trim();
        code = code.trim();

        if (phone.isEmpty() || code.isEmpty())
            return ApiResult.error("参数错误");

        if (!VerificationUtil.VerificationCode(session, phone, code))
            return BaseResult.error("错误的验证码");


        ApiResult<UserInfo> user = (ApiResult<UserInfo>) userService.loginByPhone(phone, getIpAddr(), Constants.UserInfoDataSource.PHONE_CLIENT);

        if (!user.isSuccess())
            return user;
        //TODO:暂时没有考虑性能，后面可以重构
        UserInfo getUser = userService.getCityUserInfo(user.getData().getId());
        int extendId = 0;
        CarriersInfo carriers = null;
        if (isUserType(getUser.getUsertypes(), Enums.UserType.Carriers)) {
            carriers = carriersService.selectForUser(getUser.getId());
            if (carriers == null)
                return BaseResult.error("错误的运营商");
            extendId = carriers.getId();
        } else {
            return ApiResult.error("非运营商不能登录");
        }
        CarriersLoginResult result = new CarriersLoginResult();
        result.setAccount(user.getData().getUsername());
        result.setUserId(user.getData().getId());
        result.setUser(getUser);
        result.setCarriers(carriers);
        result.setToken(VerificationUtil.getToken(getUser, extendId, Enums.UserType.Carriers));
        return ApiResult.success(result);
    }


}
