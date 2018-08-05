package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@AdminApiLogin
@Controller
@RequestMapping("/pinpaiMain")
public class PinpaiMainController extends BaseController {
    @Autowired
    PinPaiInfoService pinpaiInfoService;

    //品牌初始化页面
    @RequestMapping(value = "/Main", method = RequestMethod.GET)
    public ModelAndView Index() throws Exception {
        ModelAndView modelAndView = new ModelAndView("pinpaiManage/pinpaiList");
        return modelAndView;
    }

    //品牌初始化页面
    @RequestMapping(value = "/pageMain", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult Index(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                            @RequestParam(value = "Name", required = false) String Name, HttpServletResponse response) throws Exception {
        List<PinPaiInfo> pinpaiList = pinpaiInfoService.adminPinpaiList(pageSize, pageIndex, Name);
        List<PinPaiInfo> ListCount = pinpaiInfoService.adminPinpaiCount(Name);
        return ApiResult.success(pinpaiList, ListCount.size());
    }

    //新增或者修改界面跳转
    @RequestMapping(value = "/AddOrEdit", method = RequestMethod.GET)
    public ModelAndView AddOrEdit(@RequestParam(value = "Id") Integer Id, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView;
        if (Id != null && Id > 0) {
            modelAndView = new ModelAndView("pinpaiManage/pinpaiEdit");
            modelAndView.addObject("Id", Id);

        } else {
            modelAndView = new ModelAndView("pinpaiManage/pinpaiAdd");
        }
        return modelAndView;
    }

    //品牌信息修改显示查询
    @RequestMapping(value = "/pinpaiDetail", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult pinpaiDetail(@RequestParam("Id") Integer Id) {
        PinPaiInfo objInfo = pinpaiInfoService.PinPaiName(Id);
        if (null == objInfo) {
            return ApiResult.error("没有该品牌信息");
        }
        return ApiResult.success(objInfo);
    }

    //新增或者修改操作方法
    @RequestMapping(value = "/AddorEditPinpai", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult Index(PinPaiInfo obj, HttpServletResponse response) throws Exception {
        if (obj.getId() > 0) {
            PinPaiInfo objInfo = pinpaiInfoService.PinPaiName(obj.getId());
            if (null == objInfo) {
                return ApiResult.error("没有该品牌信息");
            }
            objInfo.setName(obj.getName());
            pinpaiInfoService.updatepinpai(objInfo);
        } else {
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String date = dateFormat.format(now);
            PinPaiInfo objInfo = new PinPaiInfo();
            objInfo.setName(obj.getName());
            objInfo.setDesc("");
            objInfo.setStatus(0);
            objInfo.setCreateDate(date);
            objInfo.setUpdateDate(date);
            objInfo.setCreateBy("");
            objInfo.setUpdateBy("");
            objInfo.setImage("");
            objInfo.setSortNumber(0);
            pinpaiInfoService.insertpinpai(objInfo);
        }
        return ApiResult.success();
    }

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
}
