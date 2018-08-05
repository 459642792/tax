package com.blueteam.base.util;


import com.blueteam.base.constant.Constants;

/**
 * 项目类
 *
 * @author libra
 */
public class ProjectUtil {
    private static final String DEBUG = "debug";
    private static final String RELEASE = "release";

    /**
     * 是否是debug版本
     *
     * @return
     */
    public static boolean isDebug() {
        return DEBUG.equalsIgnoreCase(Constants.EDITION);
    }
}
