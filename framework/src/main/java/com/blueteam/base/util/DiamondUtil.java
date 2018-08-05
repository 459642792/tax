package com.blueteam.base.util;

import com.blueteam.base.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.n3r.diamond.client.Miner;

public class DiamondUtil {

    public static String getStr(String key) {
        String val = new Miner().getString(key);
        if (StringUtils.isEmpty(val)) {
            throw new AppException(key + " not found in config system");
        }
        return val;
    }

    public static String getStr(String key, String defaultValue) {
        String val = new Miner().getString(key);
        if (StringUtils.isEmpty(val)) {
            return defaultValue;
        }
        return val.trim();
    }

    public static String getPre(String pre, String key) {
        String val = new Miner().getMiner(pre).getString(key);
        if (StringUtils.isEmpty(val)) {
            return val;
        }
        return val.trim();
    }

    public static String getPre(String pre, String key, String defaultVal) {
        String thisVal = new Miner().getMiner(pre).getString(key);
        if (StringUtils.isEmpty(thisVal)) {
            return defaultVal;
        }
        return thisVal;
    }

    public static boolean getBool(String key) {
        return toBool(getStr(key));
    }

    public static boolean getBool(String pre, String key) {
        return toBool(getPre(pre, key));
    }

    public static boolean getBool(String key, boolean defaultValue) {
        String str = getStr(key);
        if (StringUtils.isEmpty(str)) {
            return defaultValue;
        }
        return toBool(str);
    }

    public static boolean getBool(String pre, String key, boolean defaultValue) {
        String str = getPre(pre, key);
        if (StringUtils.isEmpty(str)) {
            return defaultValue;
        }
        return toBool(str);
    }

    private static boolean toBool(String str) {
        return "true".equalsIgnoreCase(str) || "yes".equalsIgnoreCase(str) || "on".equalsIgnoreCase(str)
                || "y".equalsIgnoreCase(str);
    }

    public static void main(String[] args) {

        String s = DiamondUtil.getStr("REDIS_DATA", "redis.expiration");
        System.out.println(s);
    }

}
