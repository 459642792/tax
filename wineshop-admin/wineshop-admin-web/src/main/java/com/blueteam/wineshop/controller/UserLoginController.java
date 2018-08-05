package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.VerificationUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.LoginResult;
import com.blueteam.entity.po.CarriersInfo;
import com.blueteam.entity.po.UserInfo;
import com.blueteam.wineshop.service.CarriersService;
import com.blueteam.wineshop.service.OrderInfoService;
import com.blueteam.wineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by libra on 2017/4/13.
 */
@Controller
public class UserLoginController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    CarriersService carriersService;

    /**
     * 订单服务
     */
    @Autowired
    OrderInfoService orderInfoService;

    /**
     * @return
     */

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public ModelAndView login() {
        orderInfoService.selectPayUserInfo(2669);
        ModelAndView modelAndView = new ModelAndView("/Login");
        return modelAndView;
    }


    @RequestMapping(value = "/user/AdminLogin", method = RequestMethod.GET)
    public ModelAndView AdminLogin() {
        ModelAndView modelAndView = new ModelAndView("/AdminLogin");
        return modelAndView;
    }


    /**
     * 用户登录
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/api/user/login", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult login(@RequestParam String userName, @RequestParam String password, HttpServletResponse response, HttpServletRequest request) {
        if (userName == null || password == null)
            return ApiResult.error("参数错误");
        userName = userName.trim();
        password = password.trim();
        if (userName.isEmpty() || password.isEmpty())
            return ApiResult.error("参数错误");
        BaseResult user = userService.pwdLogin(userName, password);
        if (!user.isSuccess())
            return user;
        ApiResult<UserInfo> result = (ApiResult<UserInfo>) user;
        if (!isUserType(result.getData().getUsertypes(), Enums.UserType.Admin))
            return BaseResult.error("您不是管理员");

        String token = VerificationUtil.getToken(result.getData(), result.getData().getUsertypes());
        LoginResult loginResult = new LoginResult();
        loginResult.setAccount(result.getData().getUsername());
        loginResult.setToken(token);
        loginResult.setUserId(result.getData().getId());
        loginResult.setUser(result.getData());
        request.getSession().setAttribute("userName", userName);
        return ApiResult.success(loginResult);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    @ApiLogin(userType = Enums.UserType.Carriers | Enums.UserType.Admin, message = "必须为商家和管理员")
    @RequestMapping(value = "/api/user/current", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getLoginUser() {
        UserInfo userInfo = userService.selectByPrimaryKey(super.getCurrentUserID());
        if (super.isUserType(super.getIdentify(), Enums.UserType.Carriers)) {
            CarriersInfo vendorInfo = carriersService.getCarriersByID(super.getIdentify().getExtendId());
            if (vendorInfo == null)
                return BaseResult.error("您不是合法的运营商");
            userInfo.setNickname(vendorInfo.getName());
        }
        return ApiResult.success(userInfo);
    }

    /**
     * 用户退出登录
     *
     * @param
     * @return
     */
    @ApiLogin
    @RequestMapping(value = "/user/loginout", method = RequestMethod.GET)
    public ModelAndView loginOut(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        boolean isCarriers = super.isUserType(super.getIdentify(), Enums.UserType.Carriers);
        ModelAndView modelAndView = new ModelAndView(isCarriers ? "/Login" : "/AdminLogin");
        modelAndView.getModel().put("loginout", true);
        return modelAndView;
    }
}
