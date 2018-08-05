package com.blueteam.wineshop.service;

import com.blueteam.entity.po.WxUser;

/**
 * Created by libra on 2017/6/2.
 * <p>
 * 微信信息
 */
public interface WxUserService {

    /**
     * 添加微信用户
     *
     * @param model
     * @return
     */
    int insert(WxUser model);

    /**
     * 根据主键ID修改信息
     *
     * @param model 实体
     * @return
     */
    int updateByPrimeryKey(WxUser model);

    /**
     * 根据 openid 和 UnionId获取微信用户
     *
     * @param openId  openid
     * @param unionId unionid
     * @return
     */
    WxUser getWxUserByOpendIdAndUnionId(String openId, String unionId);

}
