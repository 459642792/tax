package com.blueteam.base.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyBeanUtils {
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String mapToString(Map<String, String[]> map) {
        StringBuffer buf = new StringBuffer("params: { ");
        for (String key : map.keySet()) {
            String[] arr = (String[]) map.get(key);
            String s = StringUtil.join(arr, ",");
            buf.append(key).append("=").append(s).append(", ");
        }
        buf.deleteCharAt(buf.length() - 2);
        buf.append(" }");
        return buf.toString();
    }

    /**
     * bean 转化为实体
     *
     * @param bean
     * @return
     */
    public static HashMap<String, Object> beanToMap(Object bean) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (null == bean) {
            return map;
        }
        Class<?> clazz = bean.getClass();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : descriptors) {
            String propertyName = descriptor.getName();
            if (!"class".equals(propertyName)) {
                Method method = descriptor.getReadMethod();
                Object result;
                try {
                    result = method.invoke(bean);
                    if (null != result) {
                        map.put(propertyName, result);
                    } else {
                        map.put(propertyName, "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return map;
    }

    /**
     * map 转化为 bean
     *
     * @param clazz
     * @param map
     * @return
     */
    public static Object mapToBean(Class<?> clazz, HashMap<?, ?> map) {
        Object object = null;
        try {
            object = clazz.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : descriptors) {
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    Object[] args = new Object[1];
                    args[0] = value;
                    descriptor.getWriteMethod().invoke(object, args);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
