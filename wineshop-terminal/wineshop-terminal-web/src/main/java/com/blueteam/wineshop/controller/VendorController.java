package com.blueteam.wineshop.controller;

import com.blueteam.base.util.HttpRequestUtil;
import com.blueteam.base.util.weixin.WeiXinUtil;
import com.blueteam.wineshop.mapper.CityInfoMapper;
import com.blueteam.wineshop.mapper.ThirdPartyUserInfoMapper;
import com.blueteam.wineshop.mapper.UserInfoMapper;
import com.blueteam.base.constant.*;
import com.blueteam.base.util.FileTools;
import com.blueteam.base.util.MyBeanUtils;
import com.blueteam.base.util.VerificationUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.LoginResult;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.entity.po.*;
import com.blueteam.wineshop.service.*;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 门店控制器<br/>
 * 对店铺信息的录入、优惠券管理、相册管理等
 *
 * @author Eric Lee , 2017-02-21
 */
@VendorApiLogin
@RestController
@RequestMapping("/vendor")
public class VendorController extends BaseController {
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    VendorInfoService vendorInfoService;
    @Autowired
    ImageInfoService imageInfoService;
    @Autowired
    AdvertiseInfoService advertiseInfoService;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    PinPaiInfoService pinPaiInfoService;
    @Autowired
    CityInfoMapper cityInfoMapper;
    @Autowired
    ThirdPartyUserInfoMapper thirdPartyUserInfoMapper;
    @Autowired
    BrandInfoService brandInfoService;

    /**
     * 添加店铺信息<br/>
     *
     * @param model 参数模型
     *              必传字段：Name, CityCode, Addr, ContactPerson, Telephone, Image, Longitude, Latitude
     * @return
     */

    @ApiLogin(userType = Enums.UserType.Vendor, message = "您不是合法的商家用户")
    @RequestMapping(value = "/addVendorInfo", method = {RequestMethod.POST, RequestMethod.OPTIONS},
            params = {"Name", "CityCode", "Addr", "ContactPerson", "Telephone", "Longitude", "Latitude"})
    public BaseResult addVendorInfo(VendorInfo model, @RequestParam("Image") String Image) {
        try {
            if (!isVendorUser()) {
                return BaseResult.error("对不起，您不是商家用户！");
            }
            model.setImage(Image);
            if (!Image.isEmpty() && "fjjh-oss".equalsIgnoreCase(Image.substring(7, 15))) {
                String rgb = HttpRequestUtil.httpRequest(Image + "?x-oss-process=image/average-hue");
                model.setImageTone(rgb);
            }
            model.setCreateBy(getUserName());
            model.setCreateDate(getCurrentDateString());
            model.setStatus(Enums.VendorStatus.CheckAccess.getValue());
            model.setAuditStatus(EnabledOrDisabled.DISABLED);
            model.setUserId(getCurrentUserID());
            //查询用户
            UserInfo exampleUser = userInfoMapper.selectByPrimaryKey(getCurrentUserID());
            if (exampleUser == null) {
                return BaseResult.error("对不起，用户为空请重新登录！");
            }
            model.setVisitCount(0);
            String[] strsArea = model.getCityCode().split("\\_", -1);
            CityInfo cityInfo = null;
            String area = "";
            for (int i = 0; i < strsArea.length; i++) {
                switch (i) {
                    case 0:
                        cityInfo = cityInfoMapper.selectCityName(strsArea[i]);
                        area += cityInfo != null ? cityInfo.getName() : "";
                        break;
                    case 1:
                        cityInfo = cityInfoMapper.selectCityName(strsArea[0] + "_" + strsArea[i]);
                        area += cityInfo != null ? cityInfo.getName() : "";
                        break;
                    case 2:
                        cityInfo = cityInfoMapper.selectCityName(model.getCityCode());
                        area += cityInfo != null ? cityInfo.getName() : "";
                        break;
                    default:
                        break;
                }
            }
            model.setTradingArea(area);
            boolean isExist = vendorInfoService.findByUserId(model.getUserId()) != null;
            if (isExist) {
                return BaseResult.error("您已录入过店铺信息！");
            }
            int id = vendorInfoService.insert(model);
            //刷新Token
            LoginResult result = new LoginResult();
            UserInfo user = userInfoMapper.selectByPrimaryKey(getCurrentUserID());
            String token = VerificationUtil.getToken(user, model.getId(), Enums.UserType.Vendor);
            result.setToken(token);
            result.setHasVendorInfo(true);
            result.setVendorInfoId(id);
            ApiResult<?> a = ApiResult.success(result);
            a.setReturnId(String.valueOf(model.getId()));
            return a;
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
    }

    /**
     * 商家后台基本信息录入与修改<br/>
     *
     * @param model model 参数模型
     * @return
     */
    @RequestMapping(value = "modifyVendorInfo", method = RequestMethod.POST)
    public BaseResult modifyVendorInfo(VendorInfo model) {
        BaseResult result = BaseResult.success();
        try {
            if (!isVendorUser()) {
                return BaseResult.error("对不起，您不是商家用户！");
            }
            model.setId(getIdentify().getExtendId());
            model.setUserId(getCurrentUserID());
            model.setUpdateBy(getUserName());
            model.setUpdateDate(getCurrentDateString());
            String[] strsArea = model.getCityCode().split("\\_", -1);
            CityInfo cityInfo = null;
            String area = "";
            for (int i = 0; i < strsArea.length; i++) {
                switch (i) {
                    case 0:
                        cityInfo = cityInfoMapper.selectCityName(strsArea[i]);
                        area += cityInfo != null ? cityInfo.getName() : "";
                        break;
                    case 1:
                        cityInfo = cityInfoMapper.selectCityName(strsArea[0] + "_" + strsArea[i]);
                        area += cityInfo != null ? cityInfo.getName() : "";
                        break;
                    case 2:
                        cityInfo = cityInfoMapper.selectCityName(model.getCityCode());
                        area += cityInfo != null ? cityInfo.getName() : "";
                        break;
                    default:
                        break;
                }
            }
            //更新基本信息
            model.setTradingArea(area);
            String msg = vendorInfoService.updateByModel(model);
            result.setMessage(msg);
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
        return result;
    }

    /**
     * 商家资质认证<br/>
     *
     * @param model model 参数模型
     * @return
     */
    @RequestMapping(value = "authentication", method = RequestMethod.POST,
            params = {"legalPerson", "legalPersonIdCard", "idInHandImage", "businessLicenseImg", "licenseFileImg"})
    public BaseResult authentication(VendorInfo model) {
        BaseResult result = BaseResult.success();
        try {
            if (!isVendorUser()) {
                return BaseResult.error("对不起，您不是商家用户！");
            }
            VendorInfo tmpModel = vendorInfoService.findByUserId(getCurrentUserID());
            MyBeanUtils.copyPropertiesIgnoreNull(model, tmpModel);
            tmpModel.setAuthenticationStatus(Enums.VendorStatus.Uncheck);
            vendorInfoService.updateByPrimaryKey(tmpModel);
            result.setReturnId(String.valueOf(tmpModel.getId()));
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
        return result;
    }

    /**
     * 展示商家认证信息
     *
     * @return
     */
    @RequestMapping(value = "showAuthentication", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult showAuthentication() {
        //		Object o = "";
        //		Integer a = (Integer)o;
        try {
            if (!isVendorUser()) {
                return BaseResult.error("对不起，您不是商家用户！");
            }
            int vendorId = getIdentify().getExtendId();
            VendorInfo vendor = vendorInfoService.vendorDetail(vendorId);
            if (vendor != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", vendor.getId());
                map.put("legalPerson", vendor.getLegalPerson());
                map.put("legalPersonIdCard", vendor.getLegalPersonIdCard());
                map.put("idInHandImage", vendor.getIdInHandImage());
                map.put("businessLicenseImg", vendor.getBusinessLicenseImg());
                map.put("licenseFileImg", vendor.getLicenseFileImg());
                map.put("status", vendor.getStatus());
                map.put("authenticationStatus", vendor.getAuthenticationStatus() == null ? "" : vendor.getAuthenticationStatus().getValue());
                return ApiResult.success(map);
            } else {
                System.out.println("ss1s");
                return ApiResult.success(true);
            }
        } catch (Exception e) {
            System.out.println("sss");
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
    }

    /**
     * 展示商家基本信息
     *
     * @return
     */

    @RequestMapping(value = "showBaseInfo", method = RequestMethod.GET)
    public BaseResult showBaseInfo() {
        try {
            if (!isVendorUser()) {
                return BaseResult.error("对不起，您不是商家用户！");
            }
            int vendorId = getIdentify().getExtendId();
            VendorInfo vendor = vendorInfoService.vendorDetail(vendorId);
            Map<String, Object> map = new HashMap<String, Object>();
            if (vendor != null) {
                map.put("id", vendor.getId());
                map.put("name", vendor.getName());
                map.put("image", vendor.getImage());
                List<AdvertiseInfo> objadvertiseInfo = advertiseInfoService.VendorPhotoList(vendor.getId(), Constants.CREATE_VENDOR_GENERALVIEW);
                if (null != objadvertiseInfo && objadvertiseInfo.size() != 0) {
                    String qjImageStr = "";
                    for (AdvertiseInfo advertiseInfo : objadvertiseInfo) {
                        qjImageStr = advertiseInfo.getImg() + ",";
                    }
                    if (qjImageStr != "") {
                        qjImageStr = qjImageStr.substring(0, qjImageStr.length() - 1);
                    }
                    map.put("qjImage", qjImageStr);
                }
                map.put("opentime", vendor.getOpentime());
                map.put("label", vendor.getLabel());
                map.put("phone", vendor.getPhone());
                map.put("telephone", vendor.getTelephone());
                map.put("addr", vendor.getAddr());
                map.put("pinpaiIds", StringUtils.isBlank(vendor.getPinpaiIds()) ? "" : vendor.getPinpaiIds());
                map.put("status", vendor.getStatus());
                map.put("authenticationStatus", vendor.getAuthenticationStatus() != null ? vendor.getAuthenticationStatus().getValue() : "");
                map.put("advantage", vendor.getAdvantage());
                map.put("cityCode", StringUtils.isBlank(vendor.getCityCode()) ? "" : vendor.getCityCode());
                map.put("longitude", vendor.getLongitude());
                map.put("latitude", vendor.getLatitude());
                if (vendor.getUserId() != null && vendor.getUserId() != 0) {
//					UserInfo userinfo = 	userInfoDao.getCityUserInfo(vendor.getUserId());
                    List<ThirdPartyUserInfo> lists = thirdPartyUserInfoMapper.listThirdPartyUserInfo(vendor.getUserId(), Enums.UserType.Vendor, Enums.ThirdPartyUserInfo.WEI_XIN, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
                    if (null != lists && lists.size() != 0) {
                        map.put("wxOpenid", lists.get(0).getThirdPartyId());
                        map.put("nickName", lists.get(0).getThirdPartyNickName());
                    } else {
                        map.put("wxOpenid", "");
                        map.put("nickName", "");
                    }
                } else {
                    map.put("wxOpenid", "");
                    map.put("nickName", "");
                }
                Map<String, String> mapM = new GsonBuilder().create().fromJson(vendor.getModify(), new TypeToken<Map<String, String>>() {
                }.getType());
                map.put("editStatus", "Y");
                if (mapM != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    String name = mapM.get("Name");
                    if (null != name && !name.isEmpty()) {
                        map.put("createTimeName", sdf1.format(sdf.parse(name)));
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(sdf.parse(name));
                        calendar.add(Calendar.MONTH, 1);
                        if (calendar.getTime().compareTo(sdf.parse(sdf.format(new Date()))) > 0) {
                            map.put("editStatus", "N");
                        }

                    }
                    String image = mapM.get("Image");
                    if (null != image && !"".equals(image)) {
                        map.put("createTimeImage", sdf1.format(sdf.parse(image)));
                    }
                }
                String[] strs = map.get("cityCode").toString().split("\\_", -1);
                CityInfo cityInfo = null;
                for (int i = 0; i < strs.length; i++) {
                    switch (i) {
                        case 0:
                            cityInfo = cityInfoMapper.selectCityName(strs[i]);
                            map.put("AreaName", cityInfo != null ? cityInfo.getName() : "");
                            break;
                        case 1:
                            cityInfo = cityInfoMapper.selectCityName(strs[0] + "_" + strs[i]);
                            map.put("CSName", cityInfo != null ? cityInfo.getName() : "");
                            break;
                        case 2:
                            cityInfo = cityInfoMapper.selectCityName(map.get("cityCode").toString());
                            map.put("DistrictName", cityInfo != null ? cityInfo.getName() : "");
                            break;
                        default:
                            break;
                    }
                }
                if (map.get("CSName") == null) {
                    map.put("CSName", "");
                }
                if (map.get("DistrictName") == null) {
                    map.put("DistrictName", "");
                }
            }
            return ApiResult.success(map);
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
    }

    /**
     * 前置顶部头图
     *
     * @return
     */
    @RequestMapping(value = "raiseUpHeadImage", method = RequestMethod.POST)
    public BaseResult raiseUpHeadImage(Integer Id) {
        try {
            if (!isVendorUser()) {
                return BaseResult.error("对不起，您不是商家用户！");
            }
            //imageInfoService.raiseUpHeadImage(Id);
            advertiseInfoService.raiseUpHeadImage(Id);
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }

        return BaseResult.success();
    }

    /**
     * 删除图片：如顶部头图、相册(门脸图、店内环境图、单品图)等，都进这个Controller
     *
     * @param Id 图片Id
     * @return
     */
    @RequestMapping(value = {"removeImage", "removeHeadImage"}, method = RequestMethod.POST)
    public BaseResult removeHeadImage(Integer Id) {
        try {
            if (!isVendorUser()) {
                return BaseResult.error("对不起，您不是商家用户！");
            }
            //imageInfoService.removeHeadImage(Id);
            advertiseInfoService.removeHeadImage(Id);
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
        return BaseResult.success();
    }

    /**
     * 添加顶部头图
     *
     * @param image 图片地址
     * @return
     */
    @RequestMapping(value = "addHeadImage", method = RequestMethod.POST)
    public BaseResult addHeadImage(String image) {
        try {
            if (!isVendorUser()) {
                return BaseResult.error("对不起，您不是商家用户！");
            }
            int vendorId = getIdentify().getExtendId();
            AdvertiseInfo model = new AdvertiseInfo();
            List<AdvertiseInfo> list = advertiseInfoService.getImagesByType(vendorId, Constants.CREATE_VENDOR_DETAIL_TOPIMAGE);
            if (list.size() == 3) {
                return BaseResult.error("头图限制只能传3张");
            }
            int newSortNumber = advertiseInfoService.findMaxSortNumberOfHeadImg(vendorId) + 1;
            model.setImg(image);
            model.setEnableFlag(EnabledOrDisabled.ENABLED);
            model.setTypeCode(Constants.CREATE_VENDOR_DETAIL_TOPIMAGE);
            model.setForeignKey(vendorId + "");
            model.setSortNumber(newSortNumber);
            model.setCreateBy(getUserName());
            model.setCreateDate(getCurrentDateString());
            advertiseInfoService.save(model);
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
        return BaseResult.success();
    }

    /**
     * 展示顶部头图
     *
     * @return
     */
    @RequestMapping(value = "getHeadImages", method = RequestMethod.GET)
    public BaseResult getHeadImages() {
        // 参数检查
        UserIdentify currentUser = this.getIdentify();
        if (currentUser == null || currentUser.getExtendId() <= 0) {
            return ApiResult.error("未找到门店信息，请重新登陆！");
        }
        return getImages(Constants.CREATE_VENDOR_DETAIL_TOPIMAGE);
    }

    /**
     * 获取某个商家的指定类型的图片
     *
     * @param type 图片类型
     * @return
     */
    @RequestMapping(value = "getImages", method = RequestMethod.GET)
    public ApiResult<List<AdvertiseInfo>> getImages(String type) {
        ApiResult<List<AdvertiseInfo>> result = new ApiResult<List<AdvertiseInfo>>();
        List<AdvertiseInfo> list = null;
        try {
            if (!isVendorUser()) {
                result.setMessage("对不起，您不是商家用户！");
                return result;
            }
            Integer vendorId = getIdentify().getExtendId();
            list = advertiseInfoService.getImagesByType(vendorId, type);
            if (list.size() > 0) {
                AdvertiseInfo info = list.get(0);
                info.setFirst(true);
            }
            result = ApiResult.success(list, list.size());
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return ApiResult.error(list, e.getMessage());
        }

        return result;
    }

    /**
     * 商家添加相册
     *
     * @param image 图片地址
     * @param type  图片类型
     * @return
     */
    @RequestMapping(value = "addImages", method = RequestMethod.POST)
    public BaseResult addImage(String image, String type) {
        try {
            if (!isVendorUser()) {
                return BaseResult.error("对不起，您不是商家用户！");
            }

            Integer vendorId = getIdentify().getExtendId();
            AdvertiseInfo model = new AdvertiseInfo();
            int newSortNumber = advertiseInfoService.findMaxSortNumberOfHeadImg(vendorId) + 1;
            model.setImg(image);
            model.setTypeCode(type);
            model.setForeignKey(vendorId + "");
            model.setEnableFlag(EnabledOrDisabled.ENABLED);
            model.setSortNumber(newSortNumber);
            model.setCreateBy(getUserName());
            model.setCreateDate(getCurrentDateString());
            advertiseInfoService.save(model);
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
        return BaseResult.success();
    }

    /**
     * 获取商家的首页信息<br/>
     * 包括：店招图片、店铺名称、谁状态、今日交易额、累计交易额、账户余额(暂时用累计交易额)
     *
     * @return
     */
    @ApiLogin
    @RequestMapping(value = "getVendorIndexInfo", method = RequestMethod.GET)
    public BaseResult getVendorIndexInfo() {
        ApiResult<Map<String, Object>> result = ApiResult.success(null);
        try {
            int vendorId = getIdentify().getExtendId();
            if (0 == vendorId) {
                result.setData(null);
            } else {
                Map<String, Object> resultList = vendorInfoService.fetchIndexInfo(vendorId);
                result.setData(resultList);
            }

        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }

        return result;
    }

    /**
     * 获取商家的二维码
     *
     * @return
     */
    @RequestMapping(value = "getVendorQrcode", method = RequestMethod.GET)
    public BaseResult getVendorQrcode(HttpServletRequest request) {
        try {
            if (!isVendorUser()) {
                return BaseResult.error("对不起，您不是商家用户！");
            }
            int vendorId = getIdentify().getExtendId();
            String qrcodeImg = new FileTools().getQrcode(vendorId, request);
            return ApiResult.success(qrcodeImg);
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
    }

    //	@InitBinder
    //	public void initBinder(WebDataBinder binder,HttpServletRequest req){
    //		String suffix = req.getServletPath();
    //		suffix = suffix.substring(suffix.lastIndexOf('/'));
    //		if("/addVendorInfo".equals(suffix)){
    //			binder.registerCustomEditor(Date.class, new MyDateEditor(null, "yyyy-MM-dd HH:mm:ss", true));
    //		}
    //	}
}
