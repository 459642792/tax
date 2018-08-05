package com.blueteam.wineshop.service;

import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.AES;
import com.blueteam.base.util.VerificationUtil;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.entity.dto.WxLoginResult;
import com.blueteam.entity.po.UserInfo;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xiaojiang
 * @create 2017-08-15  16:13
 */
@Component("userWXLogin")
public class UserWXLoginService implements UserWxLogin {
    @Autowired
    private UserService userService;
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
        WxLoginResult result = new WxLoginResult();
//        String token = VerificationUtil.getToken(userInfo, Enums.UserType.Every)；
        UserIdentify identify = new UserIdentify();
        Date loginDate = new Date();
        identify.setLoginDate(loginDate);
        identify.setUserId(userInfo.getId());
        identify.setUserName(userInfo.getUsername());
        identify.setUserType(userInfo.getUsertypes());
        identify.setCurUType(Enums.UserType.Every);
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