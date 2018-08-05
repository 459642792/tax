package com.blueteam.wineshop.base.web.component;

import com.blueteam.base.constant.Constants;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.exception.MessageException;
import com.blueteam.base.util.ClientUtils;
import com.blueteam.base.util.MyBeanUtils;
import com.blueteam.base.util.StringUtil;
import com.blueteam.base.util.json.JSONObject;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.ExceptionWrite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created by  NastyNas on 17/8/11.
 * <p/>
 * 全局异常处理器
 */
public class ExceptionHandlerResolver implements HandlerExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerResolver.class);
    private static final String DEFAULT_MSG = "系统繁忙，请稍候再试。";
    private static final String AJAX_MSG_CODE = "400";
    private static final String AJAX_SYSERR_CODE = "500";

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        //@TODO 普通提交异常jsp未设计，待实现
//        if (isAjaxRequest(request)) {
        //处理异步请求异常
        return handleAjaxException(ex, request, response);
//        }
        //处理普通提交请求异常
//        return handlerFormException(ex, request);
    }


    /**
     * 处理异步请求
     *
     * @param ex
     * @param request
     */
    private ModelAndView handleAjaxException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        //application/json类型的ModelAndView，用于处理ajax返回
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        try {
            boolean handled = false;
            Throwable throwEx = ex;
            while (throwEx != null) {
                // 前台一页面需要引用一个公共js用弹窗形式展示ajax请求业务异常消息
                // MessageException 需要页面弹框展示异常信息，不是系统异常，因此不需要记录异常信息，是业务级的处理
                if (throwEx instanceof MessageException) {
                    modelAndView.addAllObjects(MyBeanUtils.beanToMap(BaseResult.error(AJAX_MSG_CODE, throwEx.getMessage())));
                    handled = true;
                } else if (throwEx instanceof BusinessException) {
                    String messageCode = ((BusinessException) throwEx).getMessageCode();
                    //登录拦截跳转
                    if (checkIsLoginError(messageCode)) {
                        BaseResult result = BaseResult.error(messageCode, throwEx.getMessage());
                        JSONObject json = new JSONObject(result);
                        response.setContentType("application/json; charset=UTF-8");
                        response.getWriter().write(json.toString());
                        response.sendRedirect(request.getContextPath() + "/user/login?fromUrl=" + URLEncoder.encode(getRequestUrl(request), "UTF-8"));
                    } else {
                        modelAndView.addAllObjects(MyBeanUtils.beanToMap(BaseResult.error(((BusinessException) throwEx).getMessageCode(), throwEx.getMessage())));
                    }
                    handled = true;
                } else {
                    modelAndView.addAllObjects(MyBeanUtils.beanToMap(BaseResult.error(AJAX_SYSERR_CODE, DEFAULT_MSG)));
                }
                throwEx = throwEx.getCause();
            }
            //系统异常记录日志
            if (!handled) {
                logExcMsg(throwEx, request);
            }
        } catch (Exception e) {
            try {
                logExcMsg(e, request);
                modelAndView.addAllObjects(MyBeanUtils.beanToMap(BaseResult.error(AJAX_SYSERR_CODE, DEFAULT_MSG)));
            } catch (Exception ee) {
                logger.error(ee.getMessage());
            }

        }
        return modelAndView;
    }

    /**
     * 验证是否为登录拦截异常
     *
     * @param messageCode
     * @return
     */
    private boolean checkIsLoginError(String messageCode) {
        boolean isLoginError = false;
        for (Enums.LoginState loginState : Enums.LoginState.values()) {
            if (String.valueOf(loginState.getValue()).equals(messageCode)) {
                isLoginError = true;
                break;
            }
        }
        return isLoginError;
    }

    /**
     * 处理普通请求
     *
     * @param ex
     * @param request
     * @return
     */
    private ModelAndView handlerFormException(Exception ex, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            boolean handled = false;
            Throwable throwEx = ex;
            while (throwEx != null) {
                if (throwEx instanceof MessageException) {
                    //@TODO 消息异常只在当前页弹出一个消息框，不会跳转到错误页面
                    handled = true;
                } else {
                    modelAndView.setViewName("error");
                }
                throwEx = throwEx.getCause();
            }
            if (!handled) {
                logExcMsg(throwEx, request);
            }
        } catch (Exception e) {
            try {
                logExcMsg(e, request);
                modelAndView.setViewName("error");
            } catch (Exception ee) {
                logger.error(ee.getMessage());
            }
        }
        return modelAndView;
    }


    /**
     * 记录异常日志
     *
     * @param ex
     * @param request
     */
    private void logExcMsg(Throwable ex, HttpServletRequest request) {
        ExceptionWrite logBean = new ExceptionWrite();
        logBean.setEx(ex);
        logBean.setRequestIp(ClientUtils.obtainIp(request));
        logBean.setRequestParamter(MyBeanUtils.mapToString(request.getParameterMap()));
        logBean.setRequestToken(request.getHeader(Constants.ACCESS_TOKEN));
        logBean.setRequestUrl(request.getRequestURL().toString());
        logBean.setRequestUserAgent(request.getHeader("User-Agent"));
        logBean.setUserId(ClientUtils.obtainUserId(request));
        logger.error(String.valueOf(logBean));

    }

    /**
     * 判断是否ajax请求
     *
     * @param request
     * @return
     */

    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header)) {
            return true;
        }
        return false;
    }

    /**
     * 获取请求域名
     *
     * @param request
     * @return
     */
    private String getRequestUrl(HttpServletRequest request) {
        String path = request.getContextPath();
        String domainUrl = getRequestDomain(request);
        if (!StringUtil.IsNullOrEmpty(path))
            domainUrl += path;
        domainUrl += "/#" + request.getRequestURI();
        return domainUrl;
    }

    /**
     * 获取请求域名部分，包括端口
     *
     * @param request
     * @return
     */
    private String getRequestDomain(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + (request.getServerPort() == 80 || request.getServerPort() == 443 ? "" : (":" + request.getServerPort()));
    }
}
