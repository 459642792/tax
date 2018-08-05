package com.blueteam.wineshop.service;

import com.blueteam.wineshop.mapper.WxUserMapper;
import com.blueteam.entity.po.WxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by libra on 2017/6/2.
 */
@Service
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private WxUserMapper dao;

    /**
     * 添加微信用户
     *
     * @param model
     * @return
     */
    @Override
    public int insert(WxUser model) {
        return dao.insert(model);
    }

    /**
     * 根据主键ID修改信息
     *
     * @param model 实体
     * @return
     */
    @Override
    public int updateByPrimeryKey(WxUser model) {
        return dao.updateByPrimeryKey(model);
    }

    /**
     * 根据 openid 和 UnionId获取微信用户
     *
     * @param openId  openid
     * @param unionId unionid
     * @return
     */
    @Override
    public WxUser getWxUserByOpendIdAndUnionId(String openId, String unionId) {
        return dao.getWxUserByOpendIdAndUnionId(openId, unionId);
    }
}
