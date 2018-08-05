package com.blueteam.wineshop.base.web.interceptor;

import com.blueteam.base.constant.*;
import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.lang.RList;
import com.blueteam.base.lang.RStr;
import com.blueteam.base.util.ExceptionUtil;
import com.blueteam.base.util.StringUtil;
import com.blueteam.base.util.VerificationUtil;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.entity.po.UserInfo;
import com.blueteam.wineshop.base.web.interceptor.support.LoginValidator;
import com.blueteam.wineshop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URLDecoder;
import java.util.*;

/**
 * Refactor by NastyNas on 17/9/20.
 * 登录拦截器
 */
@Component
public class LoginInterceptorTerminal extends HandlerInterceptorAdapter {

    public static final String REQUEST_PARAM_NAMES = "REQUEST_PARAM_NAMES";
    private static List<Class<? extends Annotation>> ANNOTATION_TYPES = null;
    private static List<Class<? extends Annotation>> BASE_ANNOTATION_TYPES = null;
    private static Map<Class<?>, LoginValidator> LoginValidators = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptorTerminal.class);
    @Autowired
    UserService userService;

    static {
        logger.info("terminal登录校验器加载start");
        ANNOTATION_TYPES = RList.asList(ApiLogin.class, ExtendApiLogin.class, CarriersApiLogin.class, VendorApiLogin.class, AdminApiLogin.class);
        BASE_ANNOTATION_TYPES = RList.asList(ApiLogin.class, ExtendApiLogin.class);
        LoginValidators.put(ApiLogin.class, new LoginValidator() {
            public BaseResult loginVerify(Annotation annotation, UserIdentify identify) {
                ApiLogin api = (ApiLogin) annotation;
                //用户类型不匹配
                if (!Enums.FlagEnumHelper.HasFlag(identify.getUserType(), api.userType())) {
                    return BaseResult.error(String.valueOf(Enums.LoginState.ERROR_USER_TYPE.getValue()), Enums.LoginState.ERROR_USER_TYPE.getDescription());
                }
                return BaseResult.success();
            }
        });
        LoginValidators.put(ExtendApiLogin.class, new LoginValidator() {
            public BaseResult loginVerify(Annotation annotation, UserIdentify identify) {
                //extendId<=0不满足登录要求
                if (identify.getExtendId() <= 0) {
                    return BaseResult.error(String.valueOf(Enums.LoginState.ERROR_USER_EXTEND.getValue()), Enums.LoginState.ERROR_USER_EXTEND.getDescription());
                }
                return BaseResult.success();
            }
        });
        //集成ApiLogin和ExtendApiLogin的登录校验器
        LoginValidator extendsValidator = new LoginValidator() {
            public BaseResult loginVerify(Annotation annotation, UserIdentify identify) {
                Class<? extends Annotation> clazz = annotation.annotationType();
                BaseResult result = null;
                for (Class<? extends Annotation> cs : BASE_ANNOTATION_TYPES) {
                    Annotation base = clazz.getAnnotation(cs);
                    if (base != null && LoginValidators.containsKey(cs)) {
                        result = LoginValidators.get(cs).loginVerify(base, identify);
                        if (!result.isSuccess()) {
                            return result;
                        }
                    }
                }
                if (result == null) {
                    return BaseResult.success();
                }
                return result;
            }
        };
        LoginValidators.put(VendorApiLogin.class, extendsValidator);
        LoginValidators.put(CarriersApiLogin.class, extendsValidator);
        LoginValidators.put(AdminApiLogin.class, extendsValidator);
        logger.info("terminal登录校验器加载end");
    }


    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * <p>
     * 如果返回true
     * 执行下一个拦截器,直到所有的拦截器都执行完毕
     * 再执行被拦截的Controller
     * 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (HandlerMethod.class.equals(handler.getClass())) {
            HandlerMethod method = (HandlerMethod) handler;
            request.setAttribute(REQUEST_PARAM_NAMES, method.getMethodParameters());
            //获取请求类和方法上的登录拦截注解
            List<Annotation> annotationList = obtainLoginAnnotation(method);
            if (annotationList != null && annotationList.size() > 0) {
                checkIsLogin(request, annotationList);
                return true;
            }
        }
        return true;
    }

    /**
     * 校验登录
     *
     * @param request
     * @param annotations
     * @return
     * @throws Exception
     */
    private void checkIsLogin(HttpServletRequest request, List<Annotation> annotations) throws Exception {
        //从请求cookie中获取token
        String encodedToken = obtainTokenFromRequest(request);
        String token = decodeToken(encodedToken);
        //获取用户登录信息
        UserIdentify userIdentify = obtainUserIdentify(token);
        //登录校验
        loginValidate(annotations, userIdentify);
        //校验成功，更新登录信息
        updateLoginInfo(userIdentify);
        //校验成功,请求中设置用户登录信息
        request.setAttribute(Constants.LOGINIDENTIFY_KEY, userIdentify);
    }

    private void updateLoginInfo(UserIdentify userIdentify) {
        try {
            //登录用户信息
            UserInfo userInfo = userService.selectByPrimaryKey(userIdentify.getUserId());
            if (userInfo != null) {
                UserInfo latestUserInfo = new UserInfo();
                latestUserInfo.setId(userIdentify.getUserId());
                latestUserInfo.setLogintime(new Date());
                userService.updateUser(latestUserInfo);
            }
        } catch (Exception e) {
            logger.error("用户登录时间更新失败,用户信息userIdentify:{},失败原因:{}", userIdentify, ExceptionUtil.stackTraceString(e));
        }
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

    private String getRequestUrl(HttpServletRequest request) {
        String path = request.getContextPath();
        String domainUrl = getRequestDomain(request);
        if (!StringUtil.IsNullOrEmpty(path))
            domainUrl += path;
        domainUrl += "/#" + request.getRequestURI();
        return domainUrl;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        SetResponseHeaders(response);

    }

    private void loginValidate(List<Annotation> annotations, UserIdentify userIdentify) {
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> clazz = annotation.annotationType();
            if (LoginValidators.containsKey(clazz)) {
                BaseResult result = LoginValidators.get(clazz).loginVerify(annotation, userIdentify);
                if (!result.isSuccess()) {
                    throw new BusinessException(result.getStatus(), result.getMessage());
                }
            }
        }
    }

    private UserIdentify obtainUserIdentify(String token) {
        UserIdentify identify = VerificationUtil.getUserIdentify(token);
        if (identify == null) {
            throw new BusinessException(String.valueOf(Enums.LoginState.ERROR_TOKEN.getValue()), Enums.LoginState.ERROR_TOKEN.getDescription());
        }
        if (VerificationUtil.CheckExpire(identify)) {
            throw new BusinessException(String.valueOf(Enums.LoginState.EXPIRE_TOKEN.getValue()), Enums.LoginState.EXPIRE_TOKEN.getDescription());

        }
        return identify;
    }

    private String decodeToken(String encodedToken) {
        if (RStr.isEmpty(encodedToken)) {
            logger.info("admin登录token获取失败");
            throw new BusinessException(String.valueOf(Enums.LoginState.NOT_AUTH_TOKEN.getValue()), Enums.LoginState.NOT_AUTH_TOKEN.getDescription());
        }
        String token = null;
        try {
            token = URLDecoder.decode(encodedToken, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("admin登录token解码失败,encodedToken:{},失败原因:{}", encodedToken, ExceptionUtil.stackTraceString(e));
        }
        return token;
    }


    private String obtainTokenFromRequest(HttpServletRequest request) {
        //terminal工程对应的终端请求，将登录信息放在请求头中
        String token = request.getHeader(Constants.ACCESS_TOKEN);
        if (token == null || token.isEmpty()) {
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length > 0){
                for (Cookie cookie : cookies) {
                    if (Constants.ACCESS_TOKEN.equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }
        return token;
    }

    private List<Annotation> obtainLoginAnnotation(HandlerMethod method) {
        List<Annotation> annotation = new ArrayList<>();
        for (Class<? extends Annotation> cs : ANNOTATION_TYPES) {
            Annotation an = method.getMethodAnnotation(cs);
            if (an != null) {
                annotation.add(an);
            }
        }
        if (annotation.size() > 0) {
            return annotation;
        }
        Class<?> obj = method.getBeanType();
        for (Class<? extends Annotation> cs : ANNOTATION_TYPES) {
            Annotation an = obj.getAnnotation(cs);
            if (an != null) {
                annotation.add(an);
            }
        }
        return annotation;
    }


    private void SetResponseHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,PUT");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, access_token, visituserid");
    }


}
