package com.blueteam.wineshop.base.web.interceptor.support;

import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.UserIdentify;

import java.lang.annotation.Annotation;

public interface LoginValidator {
    /**
     * 登录验证
     *
     * @param annotation 注解
     * @param identify   登录token 实体
     * @return
     */
    BaseResult loginVerify(Annotation annotation, UserIdentify identify);
}
