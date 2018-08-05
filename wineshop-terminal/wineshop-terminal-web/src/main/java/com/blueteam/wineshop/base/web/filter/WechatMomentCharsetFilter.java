package com.blueteam.wineshop.base.web.filter;


import com.blueteam.base.util.json.JSONObject;
import com.blueteam.entity.dto.BaseResult;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by  NastyNas on 17/8/10.
 * 微信小程序字符过滤器
 */
public class WechatMomentCharsetFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //设置response头信息
        SetResponseHeaders((HttpServletResponse) response);
        //处理跨域请求
        HttpServletRequest req = (HttpServletRequest) request;
        if ("options".equalsIgnoreCase(req.getMethod())) {
            BaseResult result = BaseResult.success();
            JSONObject json = new JSONObject(result);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(json.toString());
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    private void SetResponseHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, access_token");
    }
}
