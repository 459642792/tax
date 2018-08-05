package com.blueteam.wineshop.controller;

import com.blueteam.base.cache.redis.Redis;
import com.blueteam.base.conf.WxMpConfig;
import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Constants;
import com.blueteam.base.constant.Device;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.*;
import com.blueteam.base.util.aliyun.SmsUtil;
import com.blueteam.base.util.weixin.WeiXinUtil;
import com.blueteam.entity.dto.*;
import com.blueteam.entity.po.*;
import com.blueteam.wineshop.mapper.ThirdPartyUserInfoMapper;
import com.blueteam.wineshop.mapper.UserMiddleThirdPartyMapper;
import com.blueteam.wineshop.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisCommands;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 用户控制器
 *
 * @author libra
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    //private Logger logger = LogManager.getLogger(this.getClass());
    org.slf4j.Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WxUserService wxUserService;
    /**
     * 用户业务
     */
    @Autowired
    UserService userService;

    /**
     * 运营商业务
     */
    @Autowired
    CarriersService carriersService;

    /**
     * C端城市
     */
    @Autowired
    CityInfoService cityInfoService;

    /**
     * C端商家
     */
    @Autowired
    VendorInfoService vendorInfoService;

    /**
     * C端订单积分
     */
    @Autowired
    OrderInfoService orderInfoService;


    @Autowired
    private WxMpConfig wxApiConfig;
    @Autowired
    private ThirdPartyUserInfoMapper thirdPartyUserInfoMapper;
    @Autowired
    private UserMiddleThirdPartyMapper userMiddleThirdPartyMapper;

    /**
     * 发送验证码
     *
     * @param phone 电话号码
     * @return
     */
    @RequestMapping("/sendCode")
    @ResponseBody
    public BaseResult sendCode(@RequestParam String phone, HttpSession session) {
        String code= RandomUtils.genRandomNum(4);
        String template="SMS_123674741";
        String key = "user_binding_";
        if(SmsUtil.sendSms(phone,code,template)){
            JedisCommands redis = Redis.getJedis();
            key+=phone;
            redis.set(key,code);
            redis.expire(key,120);
            return BaseResult.success();
        }else{
            return BaseResult.error("发送验证码失败");
        }
    }

    /**
     * 登录
     * <p>
     * 账号
     *
     * @param code 校验码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/carriers/login", "/login"}, method = RequestMethod.POST)
    public BaseResult login(HttpSession session, @RequestParam String phone, @RequestParam String code) {
        if (phone == null || code == null)
            return ApiResult.error("参数错误");

        phone = phone.trim();
        code = code.trim();

        if (phone.isEmpty() || code.isEmpty())
            return ApiResult.error("参数错误");

        if (!VerificationUtil.VerificationCode(session, phone, code))
            return BaseResult.error("错误的验证码");


        ApiResult<UserInfo> user = (ApiResult<UserInfo>) userService.loginByPhone(phone, getIpAddr(), Constants.UserInfoDataSource.PHONE_CLIENT);

        if (!user.isSuccess())
            return user;
        //TODO:暂时没有考虑性能，后面可以重构
        UserInfo getUser = userService.getCityUserInfo(user.getData().getId());
        int extendId = 0;
        CarriersInfo carriers = null;
        if (isUserType(getUser.getUsertypes(), Enums.UserType.Carriers)) {
            carriers = carriersService.selectForUser(getUser.getId());
            if (carriers == null)
                return BaseResult.error("错误的运营商");
            extendId = carriers.getId();
        } else {
            return ApiResult.error("非运营商不能登录");
        }
        CarriersLoginResult result = new CarriersLoginResult();
        result.setAccount(user.getData().getUsername());
        result.setUserId(user.getData().getId());
        result.setUser(getUser);
        result.setCarriers(carriers);
        result.setToken(VerificationUtil.getToken(getUser, extendId, Enums.UserType.Carriers));
        return ApiResult.success(result);
    }

    /**
     * 商家登录
     *
     * @param telephone
     * @param code
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/vendor/login"}, method = RequestMethod.POST)
    public BaseResult login4Vendors(String telephone, String code) {
        String key="code-register-"+telephone;
        if (!code.equals(Redis.getJedis().get(key))){
            return BaseResult.error("验证码错误");
        }
        try {
            BaseResult result = userService.login4Vendors(telephone, code);
            return result;
        } catch (Exception e) {
            logger.error(ExceptionUtil.stackTraceString(e));
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
    }


    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return
     */
    @ResponseBody
    @RequestMapping("/get")
    @ApiLogin
    public BaseResult get(int userId) {
        if (userId <= 0)
            return BaseResult.error("没有查询到指定用户");
        UserInfo user = userService.getCityUserInfo(userId);

        return ApiResult.success(user);
    }

    /**
     * 杩斿洖棣栧瓧姣�
     *
     * @param strChinese
     * @param bUpCase
     * @return
     */
    public static String getPYIndexStr(String strChinese, boolean bUpCase) {
        try {
            StringBuffer buffer = new StringBuffer();
            byte b[] = strChinese.getBytes("GBK");//鎶婁腑鏂囪浆鍖栨垚byte鏁扮粍
            for (int i = 0; i < b.length; i++) {
                if ((b[i] & 255) > 128) {
                    int char1 = b[i++] & 255;
                    char1 <<= 8;//宸︾Щ杩愮畻绗︾敤鈥�<鈥濊〃绀猴紝鏄皢杩愮畻绗﹀乏杈圭殑瀵硅薄锛屽悜宸︾Щ鍔ㄨ繍绠楃鍙宠竟鎸囧畾鐨勪綅鏁帮紝骞朵笖鍦ㄤ綆浣嶈ˉ闆躲�鍏跺疄锛屽悜宸︾Щn浣嶏紝灏辩浉褰撲簬涔樹笂2鐨刵娆℃柟
                    int chart = char1 + (b[i] & 255);
                    buffer.append(getPYIndexChar((char) chart, bUpCase));
                    continue;
                }
                char c = (char) b[i];
                if (!Character.isJavaIdentifierPart(c))//纭畾鎸囧畾瀛楃鏄惁鍙互鏄�Java 鏍囪瘑绗︿腑棣栧瓧绗︿互澶栫殑閮ㄥ垎銆�
                    c = 'A';
                buffer.append(c);
            }
            return buffer.toString();
        } catch (Exception e) {
            System.out.println((new StringBuilder()).append("\u53D6\u4E2D\u6587\u62FC\u97F3\u6709\u9519").append(e.getMessage()).toString());
        }
        return null;
    }

    /**
     * 寰楀埌棣栧瓧姣�
     *
     * @param strChinese
     * @param bUpCase
     * @return
     */
    private static char getPYIndexChar(char strChinese, boolean bUpCase) {
        int charGBK = strChinese;
        char result;
        if (charGBK >= 45217 && charGBK <= 45252)
            result = 'A';
        else if (charGBK >= 45253 && charGBK <= 45760)
            result = 'B';
        else if (charGBK >= 45761 && charGBK <= 46317)
            result = 'C';
        else if (charGBK >= 46318 && charGBK <= 46825)
            result = 'D';
        else if (charGBK >= 46826 && charGBK <= 47009)
            result = 'E';
        else if (charGBK >= 47010 && charGBK <= 47296)
            result = 'F';
        else if (charGBK >= 47297 && charGBK <= 47613)
            result = 'G';
        else if (charGBK >= 47614 && charGBK <= 48118)
            result = 'H';
        else if (charGBK >= 48119 && charGBK <= 49061)
            result = 'J';
        else if (charGBK >= 49062 && charGBK <= 49323)
            result = 'K';
        else if (charGBK >= 49324 && charGBK <= 49895)
            result = 'L';
        else if (charGBK >= 49896 && charGBK <= 50370)
            result = 'M';
        else if (charGBK >= 50371 && charGBK <= 50613)
            result = 'N';
        else if (charGBK >= 50614 && charGBK <= 50621)
            result = 'O';
        else if (charGBK >= 50622 && charGBK <= 50905)
            result = 'P';
        else if (charGBK >= 50906 && charGBK <= 51386)
            result = 'Q';
        else if (charGBK >= 51387 && charGBK <= 51445)
            result = 'R';
        else if (charGBK >= 51446 && charGBK <= 52217)
            result = 'S';
        else if (charGBK >= 52218 && charGBK <= 52697)
            result = 'T';
        else if (charGBK >= 52698 && charGBK <= 52979)
            result = 'W';
        else if (charGBK >= 52980 && charGBK <= 53688)
            result = 'X';
        else if (charGBK >= 53689 && charGBK <= 54480)
            result = 'Y';
        else if (charGBK >= 54481 && charGBK <= 55289)
            result = 'Z';
        else
            result = (char) (65 + (new Random()).nextInt(25));
        if (!bUpCase)
            result = Character.toLowerCase(result);
        return result;
    }


    /***
     * 根据经纬度获取市-区域等
     *
     */
    @RequestMapping(value = "/Cityaddr", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult cityList(@RequestParam("longitude") String longitude, @RequestParam("latitude") String latitude, HttpServletResponse response) throws Exception {
        // lat 39.97646
        //log 116.3039
        String add = getAdd(longitude, latitude);
        JSONObject jsonObject = JSONObject.fromObject(add);
        JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("addrList"));
        JSONObject j_2 = JSONObject.fromObject(jsonArray.get(0));
        String allAdd = j_2.getString("admName");
        String arr[] = allAdd.split(",");
        //String str=String.valueOf(arr[0]+"-"+arr[1]+"-"+arr[2]);
        City objCity = new City();
        objCity.setCountyName(arr[2]);
        objCity.setCityName(arr[1]);
        CityInfo objInfos = cityInfoService.selectCityCode(arr[2]);
        if (objInfos != null) {
            List<VendorInfo> lstVendor = vendorInfoService.VendorInfoList(objInfos.getCode());
            if (lstVendor.size() > 0) {
                objCity.setCityCode(objInfos.getCode().substring(0, objInfos.getCode().lastIndexOf("_")));
                objCity.setCounty(objInfos.getCode());
                return ApiResult.success(objCity);
            } else {
                return ApiResult.error("没有查到对应的城市商家");
            }
        } else {
            return ApiResult.error("城市库没有该区域的维护");
        }
        // objCity.setCityCode((getPYIndexStr(arr[0],true).toLowerCase()+"_"+getPYIndexStr(arr[1],true).toLowerCase()+"_"+getPYIndexStr(arr[2],true).toLowerCase()));
        // return ApiResult.success(objCity);
    }

    public static String getAdd(String log, String lat) {
        //lat 小  log  大
        //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)
        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l=" + lat + "," + log + "&type=010";
        String res = "";
        try {
            URL url = new URL(urlString);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line + "\n";
            }
            in.close();
        } catch (Exception e) {
            System.out.println("error in wapaction,and e is " + e.getMessage());
        }
        System.out.println(res);
        return res;
    }


    /**
     * C端新增用户信息接口
     */
    @ResponseBody
    @RequestMapping(value = "/loginPhoneC", method = RequestMethod.POST)
    public BaseResult loginPhoneC(HttpSession session, @RequestParam(value = "CityCode", required = false) String CityCode, @RequestParam(value = "NickName", required = false) String NickName, @RequestParam(value = "HeadImage", required = false) String HeadImage, @RequestParam("WxOpenId") String WxOpenId, @RequestParam("phone") String phone, @RequestParam("code") String code,
                                  HttpServletResponse response) throws Exception {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        if (null == phone || !p.matcher(phone).matches()) {
            return BaseResult.error("电话号码格式错误");
        }

        String key = "user_binding_";
        JedisCommands redis = Redis.getJedis();
        key+=phone;
        if (!redis.exists(key) || !code.equals(redis.get(key))){
            return BaseResult.error("错误的验证码");
        }
        if (null == CityCode)
            CityCode = "";
        if (null == NickName)
            NickName = "";
        if (null == HeadImage)
            HeadImage = "";
        if (NickName.equals("") || HeadImage.equals("")) {
            WxUser wxUser = wxUserService.getWxUserByOpendIdAndUnionId(WxOpenId, null);
            if (wxUser != null) {
                NickName = wxUser.getNickname();
                HeadImage = wxUser.getHeadImgUrl();
            }
        }
//		UserInfo objInfo=userService.getUserInfo(WxOpenId);
        UserInfo objInfo = userService.getThirdPartyUserInfo(WxOpenId, Enums.UserType.Every, Enums.ThirdPartyUserInfo.WEI_XIN, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
        UserInfo userInfo = userService.getUserInfoByTelePhone(phone, Enums.UserType.Every, Enums.ThirdPartyUserInfo.WEI_XIN, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
        LoginResult result = new LoginResult();
        if (objInfo == null && userInfo == null) {
            UserInfo objInfos = new UserInfo();
            objInfos.setUsername(phone);
            objInfos.setCitycode(CityCode);
            objInfos.setUsertypes(Enums.EnumUserType.Every.getValue());
            objInfos.setDatasource("SmallRoutine");
            objInfos.setLogintime(new Date());
            objInfos.setTelephone(phone);
            userService.insert(objInfos);
            ThirdPartyUserInfo thirdPartyUserInfo = new ThirdPartyUserInfo();
            thirdPartyUserInfo.setThirdPartyId(WxOpenId);
            thirdPartyUserInfo.setThirdPartyNickName(NickName);
            thirdPartyUserInfo.setThirdPartyHeadImage(HeadImage);
            thirdPartyUserInfo.setUserType(Enums.UserType.Every);
            thirdPartyUserInfo.setThirdPartyType(Enums.ThirdPartyUserInfo.WEI_XIN);
            thirdPartyUserInfo.setThirdPartyStatus(ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
            thirdPartyUserInfo.setUpdateBy(phone);
            thirdPartyUserInfo.setCreateBy(phone);
            thirdPartyUserInfo.setUpdateDate(new Date());
            thirdPartyUserInfo.setCreateDate(new Date());
            thirdPartyUserInfoMapper.insertSelective(thirdPartyUserInfo);
            UserMiddleThirdParty userMiddleThirdParty = new UserMiddleThirdParty();
            userMiddleThirdParty.setThirdPartyId(thirdPartyUserInfo.getId());
            userMiddleThirdParty.setUserInfoId(objInfos.getId());
            userMiddleThirdParty.setMiddleStatus(ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
            userMiddleThirdPartyMapper.insertSelective(userMiddleThirdParty);
            Map<String, Object> results = orderInfoService.sumjifen(objInfos.getId());
            objInfo.setJifen(results.get("jifens").toString());
            result.setUser(objInfos);
            result.setToken(VerificationUtil.getToken(objInfos, Enums.UserType.Every));
            return ApiResult.success(result);
        } else if (objInfo != null && userInfo == null) {
            objInfo.setTelephone(phone);
            if (!Enums.FlagEnumHelper.HasFlag(objInfo.getUsertypes(), Enums.UserType.Every)) {//如果不是普通用户 者usertype需要修改状态
                int newType = objInfo.getUsertypes() + Enums.UserType.Every;
                objInfo.setUsertypes(newType);
            }
            userService.updateByPrimaryKey(objInfo);
            Map<String, Object> results = orderInfoService.sumjifen(objInfo.getId());
            if (results != null) {
                objInfo.setJifen(results.get("jifens").toString());
            }
            result.setUser(objInfo);
            result.setToken(VerificationUtil.getToken(objInfo, objInfo.getUsertypes()));
            return ApiResult.success(result);
        } else if (objInfo == null && userInfo != null) {
            if (userInfo.getThirdPartyUserInfos() != null && userInfo.getThirdPartyUserInfos().size() != 0) {
                return ApiResult.error("该手机号码已存在！");
            } else {
                userInfo.setUsername(phone);
                userInfo.setCitycode(CityCode);
                ThirdPartyUserInfo thirdPartyUserInfo = new ThirdPartyUserInfo();
                thirdPartyUserInfo.setThirdPartyId(WxOpenId);
                thirdPartyUserInfo.setThirdPartyNickName(NickName);
                thirdPartyUserInfo.setThirdPartyHeadImage(HeadImage);
                thirdPartyUserInfo.setUserType(Enums.UserType.Every);
                thirdPartyUserInfo.setThirdPartyType(Enums.ThirdPartyUserInfo.WEI_XIN);
                thirdPartyUserInfo.setThirdPartyStatus(ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
                thirdPartyUserInfo.setUpdateBy(phone);
                thirdPartyUserInfo.setCreateBy(phone);
                thirdPartyUserInfo.setUpdateDate(new Date());
                thirdPartyUserInfo.setCreateDate(new Date());
                thirdPartyUserInfoMapper.insertSelective(thirdPartyUserInfo);
                UserMiddleThirdParty userMiddleThirdParty = new UserMiddleThirdParty();
                userMiddleThirdParty.setThirdPartyId(thirdPartyUserInfo.getId());
                userMiddleThirdParty.setUserInfoId(userInfo.getId());
                userMiddleThirdParty.setMiddleStatus(ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
                userMiddleThirdPartyMapper.insertSelective(userMiddleThirdParty);
                if (!Enums.FlagEnumHelper.HasFlag(userInfo.getUsertypes(), Enums.UserType.Every)) {//如果不是普通用户 者usertype需要修改状态
                    int newType = userInfo.getUsertypes() + Enums.UserType.Every;
                    userInfo.setUsertypes(newType);
                }
                userService.updateByPrimaryKey(userInfo);
                Map<String, Object> results = orderInfoService.sumjifen(userInfo.getId());
                if (results != null) {
                    userInfo.setJifen(results.get("jifens").toString());
                }
                result.setUser(userInfo);
                result.setToken(VerificationUtil.getToken(userInfo, userInfo.getUsertypes()));
                return ApiResult.success(result);
            }
        } else if (objInfo.getId() == userInfo.getId()) {
            Map<String, Object> results = orderInfoService.sumjifen(userInfo.getId());
            if (results != null) {
                userInfo.setJifen(results.get("jifens").toString());
            }
            result.setUser(userInfo);
            result.setToken(VerificationUtil.getToken(userInfo, userInfo.getUsertypes()));
            return ApiResult.success(result);
        } else {
            return ApiResult.error("该手机号码已存在！");
        }
    }

    /**
     * 方法的功能描述:TODO Wap端登录
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/7/15 16:35
     * @since 1.3.0
     */
    @ResponseBody
    @RequestMapping(value = "/loginPhoneWap", method = RequestMethod.GET)
    public BaseResult loginPhoneWap(HttpSession session, @RequestParam("phone") String phone, @RequestParam("code") String code,
                                    HttpServletResponse response) throws Exception {
        Pattern p = Pattern.compile("^((1[0-9][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        if (null == phone || !p.matcher(phone).matches()) {
            return BaseResult.error("电话号码格式错误");
        }
        if (!VerificationUtil.VerificationCode(session, phone, code)) {
            return BaseResult.error("错误的验证码");
        }
        UserInfo userInfo = userService.getUserInfoByTelePhone(phone, Enums.UserType.Every, Enums.ThirdPartyUserInfo.WEI_XIN, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
        LoginResult result = new LoginResult();
        if (userInfo == null) {
            UserInfo objInfos = new UserInfo();
            objInfos.setUsername(phone);
            objInfos.setCitycode("");
            objInfos.setUsertypes(Enums.EnumUserType.Every.getValue());
            objInfos.setDatasource("SmallRoutine");
            objInfos.setLogintime(new Date());
            objInfos.setTelephone(phone);
            objInfos.setNickname(phone);
            userService.insert(objInfos);
            Map<String, Object> results = orderInfoService.sumjifen(objInfos.getId());
            if (results != null) {
                userInfo.setJifen(results.get("jifens").toString());
            }
            result.setUser(objInfos);
            result.setToken(VerificationUtil.getToken(objInfos, Enums.UserType.Every));
            return ApiResult.success(result);
        } else {
            Map<String, Object> results = orderInfoService.sumjifen(userInfo.getId());
            if (!Enums.FlagEnumHelper.HasFlag(userInfo.getUsertypes(), Enums.UserType.Every)) {//如果不是普通用户 者usertype需要修改状态
                int newType = userInfo.getUsertypes() + Enums.UserType.Every;
                userInfo.setUsertypes(newType);
                userService.updateByPrimaryKey(userInfo);
            }
            if (results != null) {
                userInfo.setJifen(results.get("jifens").toString());
            }
            result.setUser(userInfo);
            result.setToken(VerificationUtil.getToken(userInfo, Enums.UserType.Every));
            return ApiResult.success(result);
        }
    }

    /**
     * C端新增用户信息接口
     */
    @ResponseBody
    @RequestMapping(value = "/loginC", method = RequestMethod.POST)
    public BaseResult loginC(@RequestParam(value = "CityCode", required = false) String CityCode,
                             @RequestParam(value = "NickName", required = false) String NickName,
                             @RequestParam(value = "HeadImage", required = false) String HeadImage,
                             @RequestParam("WxOpenId") String WxOpenId,
                             HttpServletResponse response) throws Exception {
//		UserInfo objInfo=userService.getUserInfo(WxOpenId);
        UserInfo objInfo = userService.getThirdPartyUserInfo(WxOpenId, Enums.UserType.Every, Enums.ThirdPartyUserInfo.WEI_XIN, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
        LoginResult result = new LoginResult();
//		if(objInfo==null)
//		{
//			UserInfo objInfos=new UserInfo();
//			objInfos.setNickname(NickName);
//			objInfos.setUsername("");
//			objInfos.setWxopenid(WxOpenId);
//			objInfos.setCitycode(CityCode);
//			objInfos.setUsertypes(Enums.EnumUserType.Every.getValue());
//			objInfos.setHeadimage(HeadImage);
//			objInfos.setDatasource("SmallRoutine");
//			objInfos.setLogintime(new Date());
//			userService.insert(objInfos);
//			UserInfo objInfoss=userService.getUserInfo(WxOpenId);
//			Map<String, Object> results = orderInfoService.sumjifen(objInfoss.getId());
//			objInfo.setJifen(results.get("jifens").toString());
//			result.setUser(objInfoss);
//			result.setToken(VerificationUtil.getToken(objInfoss,UserType.Every));
//		}else
//		{
//			result.setUser(objInfo);
//			Map<String, Object> results = orderInfoService.sumjifen(objInfo.getId());
//			if(results!=null)
//			{
//				objInfo.setJifen(results.get("jifens").toString());
//			}
//			result.setToken(VerificationUtil.getToken(objInfo,UserType.Every));
//		}
        if (objInfo != null) {
            objInfo.setNickname(objInfo.getThirdPartyUserInfos().get(0).getThirdPartyNickName());
            objInfo.setHeadimage(objInfo.getThirdPartyUserInfos().get(0).getThirdPartyHeadImage());
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
            if (null != objInfo.getTelephone() && p.matcher(objInfo.getTelephone()).matches()) {
                result.setUser(objInfo);
                Map<String, Object> results = orderInfoService.sumjifen(objInfo.getId());
                if (results != null) {
                    objInfo.setJifen(results.get("jifens").toString());
                }
                result.setToken(VerificationUtil.getToken(objInfo, Enums.UserType.Every));
                return ApiResult.success(result);
            } else {
                return ApiResult.error("手机号码缺失");
            }
        } else {
            return ApiResult.error("手机号码缺失");
        }
    }

    /**
     * 编辑用户信息
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin
    public BaseResult modify(UserInfo param) {
        if (param == null || StringUtil.IsNullOrEmpty(param.getNickname())
                || StringUtil.IsNullOrEmpty(param.getTelephone()) || StringUtil.IsNullOrEmpty(param.getCitycode())
                || param.getId() == null)
            return BaseResult.error("参数错误");

        if (param.getId() != getCurrentUserID())
            return BaseResult.error("您不能修改其他用户的个人信息!");
        UserInfo user = userService.selectByPrimaryKey(param.getId());
        if (user == null)
            return BaseResult.error("没有找到对应的用户信息");

        user.setAddr(param.getAddr());
        user.setNickname(param.getNickname());
        user.setTelephone(param.getTelephone());
        user.setHeadimage(param.getHeadimage());
        user.setCitycode(param.getCitycode());

        try {
            int result = userService.updateByPrimaryKey(user);
            if (result > 0)
                return BaseResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

        return BaseResult.error("修改用户信息错误");
    }

    private static Map<String, Boolean> CODE_MAPS = new HashMap<String, Boolean>();

    /**
     * 监测验证码
     *
     * @param phone 手机号码
     * @param code  验证码
     * @return
     */
    @RequestMapping(value = "/checkVerificationCode", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin
    public BaseResult checkVerificationCode(HttpSession session, String phone, String code) {
        if (!VerificationUtil.VerificationCode(session, phone, code))
            return BaseResult.error("错误的验证码");

        String key = getToken() + phone;
        if (CODE_MAPS.containsKey(key))
            CODE_MAPS.remove(key);

        CODE_MAPS.put(key, true);
        return ApiResult.success(true);
    }

    /**
     * 修改用户登录名
     *
     * @param session
     * @param phone   手机号码
     * @param code    验证码
     * @return
     */
    @RequestMapping(value = "/modifyUserName", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin
    public BaseResult modifyUserName(HttpSession session, String phone, String code) {
        if (!VerificationUtil.VerificationCode(session, phone, code))
            return BaseResult.error("错误的验证码");

        UserInfo user = userService.selectByPrimaryKey(getCurrentUserID());
        if (user == null)
            return BaseResult.error("没有找到登录用户信息");


        String key = getToken() + user.getUsername();
        if (!CODE_MAPS.containsKey(key))
            return BaseResult.error("请先校验原登录手机");
        CODE_MAPS.remove(key);

        UserInfo phoneUser = userService.getUser(phone);
        if (phoneUser != null)
            return BaseResult.error("手机号码[" + phone + "]已被其他用户使用");

        user.setUsername(phone);
        user.setUpdatedate(new Date());
        int count = userService.updateByPrimaryKey(user);
        if (count > 0)
            return BaseResult.success();
        return BaseResult.error("修改登录手机成功，请更换登录手机重新登录!");
    }

    /**
     * 绑定设备
     *
     * @param token      设备号
     * @param deviceType 设备类型 取值 为   Device常量类里面的值
     * @return
     */
    @RequestMapping(value = "/bindDeviceToken", method = RequestMethod.POST)
    @ApiLogin
    @ResponseBody
    public BaseResult bindDeviceToken(String token, String deviceType) {
        if (StringUtil.IsNullOrEmpty(token) || StringUtil.IsNullOrEmpty(deviceType))
            return BaseResult.error("参数错误");

        if (!deviceType.equals(Device.ANDROID) && !deviceType.equals(Device.IOS))
            return BaseResult.error("设备类型参数错误");

        UserInfo userInfo = userService.getCityUserInfo(super.getCurrentUserID());

        token = deviceType + ":" + token;
        userInfo.setDevice_tokens(token);
        int count = userService.updateByPrimaryKey(userInfo);
        if (count > 0)
            return BaseResult.success();
        return BaseResult.error("绑定设备失败");
    }


    /**
     * 绑定商家微信OPENID
     *
     * @param aesOpenid
     * @return
     */
    @RequestMapping(value = "/bindVendorWx", method = RequestMethod.POST)
    @ApiLogin
    @ResponseBody
    public BaseResult bindVendorWx(@RequestParam("aesOpenid") String aesOpenid, @RequestParam("source") Integer source, @RequestParam("type") Integer type) {
        UserInfo userInfo = userService.selectByPrimaryKey(super.getCurrentUserID());
        if (userInfo == null)
            return BaseResult.error("错误的登录用户");
        List<ThirdPartyUserInfo> lists = thirdPartyUserInfoMapper.listThirdPartyUserInfo(super.getCurrentUserID(), source, type, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
        String openId = AES.decrypt(wxApiConfig.getApiaeskey(), aesOpenid);
        if (StringUtil.IsNullOrEmpty(openId))
            return BaseResult.error("错误的openid");
        UserInfo objInfo = userService.getThirdPartyUserInfo(aesOpenid, source, type, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
        if (objInfo == null) {//openid没有绑定过商家
            if (null != lists && lists.size() != 0) {
                return BaseResult.error("该手机号码" + userInfo.getTelephone() + "已经绑定了" + lists.get(0).getThirdPartyId());
            } else {
                ThirdPartyUserInfo thirdPartyUserInfo = new ThirdPartyUserInfo();
                thirdPartyUserInfo.setThirdPartyId(aesOpenid);
                thirdPartyUserInfo.setUserType(source);
                thirdPartyUserInfo.setThirdPartyType(type);
                thirdPartyUserInfo.setThirdPartyStatus(ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
                thirdPartyUserInfo.setUpdateBy(userInfo.getUsername());
                thirdPartyUserInfo.setCreateBy(userInfo.getUsername());
                thirdPartyUserInfo.setUpdateDate(new Date());
                thirdPartyUserInfo.setCreateDate(new Date());
                thirdPartyUserInfoMapper.insertSelective(thirdPartyUserInfo);
                UserMiddleThirdParty userMiddleThirdParty = new UserMiddleThirdParty();
                userMiddleThirdParty.setThirdPartyId(thirdPartyUserInfo.getId());
                userMiddleThirdParty.setUserInfoId(userInfo.getId());
                userMiddleThirdParty.setMiddleStatus(ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
                userMiddleThirdPartyMapper.insertSelective(userMiddleThirdParty);
                return BaseResult.success();
            }
        } else {
            if (null != lists && lists.size() != 0) {
                if (lists.get(0).getThirdPartyId().equals(objInfo.getThirdPartyUserInfos().get(0).getThirdPartyId())) {
                    return BaseResult.success();
                } else {
                    return BaseResult.error("该手机号码" + objInfo.getTelephone() + "已经绑定了" + aesOpenid);
                }
            } else {
                return BaseResult.error("该手机号码" + objInfo.getTelephone() + "已经绑定了" + aesOpenid);
            }
        }
    }

    /**
     * 方法的功能描述:TODO wxOpenId 和 telephone解除绑定
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/8/23 14:32
     * @since 1.4.0
     */
    @RequestMapping(value = "/unbindWXOpenId", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult unbindWXOpenId(@RequestParam(name = "aesOpenid", required = false) String aesOpenid, @RequestParam("telephone") String telephone, @RequestParam("source") Integer source, @RequestParam("type") Integer type) {
//        String openId = AES.decrypt(wxApiConfig.getApiaeskey(), wxOpenId);
//        if (StringUtil.IsNullOrEmpty(openId))
//            return BaseResult.error("错误的openid");
//        UserInfo objInfo = userService.getThirdPartyUserInfo(wxOpenId, source, type, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
        UserInfo objInfo = userService.getUserInfoByTelePhone(telephone, source, type, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
        if (objInfo != null) {
//            if (objInfo.getTelephone().equals(telephone)) {
            List<ThirdPartyUserInfo> list = objInfo.getThirdPartyUserInfos();
            if (null != list && list.size() != 0) {
                ThirdPartyUserInfo thirdPartyUserInfo = list.get(0);
                thirdPartyUserInfo.setThirdPartyStatus(ThirdPartyUserInfo.THIRD_PARTY_STATUS_UNBIND);
                thirdPartyUserInfo.setUpdateDate(new Date());
                thirdPartyUserInfo.setUpdateBy(objInfo.getUsername());
                int i = thirdPartyUserInfoMapper.updateByPrimaryKeySelective(thirdPartyUserInfo);
                UserMiddleThirdParty userMiddleThirdParty = new UserMiddleThirdParty();
                userMiddleThirdParty.setMiddleStatus(ThirdPartyUserInfo.THIRD_PARTY_STATUS_UNBIND);
                userMiddleThirdParty.setThirdPartyId(thirdPartyUserInfo.getId());
                userMiddleThirdParty.setUserInfoId(objInfo.getId());
                userMiddleThirdPartyMapper.updateByOtherIdStatus(userMiddleThirdParty);
                return ApiResult.success("解绑成功");
            } else {
//                    return ApiResult.error("该wxOpenId暂时没有绑定手机号码");
                return ApiResult.error("该手机号码d暂时没有绑定wxOpenI");
            }
//            } else {
//                return ApiResult.error("该wxOpenId绑定的手机号码不是该" + telephone + "号码");
//            }
        } else {
            return ApiResult.error("该手机号码d暂时没有绑定wxOpenI");
        }

    }



    /**
     * 找回密码
     * ljc 2018年1月24日 16:44:26
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/vendor/retrievePwd",method = RequestMethod.POST)
    public BaseResult retrievePwd(@RequestBody VendorLoginDTO dto){
        logger.info("params of /vendor/retrievePwd:{}",JsonUtil.serialize(dto));
        String key="code-retrieve-"+dto.getTelephone();
        if (!dto.getCode().equals(Redis.getJedis().get(key))){
            return BaseResult.error("验证码错误");
        }
        return userService.loginByPwd(dto.getTelephone(),dto.getPassword(),dto.getCode());
    }


    /**
     * 密码登录
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/vendor/loginByPwd",method = RequestMethod.POST)
    public BaseResult loginByPwd(@RequestBody VendorLoginDTO dto){

        return userService.loginByPwd(dto.getTelephone(),dto.getPassword(),null);
    }


    /**
     * 发送验证码
     * @param telephone 电话号码
     * @return
     *
     */
    @ResponseBody
    @RequestMapping("/send/{param}")
    public BaseResult sendCheckCode(@PathVariable(value="param") String param,String telephone) {
        if (jodd.util.StringUtil.isBlank(telephone)){
            return BaseResult.error("电话号码不能为空");
        }
        String code=RandomUtils.genRandomNum(4);
        String key=null;
        //SMS_123671337(认证)
        //SMS_123671328（修改密码）
        //SMS_123671326（商家注册）
        String template="";
        if ("register".equals(param.trim())){//注册验证码
            key="code-register-"+telephone;
            template="SMS_123736164";
        }else if ("retrieve".equals(param)){//找回密码
            key="code-retrieve-"+telephone;
            template="SMS_123671328";
        }else if ("authentication".equals(param)){//商家验证
            key="code-authentication-"+telephone;
            template="SMS_123671337";
        }else {
            BaseResult.error("请求路径错误");
        }
        if(SmsUtil.sendSms(telephone,code,template)){
            Redis.getJedis().set(key,code,"NX","EX",5*60);
            return BaseResult.success();
        }
        return BaseResult.error("短信发送失败");
    }


    /**
     * 设置密码
     * @return
     */
    @ResponseBody
    @RequestMapping( value = "/vendor/updatePwd",method = RequestMethod.POST)
    @ApiLogin
    public BaseResult updatePwd(@RequestBody VendorLoginDTO dto){
        return userService.updatePwd(dto.getOldPwd(),dto.getNewPwd(),this.getCurrentUserID());
    }

    /**
     * 修改登录账号
     * @return
     */
    @ResponseBody
    @RequestMapping( value = "/vendor/updateAccount",method = RequestMethod.POST)
    @ApiLogin
    public BaseResult updateAccount(@RequestBody VendorLoginDTO dto){
        logger.info("params of /vendor/updateAccount:{}",JsonUtil.serialize(dto));
        String key="code-register-"+dto.getTelephone();
        if (!dto.getCode().equals(Redis.getJedis().get(key))){
            return BaseResult.error("验证码错误");
        }

        return userService.updateAccount(dto.getTelephone(),this.getCurrentUserID());
    }
}
