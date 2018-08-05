package com.blueteam.wineshop.base.web.component;

import com.blueteam.base.util.StringUtil;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by  NastyNas on 17/8/11.
 * 文件上传解析器
 */
public class CommonsMultipartResolverByUE extends CommonsMultipartResolver {


    @Override
    public boolean isMultipart(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String action = request.getParameter("action");
        if (StringUtil.IsNullOrEmpty(action))
            return super.isMultipart(request);

        if (uri.indexOf("jsp/ueditor") != -1 && action.indexOf("upload") != -1) {
            return false;
        }

        return super.isMultipart(request);
    }


}
