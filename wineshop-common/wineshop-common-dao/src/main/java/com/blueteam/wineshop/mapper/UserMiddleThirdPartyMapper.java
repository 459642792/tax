package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.UserMiddleThirdParty;

public interface UserMiddleThirdPartyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserMiddleThirdParty record);

    int insertSelective(UserMiddleThirdParty record);

    UserMiddleThirdParty selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserMiddleThirdParty record);

    int updateByPrimaryKey(UserMiddleThirdParty record);

    /**
     * 方法的功能描述:TODO 根据 userid和thirdPartyId 修改中间表状态
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/9/23 11:39
     * @modifier
     * @since 1.4.0
     */
    int updateByOtherIdStatus(UserMiddleThirdParty record);
}