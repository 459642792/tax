package com.blueteam.base.util;

/**
 * 字段校验工具类，暂不支持成员为数组类型
 * Created by  NastyNas on 2017/10/28.
 */

import com.blueteam.base.constant.FieldValidateConstants;
import com.blueteam.base.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

public class FieldValidateUtil {
    /**
     * DTO数据完整性校验
     *
     * @param object
     */
    public static void validate(Object object) {
        //非集合类型
        if (!Collection.class.isInstance(object)) {
            validateField(object);
        }
        //集合类型
        if (Collection.class.isInstance(object)) {
            Collection<Object> collection = (Collection<Object>) object;
            for (Object obj : collection) {
                validateField(obj);
            }
        }
    }


    public static void validateField(Object object) {
        //获取object的类型
        Class<? extends Object> clazz = object.getClass();
        //获取该类型声明的成员
        Field[] fields = clazz.getDeclaredFields();
        //遍历属性
        for (Field field : fields) {
            //对于private私有化的成员变量，通过setAccessible来修改器访问权限
            field.setAccessible(true);
            validateDetail(field, object);
            //重新设置回私有权限
            field.setAccessible(false);
        }
    }


    private static void validateDetail(Field field, Object object) {
        //获取对象的成员的注解信息
        FieldValidate lv = field.getAnnotation(FieldValidate.class);
        if (lv == null) return;
        Object value;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            throw new BusinessException("IllegalAccessException", e);
        }

        if (lv.notNullValidateTypes() != null && lv.notNullValidateTypes().length != 0) {
            String currentBusinessType;
            String currentBusinessTypeName;
            try {
                currentBusinessType = object.getClass().getDeclaredMethod("getValidateType").invoke(object).toString();
                currentBusinessTypeName = FieldValidateConstants.ValidateTypeEnum.valueOf(currentBusinessType).getValidateTypeName();
            } catch (Exception e) {
                throw new BusinessException("多业务类别的非空校验获取业务类别失败", e);
            }
            String[] businessTypes = lv.notNullValidateTypes();
            for (String businessType : businessTypes) {
                if (businessType.equals(currentBusinessType) && (value == null || StringUtils.isBlank(value.toString()))) {
                    throw new BusinessException("字段" + field.getName() + "的值在" + currentBusinessTypeName + "中不能为空");
                }
            }
        }
        if (lv.notNull()) {
            if (value == null || StringUtils.isBlank(value.toString())) {
                throw new BusinessException("字段" + field.getName() + "的值不能为空");
            }
        }
        if (value != null) {
            if (lv.fixLen() != 0 && value.toString().length() != lv.fixLen()) {
                throw new BusinessException("字段" + field.getName() + "的值的长度不等于" + lv.fixLen());
            }

            if (lv.maxLen() != 0 && value.toString().length() > lv.maxLen()) {
                throw new BusinessException("字段" + field.getName() + "的值的长度不能超过" + lv.maxLen());
            }

            if (lv.minLen() != 0 && value.toString().length() < lv.minLen()) {
                throw new BusinessException("字段" + field.getName() + "的值的长度不能小于" + lv.minLen());
            }
            if (!isPrimitive(value.getClass())) {
                validate(value);
            }
        }

    }


    public static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() //
                || clazz == Boolean.class //
                || clazz == Character.class //
                || clazz == Byte.class //
                || clazz == Short.class //
                || clazz == Integer.class //
                || clazz == Long.class //
                || clazz == Float.class //
                || clazz == Double.class //
                || clazz == BigInteger.class //
                || clazz == BigDecimal.class //
                || clazz == String.class //
                || clazz == java.util.Date.class //
                || clazz == java.sql.Date.class //
                || clazz == java.sql.Time.class //
                || clazz == java.sql.Timestamp.class //
                || clazz.isEnum() //
                ;
    }
}
