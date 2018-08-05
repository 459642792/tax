package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Enums;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.CarriersInfo;
import com.blueteam.entity.po.ReVendor;
import com.blueteam.entity.po.UserInfo;
import com.blueteam.wineshop.service.CarriersService;
import com.blueteam.wineshop.service.ReVendorService;
import com.blueteam.wineshop.service.UserService;
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

/**
 * Created by libra on 2017/4/12.
 */
@AdminApiLogin
@Controller
@RequestMapping("/recommVendor")
public class ReVendorController extends BaseController {

    @Autowired
    ReVendorService service;
    @Autowired
    CarriersService carriersinfoService;
    @Autowired
    UserService userService;

    /**
     * 初始化页面权限控制
     *
     * @return
     */
    @RequestMapping(value = "/VendorMain", method = RequestMethod.GET)
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public ModelAndView Index() {
        ModelAndView modelAndView = new ModelAndView("/AdvManager/ReVendorList");
        return modelAndView;
    }

    @RequestMapping(value = "/reVendorList", method = RequestMethod.GET)
    @ResponseBody
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public BaseResult reVendorList(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                                   @RequestParam(value = "VendorName", required = false) String VendorName,
                                   @RequestParam(value = "TradingArea", required = false) String TradingArea, HttpServletResponse response) throws Exception {
        List<ReVendor> lstRevendor = service.listReVendor(VendorName, TradingArea, pageSize, pageIndex);
        int revendorCount = service.ReVendorCount(VendorName, TradingArea);
        return ApiResult.success(lstRevendor, revendorCount);
    }

    @RequestMapping(value = "/reVendorAdd", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public BaseResult reVendorAdd(@RequestParam(value = "AreaAddr", required = false) String AreaAddr, @RequestParam(value = "VendorName", required = false) String VendorName, @RequestParam(value = "TradingArea", required = false) String TradingArea, @RequestParam(value = "vendorId", required = false) Integer vendorId, HttpServletResponse response) throws Exception {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        UserInfo user = userService.selectByPrimaryKey(this.getCurrentUserID());
        String date = dateFormat.format(now);
        ReVendor objvendor = new ReVendor();
        objvendor.setAreaAddr(AreaAddr);
        objvendor.setClickCount(1);
        objvendor.setCreateBy(user.getNickname());
        objvendor.setCreateDate(date);
        objvendor.setEabledFlag("Y");
        objvendor.setUpdateBy(user.getNickname());
        objvendor.setUpdateDate(date);
        objvendor.setTradingArea(TradingArea);
        objvendor.setVendorId(vendorId);
        objvendor.setVendorName(VendorName);
        int maxid = service.MaxOrderField(AreaAddr);
        objvendor.setOrderField(maxid + 1);
        CarriersInfo objInfo = carriersinfoService.getCarriersByCode(AreaAddr);
        objvendor.setCarriersName(objInfo == null ? "" : objInfo.getName());
        service.insert(objvendor);
        List<ReVendor> lstvendors = service.listReVendor2(AreaAddr);
        ApiResult objResult = new ApiResult();
        objResult.setReturnId(String.valueOf(objvendor.getId()));
        objResult.success(objvendor.getId(), lstvendors.size());
        objResult.setStatus("200");
        objResult.setSuccess(true);
        objResult.setMessage("推荐成功");
        return objResult;
    }

    /**
     * 推荐
     */
    @RequestMapping(value = "/getMessageRecipient", method = RequestMethod.GET)
    @ResponseBody
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public BaseResult getMessageRecipient(@RequestParam(value = "Id") Integer Id, HttpServletResponse response) throws Exception {
        if (null == Id) return ApiResult.error("参数参数信息不正确");
        MessageRecipient objRe = new MessageRecipient();
        ReVendor objvendor = service.revendorDetail(Id);
        if (null == objvendor) return ApiResult.error("不存在该推荐店铺");
        objRe.setVendorId(objvendor.getVendorId());
        CarriersInfo objInfo = carriersinfoService.getCarriersByCode(objvendor.getAreaAddr());
        objRe.setCarriersId(null != objInfo ? objInfo.getId() : 0);
        objRe.setUserId(null != objInfo ? objInfo.getUserid() : 0);
        return ApiResult.success(objRe);
    }

    /**
     * 删除推荐管理信息
     *
     * @param Id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reVendorDelete", method = RequestMethod.GET)
    @ResponseBody
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public BaseResult reVendorDelete(@RequestParam(value = "Id", required = false) Integer Id, HttpServletResponse response) throws Exception {
        if (null == Id || Id <= 0) {
            return ApiResult.error("传入的参数不正确");
        }
        service.DeleteReVendor(Id);
        return ApiResult.success();
    }

    /**
     * 推荐商家排序接口
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reVendorSort", method = RequestMethod.GET)
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public @ResponseBody
    BaseResult reVendorSort(@RequestParam(value = "LstSortId", required = false) List<String> LstSortId, HttpServletResponse response) throws Exception {
        for (int i = 0; i < LstSortId.size(); i++) {
            int Id = Integer.parseInt(LstSortId.get(i).replace("[", "").replace("\"", "").replace("]", ""));
            ReVendor objvendor = service.revendorDetail(Id);
            objvendor.setOrderField(i + 1);
            objvendor.setId(Id);
            service.update(objvendor);
        }
        return ApiResult.success();
    }


    /**
     * 推荐商家列表(按区域来查询的)
     *
     * @param Id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cityVendorList", method = RequestMethod.GET)
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public @ResponseBody
    BaseResult cityVendorList(@RequestParam(value = "Id", required = false) Integer Id, HttpServletResponse response) throws Exception {
        if (null == Id || Id <= 0) {
            return ApiResult.error("传入的参数不正确");
        }
        ReVendor objvendor = service.revendorDetail(Id);
        if (null == objvendor) return ApiResult.error("不存在该商家信息");
        List<ReVendor> lstvendor = service.listReVendor2(objvendor.getAreaAddr());
        return ApiResult.success(lstvendor, lstvendor.size());
    }
}
