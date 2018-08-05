package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.CarriersApiLogin;
import com.blueteam.base.constant.Constants;
import com.blueteam.base.constant.EnabledOrDisabled;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.StringUtil;
import com.blueteam.base.util.VerificationUtil;
import com.blueteam.entity.dto.*;
import com.blueteam.entity.po.*;
import com.blueteam.wineshop.service.*;
import org.apache.commons.collections.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 供应商控制器
 *
 * @author libra
 */
@RequestMapping("/carriers")
@Controller
@CarriersApiLogin
public class CarriersController extends BaseController {

    /**
     * 日志
     */
    private static Logger logger = LogManager.getLogger(CarriersController.class);

    /**
     * 运营商服务
     */
    @Autowired
    private CarriersService service;

    /**
     * 商家service
     */
    @Autowired
    private VendorInfoService vendorService;

    /**
     * 结算服务
     */
    @Autowired
    private SmallRoutineCommentService smallRoutineCommentService;

    /**
     * 点赞服务
     */
    @Autowired
    private UpvoteRecordService upvoteRecordService;

    /**
     * 发现服务
     */
    @Autowired
    private DiscoverService discoverService;

    /**
     * 图片服务
     */
    @Autowired
    private ImageInfoService imageInfoService;

    /**
     * 拓展服务
     */
    @Autowired
    private ContinuationService continuationService;

    /**
     * 商家服务
     */
    @Autowired
    private VendorInfoService vendorInfoService;

    /**
     * 评价服务
     */
    @Autowired
    private ScoreInfoService scoreInfoService;


    /**
     * 获取商家数量
     *
     * @param type 类型
     * @return
     */
    @RequestMapping("/getVendorNubmers")
    @ResponseBody
    public BaseResult getVendorNubmers(@RequestParam Integer type) {
        VendorNumbers numbers = new VendorNumbers();
        return ApiResult.success(numbers);
    }

    /**
     * 入驻
     *
     * @param session session
     * @param phone   电话号码
     * @param code    验证码
     * @param lon     经度
     * @param lat     纬度
     * @return
     */
    @RequestMapping(value = "/enter", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult enter(HttpSession session, String phone, String code, String name, String image,
                            String contactPerson, String cityCode, String addr, Double lon, Double lat) {

        if (phone == null || code == null || lon == null || lat == null)
            return BaseResult.error("参数错误");

        phone = phone.trim();
        code = code.trim();

        VendorInfo param = new VendorInfo();

        param.setName(name);
        param.setAddr(addr);
        param.setImage(image);
        param.setContactPerson(contactPerson);
        param.setCityCode(cityCode);
        param.setLongitude(lon.toString());
        param.setLatitude(lat.toString());

        // 校验验证码
        if (!VerificationUtil.VerificationCode(session, phone, code))
            return BaseResult.error("错误的验证码");

        if (param.getName() == null || param.getImage() == null || param.getContactPerson() == null
                || param.getCityCode() == null || param.getAddr() == null)
            return BaseResult.error("参数错误");

        try {
            UserIdentify identify = getIdentify();
            if (!super.isUserType(identify, Enums.UserType.Carriers))
                return BaseResult.error("您不是运营商");

            BaseResult result = service.enter(phone, param, identify.getExtendId());
            if (result.isSuccess() && result instanceof ApiResult)
                result.setReturnId(((ApiResult) result).getData().toString());
            return result;
        } catch (Exception e) {
            logger.error(e);
        }
        return BaseResult.error("入驻失败！");
    }

    /**
     * 获取管理区域的所有商家
     *
     * @return
     */
    @RequestMapping(value = "/vendorList")
    @ResponseBody
    public BaseResult vendorList(String authenticate, Integer pageIndex, Integer pageSize) {
        pageIndex = pageIndex == null || pageIndex <= 0 ? 1 : pageIndex;
        pageSize = pageSize == null || pageSize <= 0 ? 20 : pageSize;

        if (authenticate != null) {
            authenticate = authenticate.toUpperCase();
            if (!authenticate.equals(EnabledOrDisabled.DISABLED) && !authenticate.equals(EnabledOrDisabled.ENABLED))
                return BaseResult.error("参数错误");
        }

        int userId = getCurrentUserID();
        PageResult<List<VendorInfo>> list = vendorService.carriersVendors(userId, null, authenticate, pageIndex, pageSize);
        if (list.getList() == null)
            list.setList(ListUtils.EMPTY_LIST);
        return ApiResult.success(list);
    }

    /**
     * 认证资料提交
     *
     * @return
     */
    @RequestMapping(value = "/postAuthenticateData", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postAuthenticateData(@ModelAttribute VendorInfo param) {
        if (param == null || StringUtil.IsNullOrEmpty(param.getLegalPerson())
                || StringUtil.IsNullOrEmpty(param.getLegalPersonIdCard()))
            return BaseResult.error("参数错误,法人相关信息必须传入!");

        if (StringUtil.IsNullOrEmpty(param.getBusinessLicenseImg())
                || StringUtil.IsNullOrEmpty(param.getLicenseFileImg())
                || StringUtil.IsNullOrEmpty(param.getIdInHandImage()))
            return BaseResult.error("参数错误,证件图片必须传入");

        if (param.getId() <= 0)
            return BaseResult.error("参数错误,不正确的认证商家");

        try {
            BaseResult result = service.authenticateVendor(getCurrentUserID(), param);
            result.setReturnId(param.getId().toString());
            return result;
        } catch (Exception e) {
            logger.error(e);
        }
        return BaseResult.error("提交认证资料失败");
    }

    /**
     * 审核认证
     *
     * @param vendorId     商家ID
     * @param targetStatus 审核目标状态
     * @param reason       通过/拒绝 理由
     * @return
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult authenticate(int vendorId, int targetStatus, String reason) {
        if (vendorId <= 0 || targetStatus <= 0)
            return BaseResult.error("参数错误");

        Enums.VendorStatus target = null;
        for (Enums.VendorStatus val : Enums.VendorStatus.values()) {
            if (val.getValue() == targetStatus) {
                target = val;
                break;
            }
        }

        if (target == null)
            return BaseResult.error("审核目标值错误");

        BaseResult result = service.authenticate(getCurrentUserID(), vendorId, target, reason);
        result.setReturnId(String.valueOf(vendorId));
        return result;
    }


    /**
     * 根据商家ID获取 运营商管辖的  商家详细信息
     *
     * @param vendorId 商家ID
     * @return
     */
    @RequestMapping(value = "/getVendor", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getVendor(int vendorId) {
        UserIdentify identify = getIdentify();
        if (!isUserType(identify, Enums.EnumUserType.Carriers.getValue()))
            return BaseResult.error("您不是运营商");
        VendorInfo info = vendorService.getCarriersVendor(identify.getExtendId(), vendorId);
        return ApiResult.success(info);
    }


    /**
     * 获取管理区域的所有商家
     *
     * @param authenticate 认证状态
     * @param pageIndex    页码
     * @param pageSize     页码尺寸
     * @param orderField   排序字段
     * @param orderBy      排序方式
     * @return
     */
    @RequestMapping(value = "/vendors")
    @ResponseBody
    public BaseResult vendors(String authenticate, Integer pageIndex, Integer pageSize, String orderField, String orderBy) {
        pageIndex = pageIndex == null || pageIndex <= 0 ? 1 : pageIndex;
        pageSize = pageSize == null || pageSize <= 0 ? 20 : pageSize;

        if (authenticate != null) {
            if (authenticate.isEmpty()) {
                authenticate = null;
            } else {
                authenticate = authenticate.toUpperCase();
                if (!authenticate.equals(EnabledOrDisabled.DISABLED) && !authenticate.equals(EnabledOrDisabled.ENABLED))
                    return BaseResult.error("参数错误");
            }
        }
        if (StringUtil.IsNullOrEmpty(orderField) || !Constants.VendorsOrderField.Contain(orderField))
            orderField = Constants.VendorsOrderField.ENTER_DATE;
        if (StringUtil.IsNullOrEmpty(orderBy) || !Constants.SQLOrder.Contain(orderBy))
            orderBy = "DESC";
        UserIdentify identify = getIdentify();
        if (!isUserType(identify, Enums.EnumUserType.Carriers.getValue()))
            return BaseResult.error("您不是运营商");
        PageResult<List<VendorInfo>> list = vendorService.carriersVendorList(identify.getExtendId(), orderField, orderBy, Enums.VendorStatus.CheckAccess, authenticate, pageIndex, pageSize);
        return ApiResult.success(list.getList(), list.getCount());
    }

    /**
     * 获取运营商统计和待审核的商家
     *
     * @return
     */
    @RequestMapping(value = "/carriersSatisticsAuthVendors")
    @ResponseBody
    public BaseResult vendors(Integer pageIndex, Integer pageSize) {
        pageIndex = pageIndex == null || pageIndex <= 0 ? 1 : pageIndex;
        pageSize = pageSize == null || pageSize <= 0 ? 20 : pageSize;
        UserIdentify identify = getIdentify();
        //获取统计信息
        CarriersStatistics statistics = service.getStatistics(identify.getExtendId());
        if (statistics != null) {
            if (statistics.getSalesMoney() == null)
                statistics.setSalesMoney(new BigDecimal(0));
        }
        PageResult<List<VendorInfo>> list = vendorService.carriersPreAuthVendors(identify.getUserId(), pageIndex, pageSize);
        StatisticsAndPreAuthResult result = new StatisticsAndPreAuthResult();
        result.setStatistics(statistics);
        result.setVendorList(list.getList());
        return ApiResult.success(result, list.getCount());
    }


    /**
     * 运营商发现列表
     *
     * @param pageSize
     * @param pageIndex
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/discoverList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult discover(@RequestParam("pageSize") int pageSize, @RequestParam("pageIndex") int pageIndex, HttpServletResponse response) throws Exception {
        UserIdentify identify = getIdentify();
        CarriersInfo info = service.selectForUser(identify.getUserId());
        if (null == info) {
            return ApiResult.error("没有查询到对应的运营商信息");
        }
        List<Discover> lstDiscover = discoverService.DisCoverHeandList2(String.valueOf(identify.getUserId()), info.getCitycode(), Continuation.Continuation_type, pageSize, pageIndex);
        for (int i = 0; i < lstDiscover.size(); i++) {
            List<String> lstStr = new ArrayList<String>();
            List<ImageInfo> lstImg = imageInfoService.ImageInfoList(Discover.FACE_IMAGE.toString(), lstDiscover.get(i).getId());
            for (int j = 0; j < lstImg.size(); j++) {
                lstStr.add(lstImg.get(j).getImage());
            }
            lstDiscover.get(i).setLstFaceImage(lstStr);
        }
        int totalCount = discoverService.DisCoverHeandCount2(String.valueOf(identify.getUserId()), info.getCitycode(), Continuation.Continuation_type);
        return ApiResult.success(lstDiscover, totalCount);
    }

    /**
     * 运营商发现详情
     */
    @RequestMapping(value = "/discoverDetail", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult discoverDetail(@RequestParam("Id") int Id, HttpServletResponse response) throws Exception {
        if (Id <= 0) {
            return ApiResult.error("传入的参数不正确");
        }
        Discover objcover = discoverService.GetDiscover(Id);
        if (null == objcover) {
            return ApiResult.error("没有该发布信息");
        }
        List<Continuation> lstContinuation = continuationService.ContinuationList(Id, Continuation.vendor_link_type);
        List<Vendordiscover> lstdiscover = new ArrayList<Vendordiscover>();
        for (int i = 0; i < lstContinuation.size(); i++) {
            Vendordiscover objvendordis = new Vendordiscover();
            VendorInfo objvendor = vendorInfoService.vendorDetail(Integer.parseInt(lstContinuation.get(i).ExpandText1));
            if (null != objvendor) {
                objvendordis.setId(objvendor.getId());
                objvendordis.setImage(objvendor.getImage());
                objvendordis.setVendorName(objvendor.getName());
                objvendordis.setVendorAddr(objvendor.getAddr());
                double newScore = 0;
                List<ScoreInfo> objInfo = scoreInfoService.CommonInfoList(objvendor.getId(), Constants.COMMENTVENDOR_CODE_VENDOR);
                for (int j = 0; j < objInfo.size(); j++) {
                    newScore = add(newScore, objInfo.get(j).getScore());
                }
                objvendordis.setOutScore(objInfo.size() == 0 ? BigDecimal.valueOf(0) : BigDecimal.valueOf(newScore / objInfo.size()));
                lstdiscover.add(objvendordis);
            }
        }
        UserIdentify identify = getIdentify();
        objcover.setState(Integer.parseInt(objcover.getIsUser()) == identify.getUserId() ? "Y" : "N");
        objcover.setLstcoverVendor(lstdiscover);
        return ApiResult.success(objcover);
    }

    /**
     * 下线（隐藏显示修改）
     */
    @RequestMapping(value = "/updateShow", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateShow(@RequestParam("Id") int Id, @RequestParam("IsShow") String IsShow, HttpServletResponse response) throws Exception {
        if (Id <= 0) {
            return ApiResult.error("参数错误");
        }
        discoverService.updateDiscoverShow(Id, IsShow);
        return ApiResult.success();
    }

    /**
     * 两个Double记性计算
     */
    public double add(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.add(b2).doubleValue();
    }
}
