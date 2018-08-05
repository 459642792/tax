package com.blueteam.base.help.generator;

import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.lang.Clazz;
import com.blueteam.base.lang.RMap;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * key生成器匹配策略类
 * Created by  NastyNas on 2017/12/26.
 */
public class KeyGeneratorStrategy {
    private static Map cacheMap = Maps.newHashMap();

    static {
        System.out.println("dfasdf");
        Clazz.getClasses("com/blueteam/base/help/generator/impl/*.class", new Predicate() {
            @Override
            public boolean apply(Object object) {
                Class<?> clazz = (Class<?>) object;
                KeyGeneratorImpl annotation = clazz.getAnnotation(KeyGeneratorImpl.class);
                if (annotation != null && KeyGenerator.class.isAssignableFrom(clazz)) {
                    String key = annotation.key();
                    String classpath = clazz.getCanonicalName();
                    cacheMap.put(key, classpath);
                    return true;
                }
                return false;
            }
        });
    }

    public static KeyGenerator match(KeyGeneratorEnum keyGeneratorEnum) {
        String path = RMap.getStr(cacheMap, keyGeneratorEnum.getKey());
        if (StringUtils.isEmpty(path)) {
            throw new BusinessException("key生成器无此实现策略，key=[" + keyGeneratorEnum.getKey() + "]");
        }
        KeyGenerator keyGenerator = Clazz.newInstance(path);
        return keyGenerator;
    }

    public static void main(String[] args) {
        System.out.println(KeyGeneratorStrategy.match(KeyGeneratorEnum.ORDER_ID).generateKey());
        System.out.println(KeyGeneratorStrategy.match(KeyGeneratorEnum.ORDER_NO).generateKey());
    }


}
