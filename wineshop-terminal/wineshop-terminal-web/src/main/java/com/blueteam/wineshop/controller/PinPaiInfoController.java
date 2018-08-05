package com.blueteam.wineshop.controller;

import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.PinPaiInfo;
import com.blueteam.wineshop.service.PinPaiInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Marx
 * <p>
 * PinPaiInfoController.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
@Controller
@RequestMapping("/pinpai")
public class PinPaiInfoController extends BaseController {
    @Autowired
    PinPaiInfoService pinpaiInfoService;

    /**
     * @param Status
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pinpaiList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult pinpaiList(@RequestParam("Status") String Status,
                                 HttpServletResponse response) throws Exception {

        List<PinPaiInfo> objpinpaiInfo = pinpaiInfoService.PinPaiInfoList(Status);
        return ApiResult.success(objpinpaiInfo);
    }

    /**
     * 根据状态查询品牌
     *
     * @param Status
     * @param response
     * @return
     * @throws Exception
     * @author xiaojiang 2017年3月17日
     * @version 1.0
     * @since 1.0 2017年3月17日
     */
    @RequestMapping(value = "/listPinpai", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listPinpai(@RequestParam("Status") String Status,
                                 HttpServletResponse response) throws Exception {

        List<PinPaiInfo> objpinpaiInfo = pinpaiInfoService.listPinPaiInfo(Status);
        return ApiResult.success(objpinpaiInfo);
    }
}
