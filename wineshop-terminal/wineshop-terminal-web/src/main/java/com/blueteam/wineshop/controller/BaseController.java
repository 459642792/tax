package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.Constants;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.ClientUtils;
import com.blueteam.entity.dto.UserIdentify;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class BaseController {
    @Autowired
    HttpServletRequest request; //这里可以获取到reques
    private Logger logger = LogManager.getLogger(this.getClass());

    /**
     * @param @return
     * @return String
     * @throws
     * @Title: getIpAddr
     * @author kaka  www.zuidaima.com
     * @Description: 获取客户端IP地址
     */


    protected String getIpAddr() {
        return ClientUtils.obtainIp(request);
    }


    /**
     * 获取当前用户ID
     *
     * @return
     */
    public int getCurrentUserID() {
        return ClientUtils.obtainUserId(request);
    }


    /**
     * 获取用户登录标示
     *
     * @return 用户登录标示
     */
    public UserIdentify getIdentify() {
        UserIdentify identify = (UserIdentify) request.getAttribute(Constants.LOGINIDENTIFY_KEY);
        return identify;
    }

    /**
     * 是否是指定用户类型
     *
     * @param user     需要检测的用户
     * @param userType 需要包含的用户类型
     * @return
     */
    protected boolean isUserType(UserIdentify user, int userType) {
        if (user == null)
            return false;
        return Enums.FlagEnumHelper.HasFlag(user.getCurUType(), userType);
    }

    /**
     * 获取Token
     *
     * @return
     */
    protected String getToken() {
        return request.getHeader(Constants.ACCESS_TOKEN);
    }

    /**
     * 是否是指定用户类型
     *
     * @param ownUserType    需要检测的用户类型
     * @param targetUserType 需要包含的用户类型
     * @return
     */
    protected boolean isUserType(int ownUserType, int targetUserType) {
        return Enums.FlagEnumHelper.HasFlag(ownUserType, targetUserType);
    }

    protected String getCurrentDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        return date;
    }

    protected String getUserName() {
        UserIdentify userIdentify = getIdentify();
        return userIdentify == null ? null : userIdentify.getUserName();
    }

    /**
     * 判断当前用户是否是商家用户
     *
     * @return
     */
    protected boolean isVendorUser() {
        UserIdentify userIdentify = getIdentify();
        return isUserType(userIdentify, Enums.UserType.Vendor);
    }

}
