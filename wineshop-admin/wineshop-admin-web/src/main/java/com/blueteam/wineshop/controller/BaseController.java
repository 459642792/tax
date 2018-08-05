package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.Constants;
import com.blueteam.base.constant.Enums;
import com.blueteam.entity.dto.UserIdentify;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    protected String getIpAddr() {
        return getIpAddr(request);
    }


    /**
     * 获取当前用户ID
     *
     * @return
     */
    public int getCurrentUserID() {
        return getCurrentUserID(request);
    }

    public static int getCurrentUserID(HttpServletRequest request) {
        UserIdentify identify = (UserIdentify) request.getAttribute(Constants.LOGINIDENTIFY_KEY);
        if (identify == null)
            return 0;
        return identify.getUserId();
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
        return Enums.FlagEnumHelper.HasFlag(user.getUserType(), userType);
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

    protected String getNickName() {
        UserIdentify userIdentify = getIdentify();
        return userIdentify == null ? null : userIdentify.getUserName();
    }

    /**
     * 判断当前用户是否是商家用户
     *
     * @return
     */
    protected boolean isVendorUser() {
        return isUserType(getIdentify(), Enums.UserType.Vendor);
    }

}
