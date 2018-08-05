package com.blueteam.base.lang;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by  NastyNas on 17/8/14.
 * Map工具类
 */
public class RMap {
    /**
     * 组合一组对象为Map, 偶数位对象为键, 奇数位对象为值. asMap(键，值，键，值)
     */
    public static Map asMap(Object... objects) {
        Map m = new HashMap();
        for (int i = 0; i < objects.length; i += 2) {
            if (i + 1 < objects.length) {
                m.put(objects[i], objects[i + 1]);
            } else {
                m.put(objects[i], null);
            }
        }
        return m;
    }

    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static String getStr(Map m, Object key) {
        return getStr(m, key, null);
    }

    public static String getStr(Map m, Object key, String defaultValue) {
        if (m == null)
            return defaultValue;
        Object value = m.get(key);
        if (value == null)
            return defaultValue;
        return value.toString();
    }

    public static Number getNum(Map m, Object key) {
        if (m == null)
            return null;
        Object value = m.get(key);
        if (value == null)
            return null;
        if (value instanceof Number)
            return (Number) value;
        if (!(value instanceof String))
            return null;
        try {
            return NumberFormat.getInstance().parse((String) value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer getInt(Map m, Object key) {
        Number value = getNum(m, key);
        if (value == null)
            return null;
        return value instanceof Integer ? (Integer) value : new Integer(value.intValue());
    }

    public static Long getLong(Map m, Object key) {
        Number value = getNum(m, key);
        if (value == null)
            return null;
        return value instanceof Long ? (Long) value : new Long(value.longValue());
    }
}
