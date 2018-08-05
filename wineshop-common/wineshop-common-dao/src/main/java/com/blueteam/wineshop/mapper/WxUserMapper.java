package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.WxUser;
import org.apache.ibatis.annotations.Param;

/**
 * Created by libra on 2017/6/2.
 * <p>
 * 微信用户信息dao
 */
public interface WxUserMapper {

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
    WxUser getWxUserByOpendIdAndUnionId(@Param("openId") String openId, @Param("unionId") String unionId);
}
