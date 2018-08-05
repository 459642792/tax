package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.ThirdPartyUserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 方法的功能描述:TODO 第三方用户信息
 *
 * @author xiaojiang 2017/8/28 14:56
 * @methodName
 * @param: null
 * @return
 * @modifier
 * @since 1.4.0
 */
@Repository
public interface ThirdPartyUserInfoMapper {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(ThirdPartyUserInfo record);

    ThirdPartyUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThirdPartyUserInfo record);


    /**
     * 方法的功能描述:TODO  根据userinfoid查询用户其余信息
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/8/28 15:16
     * @modifier
     * @since 1.4.0
     */
    List<ThirdPartyUserInfo> listThirdPartyUserInfo(@Param("userInfoId") Integer userInfoId, @Param("status") Integer status,
                                                    @Param("thirdStatus") Integer thirdStatus, @Param("bindStatus") Integer bindStatus);
}