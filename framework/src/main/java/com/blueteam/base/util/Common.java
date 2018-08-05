package com.blueteam.base.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Marx
 * <p>
 * Common.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public class Common {
    public static String getIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");
        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded;
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                ip = realIp + "/" + forwarded.replaceAll(", " + realIp, "");
            }
        }
        return ip;
    }

    /**
     * 浏览量统计的key
     * @return
     */
    public static String getRedisKeyOfPageView(Date date,Integer vendorId){
        String key=vendorId+"-pageView-"+DateUtil.format(date,"yyyy-MM-dd");
        return key;
    }


}
