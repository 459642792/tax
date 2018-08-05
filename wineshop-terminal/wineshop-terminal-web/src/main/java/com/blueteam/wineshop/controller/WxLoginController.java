package com.blueteam.wineshop.controller;

import com.blueteam.base.conf.WxMpConfig;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.AES;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.WxLoginResult;
import com.blueteam.entity.dto.WxUserConvert;
import com.blueteam.entity.po.ThirdPartyUserInfo;
import com.blueteam.entity.po.WxUser;
import com.blueteam.wineshop.base.spring.help.SpringContextHelper;
import com.blueteam.wineshop.service.*;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


@DependsOn(value = {"springContextHelper", "vendorWxLogin"})
@RestController
@RequestMapping("/wechat/login")
public class WxLoginController {

    private static final HashMap<Integer, UserWxLogin> WX_LOGINS = new HashMap<>();

    static {
        WX_LOGINS.put(Enums.UserType.Vendor, (VendorWxLoginService) SpringContextHelper.getBean("vendorWxLogin"));
    }

    @Autowired
    private WeixinService wxMpService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

    @Autowired
    private WxMpConfig apiConfig;

    @RequestMapping(method = RequestMethod.GET)
    public BaseResult get(@RequestHeader("User-Agent") String userAgent,
                          @RequestParam(name = "code", required = false) String code,
                          @RequestParam(name = "fromUrl", required = false) String fromUrl,
                          @RequestParam(name = "utype", required = false) String utype,
                          @RequestParam(name = "state", required = false) String state) throws IOException, WxErrorException {


        //验证微信userAgent
        if (userAgent == null || userAgent.isEmpty() || !userAgent.toLowerCase().contains("micromessenger"))
            return BaseResult.error("只能在微信下访问");

        //正常登陆，获取用户信息
        if (StringUtils.isEmpty(code)) {
            String wxReturnUrl = apiConfig.getWxLoginCallbackUrl() + "?" + request.getQueryString();
            String url = wxMpService.oauth2buildAuthorizationUrl(wxReturnUrl, WxConsts.OAUTH2_SCOPE_USER_INFO, utype);
            response.sendRedirect(url);
            return BaseResult.success();
        }

        //根据code获取access token
        if (!StringUtils.isEmpty(code)) {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
            String aesOpenid = AES.encrypt(apiConfig.getApiaeskey(), wxMpUser.getOpenId());
            wxMpUser.setOpenId(aesOpenid);
            //将获取到的数据添加到数据库
            insertOrUpdateDbUser(wxMpUser);

            wxMpOAuth2AccessToken = wxMpService.oauth2refreshAccessToken(wxMpOAuth2AccessToken.getRefreshToken());
            boolean valid = wxMpService.oauth2validateAccessToken(wxMpOAuth2AccessToken);
            if (valid) {
                if (StringUtils.isEmpty(state))
                    return BaseResult.error("错误的状态");

                int userType = Integer.parseInt(state);
                WxLoginResult token = null;
                if (WX_LOGINS.containsKey(userType)) {
                    UserWxLogin wxLogin = WX_LOGINS.get(userType);
                    token = wxLogin.logIn(wxMpUser, Enums.UserType.Vendor, Enums.ThirdPartyUserInfo.WEI_XIN, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
                }

                loginRedirect(wxMpUser, fromUrl, token);
                return BaseResult.success();
            }
        }

        return BaseResult.success();
    }

    /**
     * 登录跳转
     *
     * @param wxMpUser 微信用户
     * @param fromUrl  跳转url
     * @param token    登录成功的token
     * @throws IOException
     */
    private void loginRedirect(WxMpUser wxMpUser, String fromUrl, WxLoginResult token) throws IOException {
        //查询该openid用户是否绑定
        int queryIndex = fromUrl.indexOf("?");
        String aesOpenid = AES.encrypt(apiConfig.getApiaeskey(), wxMpUser.getOpenId());
        String split = queryIndex != -1 ? "&" : "?";
        fromUrl += split + "openid=" + aesOpenid;

        if (token == null) {
            response.sendRedirect(fromUrl);
        } else {
            String aesToken = AES.encrypt(apiConfig.getApiaeskey(), token.getToken());
            fromUrl += "&token=" + aesToken + "&hasvendor=" + (token.getExtendId() == null ? "0" : token.getExtendId().toString());
            response.sendRedirect(fromUrl);
        }
    }


    private void insertOrUpdateDbUser(WxMpUser wxMpUser) {
        //将获取到的用户信息保存到数据库
        WxUser wxUser = wxUserService.getWxUserByOpendIdAndUnionId(wxMpUser.getOpenId(), wxMpUser.getUnionId());
        if (wxUser != null) {
            if (wxUser.getNickname() == null || !wxUser.getNickname().equals(wxMpUser.getNickname()) || wxUser.getHeadImgUrl() == null || !wxUser.getHeadImgUrl().equals(wxMpUser.getHeadImgUrl())) {
                WxUser updateUser = WxUserConvert.convertWxMpUserToWxUser(wxMpUser);
                updateUser.setId(wxUser.getId());
                wxUserService.updateByPrimeryKey(updateUser);
            }
        } else
            wxUserService.insert(WxUserConvert.convertWxMpUserToWxUser(wxMpUser));
    }
}