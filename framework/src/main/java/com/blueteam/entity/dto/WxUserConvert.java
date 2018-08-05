package com.blueteam.entity.dto;

import com.blueteam.entity.po.WxUser;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * Created by libra on 2017/6/2.
 */
public class WxUserConvert {

    public static WxUser convertWxMpUserToWxUser(WxMpUser mpUser) {
        if (mpUser == null)
            return null;

        WxUser user = new WxUser();
        user.setCity(mpUser.getCity());
        user.setCountry(mpUser.getCountry());
        user.setGroupId(mpUser.getGroupId());
        user.setHeadImgUrl(mpUser.getHeadImgUrl());
        user.setLanguage(mpUser.getLanguage());
        user.setNickname(mpUser.getNickname());
        user.setOpenId(mpUser.getOpenId());
        user.setProvince(mpUser.getProvince());
        user.setRemark(mpUser.getRemark());
        user.setSex(mpUser.getSex());
        user.setSexId(mpUser.getSexId());
        user.setSubscribe(mpUser.getSubscribe() == null ? false : mpUser.getSubscribe());
        user.setSubscribeTime(mpUser.getSubscribeTime());
        user.setUnionId(mpUser.getUnionId());
        return user;
    }

}
