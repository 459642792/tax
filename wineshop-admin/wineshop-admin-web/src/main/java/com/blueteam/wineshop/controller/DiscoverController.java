package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Enums;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.City;
import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.*;
import com.blueteam.wineshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/discover")
public class DiscoverController extends BaseController {

    @Autowired
    DiscoverService discoverService;
    @Autowired
    ImageInfoService imageInfoService;
    @Autowired
    UserService userInfoService;
    @Autowired
    ContinuationService continuationService;
    @Autowired
    VendorInfoService vendorInfoService;
    @Autowired
    CityInfoService cityInfoService;
    @Autowired
    CarriersService service;
    @Autowired
    ReVendorService reVendorService;

    /**
     * 初始化页面权限控制
     *
     * @return
     */
    @RequestMapping(value = "/discoverMain", method = RequestMethod.GET)
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public ModelAndView Index() {
        ModelAndView modelAndView;
        modelAndView = new ModelAndView("discoverManage/discoverList");
        if (super.isUserType(super.getIdentify(), Enums.UserType.Admin)) {
            modelAndView.addObject("Power", "admin");
        } else {
            modelAndView.addObject("Power", "operators");
        }
        return modelAndView;
    }

    /**
     * 初始化查询列表
     *
     * @param pageSize
     * @param pageIndex
     * @param Type
     * @param IsUser
     * @param Status
     * @param Title
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/discoverList", method = RequestMethod.GET)
    @ResponseBody
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public BaseResult Index(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                            @RequestParam(value = "Type", required = false) String Type,
                            @RequestParam(value = "IsUser", required = false) String IsUser,
                            @RequestParam(value = "Status", required = false) Integer Status,
                            @RequestParam(value = "Title", required = false) String Title, HttpServletResponse response) throws Exception {
        List<Discover> couponList;
        int totalCount = 0;
        if (super.isUserType(super.getIdentify(), Enums.UserType.Admin)) {
            couponList = discoverService.DiscoverList(Title, Type, IsUser, Status, pageSize, pageIndex);
            totalCount = discoverService.DiscoverCount(Title, Type, IsUser, Status);
        } else {
            couponList = discoverService.DiscoverCarrList(String.valueOf(this.getCurrentUserID()), Title, Type, IsUser, Status, pageSize, pageIndex);
            totalCount = discoverService.DiscoverCarrCount(String.valueOf(this.getCurrentUserID()), Title, Type, IsUser, Status);
        }
        for (int i = 0; i < couponList.size(); i++) {
            CarriersInfo objInfo = service.selectForUser(Integer.parseInt(couponList.get(i).getIsUser()));
            if (null == objInfo) {
                UserInfo user = userInfoService.selectByPrimaryKey(Integer.parseInt(couponList.get(i).getIsUser()));
                if (null != user) {
                    couponList.get(i).setIsUser(null == user ? "-" : user.getNickname());
                }
            } else {
                couponList.get(i).setIsUser(null == objInfo ? "-" : objInfo.getName());
            }
        }
        //int totalCount=discoverService.DiscoverCount(Title, Type, IsUser, Status);
        return ApiResult.success(couponList, totalCount);
    }

    //新增或者修改界面跳转
    @RequestMapping(value = "/AddOrEdit", method = RequestMethod.GET)
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public ModelAndView AddOrEdit(@RequestParam(value = "Id") Integer Id, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView;
        if (Id != null && Id > 0) {
            modelAndView = new ModelAndView("discoverManage/discoverEdit");
            modelAndView.addObject("Id", Id);

        } else {
            modelAndView = new ModelAndView("discoverManage/discoverAdd");
        }
        if (super.isUserType(super.getIdentify(), Enums.UserType.Admin)) {
            modelAndView.addObject("Power", "admin");
        } else {
            modelAndView.addObject("Power", "operators");
        }
        return modelAndView;
    }


    @RequestMapping(value = "/Adddiscover", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public BaseResult Adddiscover(@RequestParam(value = "Title", required = false) String Title,
                                  @RequestParam(value = "Label", required = false) String Label,
                                  @RequestParam(value = "Type", required = false) String Type,
                                  @RequestParam(value = "Detail", required = false) String Detail,
                                  @RequestParam(value = "Groom", required = false) String Groom,
                                  @RequestParam(value = "HandLine", required = false) String HandLine,
                                  @RequestParam(value = "LstFaceImage", required = false) List<String> LstFaceImage,
                                  @RequestParam(value = "LstVendor", required = false) List<String> LstVendor,
                                  @RequestParam(value = "LstCitys", required = false) List<String> LstCitys,
                                  HttpServletResponse response) throws Exception {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = dateFormat.format(now);
        //新增操作
        Discover objdiscovers = new Discover();
        objdiscovers.setTitle(Title);
        objdiscovers.setLabel(Label);
        objdiscovers.setType(Type);
        objdiscovers.setIsUser(String.valueOf(this.getCurrentUserID()));
        if (super.isUserType(super.getIdentify(), Enums.UserType.Admin)) {
            objdiscovers.setStatus(Discover.Audit_2);
            objdiscovers.setVendorType(Discover.ADMIN_TYPE);
        } else {
            objdiscovers.setStatus(Discover.Audit_1);
            objdiscovers.setVendorType(Discover.CARRIERS_TYPE);
        }
        objdiscovers.setVisits(1);
        objdiscovers.setDetail(Detail);
        objdiscovers.setHandLine(HandLine);
        objdiscovers.setIsShow("Y");
        objdiscovers.setCreateBy(getUserName());
        objdiscovers.setUpdateBy(getUserName());
        objdiscovers.setCreateDate(date);
        objdiscovers.setUpdateDate(date);
        int groomCount = discoverService.groomCount();
        //objdiscovers.setGroom(groomCount>2 ?"N":Groom);
        objdiscovers.setGroom(Groom);
        int id = discoverService.insertDiscover(objdiscovers);

        //新增门面图
        for (int i = 0; i < LstFaceImage.size(); i++) {
            String str = LstFaceImage.get(i).replace("[", "").replace("\"", "").replace("]", "");
            if (!str.equals("[]") && !str.equals("null") && !str.equals("")) {
                ImageInfo objImageInfo = new ImageInfo();
                objImageInfo.setImage(LstFaceImage.get(i).replace("[", "").replace("\"", "").replace("]", ""));
                objImageInfo.setUrl("");
                objImageInfo.setTitle("");
                objImageInfo.setStatus(1);
                objImageInfo.setDetail("");
                objImageInfo.setForeignKey(String.valueOf(this.getCurrentUserID()));
                objImageInfo.setType(Discover.FACE_IMAGE);
                objImageInfo.setSortNumber(0);
                objImageInfo.setCreateBy(getUserName());
                objImageInfo.setUpdateBy(getUserName());
                objImageInfo.setCreateDate(date);
                objImageInfo.setUpdateDate(date);
                objImageInfo.setExtendId(objdiscovers.getId());
                imageInfoService.insertImage(objImageInfo);
            }
        }

        //新增店铺信息
        for (int j = 0; j < LstVendor.size(); j++) {
            if (!LstVendor.get(j).equals("null") && !LstVendor.get(j).equals("[]")) {
                Continuation objconti = new Continuation();
                objconti.setForeignKey(objdiscovers.getId());
                objconti.setType(Continuation.vendor_link_type);
                objconti.setExpandText1(LstVendor.get(j).replace("[", "").replace("\"", "").replace("]", ""));
                objconti.setExpandText2("");
                objconti.setCreateBy(getUserName());
                objconti.setUpdateBy(getUserName());
                objconti.setCreateDate(date);
                objconti.setUpdateDate(date);
                continuationService.insertContinuation(objconti);
            }
        }

        if (super.isUserType(super.getIdentify(), Enums.UserType.Admin)) {
            //新增城市信息
            for (int k = 0; k < LstCitys.size(); k++) {
                Continuation objCitys = new Continuation();
                objCitys.setStatus("Y");
                objCitys.setForeignKey(objdiscovers.getId());
                objCitys.setExpandText1(LstCitys.get(k).split("/")[0].replace("[", "").replace("\"", "").replace("]", ""));
                objCitys.setExpandText2(LstCitys.get(k).split("/")[1].replace("[", "").replace("\"", "").replace("]", ""));
                objCitys.setCreateBy(getUserName());
                objCitys.setUpdateBy(getUserName());
                objCitys.setCreateDate(date);
                objCitys.setUpdateDate(date);
                objCitys.setType(Discover.DISCOVER_LINK_CITYS);
                continuationService.insertContinuation(objCitys);

                /**
                 * 新增头条关联城市
                 */
                Continuation objCitys1 = new Continuation();
                objCitys1.setStatus("Y");
                objCitys1.setForeignKey(objdiscovers.getId());
                objCitys1.setExpandText1(LstCitys.get(k).split("/")[0].replace("[", "").replace("\"", "").replace("]", ""));
                objCitys1.setExpandText2("");
                objCitys1.setCreateBy(getUserName());
                objCitys1.setUpdateBy(getUserName());
                objCitys1.setCreateDate(date);
                objCitys1.setUpdateDate(date);
                objCitys1.setType(Continuation.Continuation_type);
                continuationService.insertContinuation(objCitys1);
            }
        } else {
            CarriersInfo objvendor = service.selectForUser(this.getCurrentUserID());
            Continuation objCitys = new Continuation();
            objCitys.setStatus("Y");
            objCitys.setForeignKey(objdiscovers.getId());
            objCitys.setExpandText1(objvendor == null ? "" : objvendor.getCitycode());
            objCitys.setExpandText2("");
            objCitys.setCreateBy(getUserName());
            objCitys.setUpdateBy(getUserName());
            objCitys.setCreateDate(date);
            objCitys.setUpdateDate(date);
            objCitys.setType(Discover.DISCOVER_LINK_CITYS);
            continuationService.insertContinuation(objCitys);

            /**
             * 新增头条关联城市
             */
            Continuation objCitys2 = new Continuation();
            objCitys2.setStatus("Y");
            objCitys2.setForeignKey(objdiscovers.getId());
            objCitys2.setExpandText1(objvendor == null ? "" : objvendor.getCitycode());
            objCitys2.setExpandText2("");
            objCitys2.setCreateBy(getUserName());
            objCitys2.setUpdateBy(getUserName());
            objCitys2.setCreateDate(date);
            objCitys2.setUpdateDate(date);
            objCitys2.setType(Continuation.Continuation_type);
            continuationService.insertContinuation(objCitys2);
        }

        BaseResult result = ApiResult.success();
        result.setReturnId(String.valueOf(id));
        return result;
    }

    //发现信息列表
    @RequestMapping(value = "/GetDiscover", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult GetDiscover(@RequestParam("Id") Integer Id) {
        Discover objdiscover = discoverService.GetDiscover(Id);
        if (null == objdiscover) {
            return ApiResult.error("没有该发现信息");
        }
        //查询门脸图
        List<ImageInfo> lstCont = imageInfoService.ImageInfoList(Discover.FACE_IMAGE.toString(), Id);
        List<String> lstStr = new ArrayList<String>();
        for (int a = 0; a < lstCont.size(); a++) {
            lstStr.add(lstCont.get(a).getImage());
        }
        objdiscover.setLstFaceImage(lstStr);
        //查询商家链接
        List<Continuation> lstLinkvendor = continuationService.ContinuationList(Id, Continuation.vendor_link_type.toString());
        List<String> lstvend = new ArrayList<String>();
        for (int b = 0; b < lstLinkvendor.size(); b++) {
            lstvend.add(lstLinkvendor.get(b).getExpandText1());
        }
        objdiscover.setLstVendor(lstvend);
        //查询城市
        List<Continuation> lstcitys = continuationService.ContinuationList(Id, Discover.DISCOVER_LINK_CITYS.toString());
        List<String> lstcity = new ArrayList<String>();
        for (int i = 0; i < lstcitys.size(); i++) {
            lstcity.add(lstcitys.get(i).ExpandText2 + "/" + lstcitys.get(i).ExpandText1);
        }
        objdiscover.setLstCitys(lstcity);
        return ApiResult.success(objdiscover);
    }

    @RequestMapping(value = "/Editdiscover", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public BaseResult Editdiscover(@RequestParam(value = "Id", required = false) int Id, @RequestParam(value = "Title", required = false) String Title,
                                   @RequestParam(value = "Label", required = false) String Label,
                                   @RequestParam(value = "Type", required = false) String Type,
                                   @RequestParam(value = "Detail", required = false) String Detail,
                                   @RequestParam(value = "Groom", required = false) String Groom,
                                   @RequestParam(value = "HandLine", required = false) String HandLine,
                                   @RequestParam(value = "LstFaceImage", required = false) List<String> LstFaceImage,
                                   @RequestParam(value = "LstVendor", required = false) List<String> LstVendor,
                                   @RequestParam(value = "LstCitys", required = false) List<String> LstCitys, HttpServletResponse response) throws Exception {
        Discover objdiscover = discoverService.GetDiscover(Id);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = dateFormat.format(now);
        //新增操作
        Discover objdiscovers = new Discover();
        objdiscovers.setTitle(Title);
        objdiscovers.setLabel(Label);
        objdiscovers.setType(Type);
        if (super.isUserType(super.getIdentify(), Enums.UserType.Admin)) {
            objdiscovers.setIsUser(String.valueOf(this.getCurrentUserID()));
            objdiscovers.setStatus(Discover.Audit_2);
        } else {
            objdiscovers.setIsUser(objdiscover.getIsUser());
            objdiscovers.setStatus(Discover.Audit_1);
        }
        objdiscovers.setHandLine(HandLine);
        objdiscovers.setDetail(Detail);
        objdiscovers.setId(Id);
        objdiscovers.setCreateBy(getUserName());
        objdiscovers.setUpdateBy(getUserName());
        objdiscovers.setCreateDate(date);
        objdiscovers.setUpdateDate(date);
        int groomCount = discoverService.groomCount();
        //objdiscovers.setGroom(groomCount>2 ?"N":Groom);
        objdiscovers.setGroom(Groom);
        discoverService.updateDiscover(objdiscovers);

        //新增门面图
        imageInfoService.deleteByPrimaryKey(objdiscovers.getId(), Discover.FACE_IMAGE.toString());
        for (int i = 0; i < LstFaceImage.size(); i++) {

            String str = LstFaceImage.get(i).replace("[", "").replace("\"", "").replace("]", "");
            if (!str.equals("[]") && !str.equals("null") && !str.equals("")) {
                ImageInfo objImageInfo = new ImageInfo();
                objImageInfo.setImage(LstFaceImage.get(i).replace("[", "").replace("\"", "").replace("]", ""));
                objImageInfo.setUrl("");
                objImageInfo.setTitle("");
                objImageInfo.setStatus(1);
                objImageInfo.setDetail("");
                if (super.isUserType(super.getIdentify(), Enums.UserType.Admin)) {
                    objImageInfo.setForeignKey(String.valueOf(this.getCurrentUserID()));
                } else {
                    objImageInfo.setForeignKey(objdiscover.getIsUser());
                }
                objImageInfo.setType(Discover.FACE_IMAGE);
                objImageInfo.setSortNumber(0);
                objImageInfo.setCreateBy(getUserName());
                objImageInfo.setUpdateBy(getUserName());
                objImageInfo.setCreateDate(date);
                objImageInfo.setUpdateDate(date);
                objImageInfo.setExtendId(objdiscovers.getId());
                imageInfoService.insertImage(objImageInfo);
            }
        }

        //新增店铺信息
        continuationService.deleteByPrimaryKey(Id, Continuation.vendor_link_type);
        for (int j = 0; j < LstVendor.size(); j++) {
            if (!LstVendor.get(j).equals("null") && !LstVendor.get(j).equals("[]")) {
                Continuation objconti = new Continuation();
                objconti.setType(Continuation.vendor_link_type);
                objconti.setExpandText1(LstVendor.get(j).replace("[", "").replace("\"", "").replace("]", ""));
                objconti.setExpandText2("");
                objconti.setForeignKey(objdiscovers.getId());
                objconti.setCreateBy(getUserName());
                objconti.setUpdateBy(getUserName());
                objconti.setCreateDate(date);
                objconti.setUpdateDate(date);
                continuationService.insertContinuation(objconti);
            }
        }

        continuationService.deleteByPrimaryKey(Id, Discover.DISCOVER_LINK_CITYS);
        continuationService.deleteByPrimaryKey(Id, Continuation.Continuation_type);
        if (super.isUserType(super.getIdentify(), Enums.UserType.Admin)) {
            //新增城市信息
            if (objdiscover.getVendorType() == Discover.CARRIERS_TYPE) {
                CarriersInfo objvendor = service.selectForUser(Integer.parseInt(objdiscover.getIsUser()));
                Continuation objCitys = new Continuation();
                City objCity = cityInfoService.selectCityByCode(objvendor.getCitycode().toString());
                objCitys.setStatus("Y");
                objCitys.setForeignKey(objdiscovers.getId());
                objCitys.setExpandText1(objvendor == null ? "" : objCity.getCityCode());
                objCitys.setExpandText2(objvendor == null ? "" : objCity.getCityName());
                objCitys.setCreateBy(getUserName());
                objCitys.setUpdateBy(getUserName());
                objCitys.setCreateDate(date);
                objCitys.setUpdateDate(date);
                objCitys.setType(Discover.DISCOVER_LINK_CITYS);
                continuationService.insertContinuation(objCitys);

                //**
                // 新增头条城市
                //
                Continuation objCitys2 = new Continuation();
                objCitys2.setStatus("Y");
                objCitys2.setForeignKey(objdiscovers.getId());
                objCitys2.setExpandText1(objvendor == null ? "" : objvendor.getCitycode());
                objCitys2.setExpandText2("");
                objCitys2.setCreateBy(getUserName());
                objCitys2.setUpdateBy(getUserName());
                objCitys2.setCreateDate(date);
                objCitys2.setUpdateDate(date);
                objCitys2.setType(Continuation.Continuation_type);
                continuationService.insertContinuation(objCitys2);
            } else {
                for (int k = 0; k < LstCitys.size(); k++) {
                    Continuation objCitys = new Continuation();
                    objCitys.setStatus("Y");
                    objCitys.setForeignKey(objdiscovers.getId());
                    objCitys.setExpandText1(LstCitys.get(k).split("/")[0].replace("[", "").replace("\"", "").replace("]", ""));
                    objCitys.setExpandText2(LstCitys.get(k).split("/")[1].replace("[", "").replace("\"", "").replace("]", ""));
                    objCitys.setCreateBy(getUserName());
                    objCitys.setUpdateBy(getUserName());
                    objCitys.setCreateDate(date);
                    objCitys.setUpdateDate(date);
                    objCitys.setType(Discover.DISCOVER_LINK_CITYS);
                    continuationService.insertContinuation(objCitys);

                    /**
                     * 新增头条城市
                     */
                    Continuation objCitys1 = new Continuation();
                    objCitys1.setStatus("Y");
                    objCitys1.setForeignKey(objdiscovers.getId());
                    objCitys1.setExpandText1(LstCitys.get(k).split("/")[0].replace("[", "").replace("\"", "").replace("]", ""));
                    objCitys1.setExpandText2("");
                    objCitys1.setCreateBy(getUserName());
                    objCitys1.setUpdateBy(getUserName());
                    objCitys1.setCreateDate(date);
                    objCitys1.setUpdateDate(date);
                    objCitys1.setType(Continuation.Continuation_type);
                    continuationService.insertContinuation(objCitys1);
                }
            }
        } else {
            CarriersInfo objvendor = service.selectForUser(this.getCurrentUserID());
            Continuation objCitys = new Continuation();
            objCitys.setStatus("Y");
            objCitys.setForeignKey(objdiscovers.getId());
            objCitys.setExpandText1(objvendor.getCitycode().toString());
            objCitys.setExpandText2("");
            objCitys.setCreateBy(getUserName());
            objCitys.setUpdateBy(getUserName());
            objCitys.setCreateDate(date);
            objCitys.setUpdateDate(date);
            objCitys.setType(Discover.DISCOVER_LINK_CITYS);
            continuationService.insertContinuation(objCitys);

            /**
             * 新增头条城市
             */
            Continuation objCitys2 = new Continuation();
            objCitys2.setStatus("Y");
            objCitys2.setForeignKey(objdiscovers.getId());
            objCitys2.setExpandText1(objvendor == null ? "" : objvendor.getCitycode());
            objCitys2.setExpandText2("");
            objCitys2.setCreateBy(getUserName());
            objCitys2.setUpdateBy(getUserName());
            objCitys2.setCreateDate(date);
            objCitys2.setUpdateDate(date);
            objCitys2.setType(Continuation.Continuation_type);
            continuationService.insertContinuation(objCitys2);
        }
        return ApiResult.success();
    }

    /**
     * 审核发现
     *
     * @param objcover
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/AduitStatus", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public BaseResult AduitStatus(Discover objcover,
                                  HttpServletResponse response) throws Exception {
        if (objcover.getId() <= 0) {
            return ApiResult.error("参数错误");
        }
        if (objcover.getStatus() == 2) {
            discoverService.updateDiscoverShow(objcover.getId(), "Y");
        }

        discoverService.updateDiscoverStatus(objcover.getId(), objcover.getStatus(), objcover.getReason());

        ApiResult objResult = new ApiResult();
        objResult.setReturnId(String.valueOf(objcover.getId()));

        //objResult.success(true);
        objResult.setSuccess(true);
        objResult.setMessage("审核成功");
        objResult.setStatus("200");
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
        objRe.setUserId(this.getCurrentUserID());
        objRe.setVendorId(0);
        objRe.setCarriersId(0);
        return ApiResult.success(objRe);
    }

    /**
     * 浏览数
     *
     * @param objcover
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/DiscoverVisitor", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public BaseResult DiscoverVisitor(Discover objcover, HttpServletResponse response) throws Exception {
        if (objcover.getId() <= 0) {
            return ApiResult.error("参数错误");
        }
        discoverService.updateDiscoverShow(objcover.getId(), objcover.getIsShow());
        return ApiResult.success();
    }

    /**
     * 发现列表
     *
     * @param Id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/DisvendorList", method = RequestMethod.GET)
    @ResponseBody
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public BaseResult DisvendorList(@RequestParam(value = "Id", required = false) int Id, HttpServletResponse response) throws Exception {
        List<VendorInfo> lstVendorInfo = null;
        if (super.isUserType(super.getIdentify(), Enums.UserType.Admin)) {
            if (Id > 0) {
                Discover objdiscover = discoverService.GetDiscover(Id);
                if (objdiscover.getVendorType().equals(Discover.CARRIERS_TYPE)) {
                    CarriersInfo objInfo2 = service.selectForUser(Integer.parseInt(objdiscover.getIsUser()));
                    if (objInfo2 == null) {
                        return ApiResult.error("该商家信息不存在");
                    }
                    lstVendorInfo = vendorInfoService.DiscoverVendorList(objInfo2.getCitycode());
                } else {
                    lstVendorInfo = vendorInfoService.DiscoverVendorList(null);
                }
            } else {
                lstVendorInfo = vendorInfoService.DiscoverVendorList(null);
            }
        } else {
            CarriersInfo objInfo = service.selectForUser(this.getCurrentUserID());
            if (objInfo == null) {
                return ApiResult.error("该商家信息不存在");
            }
            lstVendorInfo = vendorInfoService.DiscoverVendorList(objInfo.getCitycode());
        }
        return ApiResult.success(lstVendorInfo);
    }

    /**
     * 推荐活动个数
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/GroomCount", method = RequestMethod.GET)
    @ResponseBody
    @ApiLogin(userType = Enums.UserType.Admin | Enums.UserType.Carriers, message = "必须是管理员或这运营商")
    public BaseResult GroomCount(HttpServletResponse response) throws Exception {
        int groomCount = discoverService.groomCount();
        return ApiResult.success(null, groomCount);
    }

    /**
     * 修改活动
     *
     * @param LstId
     * @param response
     * @return
     */
    @RequestMapping(value = "/UpdateGroom", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult UpdateGroom(@RequestParam(value = "LstId", required = false) List<String> LstId, HttpServletResponse response) {
        discoverService.updateAllGroom();
        for (int i = 0; i < LstId.size(); i++) {
            if (!LstId.get(i).equals("[]") && !LstId.get(i).equals("null")) {
                int Id = Integer.parseInt(LstId.get(i).replace("[", "").replace("\"", "").replace("]", ""));
                discoverService.updateGroom(Id);
            }
        }
        return ApiResult.success();
    }

    /**
     * 活动列表
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/LstGroom", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult LstGroom(HttpServletResponse response) {
        List<Discover> lstDis = discoverService.lstGroom();
        return ApiResult.success(lstDis, lstDis.size());
    }

    /**
     * 推荐信息商家
     *
     * @param cityCode
     * @param response
     * @return
     */
    @RequestMapping(value = "/ReVendorList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult ReVendorList(@RequestParam(value = "cityCode", required = false) String cityCode, HttpServletResponse response) {
        List<VendorInfo> lstvendor = vendorInfoService.DiscoverVendorList(cityCode);
        return ApiResult.success(lstvendor, lstvendor.size());
    }
}
