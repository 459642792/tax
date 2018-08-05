package com.blueteam.wineshop.base.spring.aop.activemq;

import com.blueteam.base.constant.Constants;
import com.blueteam.base.lang.RStr;
import com.blueteam.base.util.ExceptionUtil;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.entity.dto.SendMessage;
import com.blueteam.wineshop.base.spring.aop.activemq.help.MqPushConfigMgr;
import com.blueteam.wineshop.base.web.interceptor.LoginInterceptorAdmin;
import com.blueteam.wineshop.service.MqService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by  NastyNas on 17/8/26.
 * <p/>
 * <p/>
 * 基于aop的ActiveMq消息推送器,将请求信息推送至消息队列中
 */
@Aspect
@Component
public class MessagePusherBasedAop {

    private static final Logger logger = LoggerFactory.getLogger(MessagePusherBasedAop.class);
    @Autowired
    HttpServletRequest request;
    @Autowired
    MqService mqService;
    @Autowired
    MqPushConfigMgr mqPushConfigMgr;

    @Pointcut("execution(* com.blueteam.wineshop.controller..*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerJoinPoint() {
    }

    @Around("controllerJoinPoint()")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        if (!(result instanceof BaseResult))
            return result;
        BaseResult baseResult = (BaseResult) result;
        if (!baseResult.isSuccess())
            return result;
        try {
            //请求成功时将请求信息和用户信息推送到消息队列中
            invokeMessagePush(joinPoint, baseResult);
        } catch (Exception ex) {
            logger.error("admin工程请求成功信息推送失败,失败原因:{}", ExceptionUtil.stackTraceString(ex));
        }
        return result;
    }

    /**
     * 向Mq中推送数据
     *
     * @param joinPoint
     * @param result
     */
    private void invokeMessagePush(ProceedingJoinPoint joinPoint, BaseResult result) {
        //基于aspectJ的aop获取切点请求信息
        MethodParameter[] parameters = (MethodParameter[]) request.getAttribute(LoginInterceptorAdmin.REQUEST_PARAM_NAMES);
        Object[] objects = joinPoint.getArgs();
        if (parameters != null && parameters.length != objects.length) return;
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        String clsName = targetMethod.getDeclaringClass().getName();
        String[] clsNames = clsName.split("\\.");
        //获取请求controller类名和action方法
        String controllerName = clsNames[clsNames.length - 1];
        String actionName = targetMethod.getName();
        //判断是否需要推送
        Integer userType = mqPushConfigMgr.obtainUserType(controllerName, actionName);
        UserIdentify identify = obtainUserIdentify();
        //不需要推送
        if (userType < 0 || (userType != 0 && identify != null && userType != identify.getCurUType())) return;
        //推送消息
        SendMessage message = new SendMessage();
        message.setParamters(prepareParam(objects, parameters, identify, result));
        message.setController(controllerName);
        message.setAction(actionName);
        message.setDataSource("");
        message.setPlatform("");
        message.setPushServerVersion("");
        message.setSoftApp("");
        message.setCreateBy(identify.getUserId());
        message.setLastUpdateBy(identify.getUserId());
        message.setDataKey(RStr.isEmpty(result.getReturnId()) ? "" : result.getReturnId());
        mqService.sendMessage(message);
    }

    /**
     * 准备请求参数
     *
     * @param objects
     * @param parameters
     * @param identify
     * @param result
     * @return
     */
    private HashMap prepareParam(Object[] objects, MethodParameter[] parameters, UserIdentify identify, BaseResult result) {
        HashMap paramMap = new HashMap();
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null || !(objects[i] instanceof Serializable))
                continue;
            objects[i].getClass().getInterfaces();
            paramMap.put(parameters[i].getParameterName(), objects[i]);
        }
        paramMap.put(Constants.USER_ID, identify.getUserId());
        paramMap.put(Constants.EXTEND_ID, identify.getExtendId());
        paramMap.put(Constants.MESSAGE_RESULT_KEY, result.getReturnId() == null ? 0 : result.getReturnId());
        return paramMap;
    }


    /**
     * 获取已登录用户的用户标识
     *
     * @return
     */
    public UserIdentify obtainUserIdentify() {
        UserIdentify identify = (UserIdentify) request.getAttribute(Constants.LOGINIDENTIFY_KEY);
        return identify;
    }
}
