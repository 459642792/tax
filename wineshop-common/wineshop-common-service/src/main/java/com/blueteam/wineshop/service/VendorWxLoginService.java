package com.blueteam.wineshop.service;

import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.AES;
import com.blueteam.base.util.VerificationUtil;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.entity.dto.WxLoginResult;
import com.blueteam.entity.po.UserInfo;
import com.blueteam.entity.po.VendorInfo;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by libra on 2017/6/2.
 */
@Component("vendorWxLogin")
public class VendorWxLoginService implements UserWxLogin {

    @Autowired
    private UserService userService;

    @Autowired
    private VendorInfoService vendorInfoService;
    /**
     * JSON
     */
    private static ObjectMapper objectMapper;

    @Override
    public WxLoginResult logIn(WxMpUser user, Integer status, Integer thirdStatus, Integer bindStatus) {
        if (user == null || user.getOpenId() == null)
            return null;

        //查询该openid用户是否绑定
        UserInfo userInfo = userService.getThirdPartyUserInfo(user.getOpenId(), status, thirdStatus, bindStatus);

        if (userInfo == null)
            return null;

        //TODO:这里不考虑性能在请求一次
        VendorInfo vendorInfo = vendorInfoService.findByUserId(userInfo.getId());
        WxLoginResult result = new WxLoginResult();
        if (vendorInfo != null)
            result.setExtendId(vendorInfo.getId());
//        String token = VerificationUtil.getToken(userInfo, vendorInfo == null ? 0 : vendorInfo.getId(), Enums.UserType.Vendor);
        UserIdentify identify = new UserIdentify();
        Date loginDate = new Date();
        identify.setLoginDate(loginDate);
        identify.setUserId(userInfo.getId());
        identify.setUserName(userInfo.getUsername());
        identify.setUserType(userInfo.getUsertypes());
        identify.setCurUType(Enums.UserType.Vendor);
        try {
            String json = objectMapper.writeValueAsString(identify);
            String token = AES.encrypt(VerificationUtil.TOEKN_KEY, json);
            result.setToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
