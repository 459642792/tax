package com.blueteam.wineshop.controller;

import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/upload")
public class ImageUploadController extends HttpServlet {
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public BaseResult Upload(HttpServletResponse response) throws Exception {

        return ApiResult.success();
    }
}
