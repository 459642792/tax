package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.WxLoginResult;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * Created by libra on 2017/6/2.
 */
public interface UserWxLogin {
    WxLoginResult logIn(WxMpUser user, Integer status, Integer thirdStatus, Integer bindStatus);
}
