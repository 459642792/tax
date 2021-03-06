package com.blueteam.wineshop.mapper;

import com.blueteam.entity.dto.UserInfoSearch;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.po.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Marx
 * <p>
 * UserInfoDao.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface UserInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UserInfo
     *
     * @mbg.generated Mon Feb 20 11:11:38 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UserInfo
     *
     * @mbg.generated Mon Feb 20 11:11:38 CST 2017
     */
    int insert(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UserInfo
     *
     * @mbg.generated Mon Feb 20 11:11:38 CST 2017
     */
    UserInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UserInfo
     *
     * @mbg.generated Mon Feb 20 11:11:38 CST 2017
     */
    List<UserInfo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UserInfo
     *
     * @mbg.generated Mon Feb 20 11:11:38 CST 2017
     */
    int updateByPrimaryKey(UserInfo record);

    int updateUser(UserInfo record);

    /**
     * 根据条件查询用户
     *
     * @param search 查询条件
     * @return
     */
    List<UserInfo> selectByWhere(UserInfo search);

    /**
     * 根据UserId获取用户信息，包括城市中文名称
     * This method corresponds to the database table UserInfo
     *
     * @mbg.generated Mon Feb 20 11:11:38 CST 2017
     */
    UserInfo getCityUserInfo(Integer id);

    /**
     * 根据WxOpenId获取用户信息
     * This method corresponds to the database table UserInfo
     *
     * @mbg.generated Mon Feb 20 11:11:38 CST 2017
     */
    UserInfo getUserInfo(String wxopenid);

    String getUserNameById(Integer id);

    String getNickNameById(Integer id);

    String getHeadImageUrlById(Integer id);

    /**
     * 搜索用户信息，包含统计信息,交易次数，和交易总额
     *
     * @param search 搜索信息
     * @return
     */
    PageResult<List<UserInfo>> selectUserStaticsByWhere(UserInfoSearch search);


    /**
     * 根据用户账号获取用户信息
     *
     * @param userName 用户账号
     * @return
     */
    UserInfo getUserInfoByUserName(String userName);

    /**
     * 根据商家微信公众号openid 获取 用户
     *
     * @param vendorWxOpenId 微信 openid
     * @return
     */
    UserInfo getUserInfoByVendorOpenid(@Param("vendorWxOpenId") String vendorWxOpenId);


    /**
     * 方法的功能描述:TODO 根据电话号码获取userinfo
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/7/15 16:16
     * @since 1.3.0
     */
    UserInfo getUserInfoByTelePhone(@Param("telephone") String telephone, @Param("status") Integer status,
                                    @Param("thirdStatus") Integer thirdStatus, @Param("bindStatus") Integer bindStatus);

    UserInfo getThirdPartyUserInfo(@Param("thirdPartyId") String thirdPartyId, @Param("status") Integer status,
                                   @Param("thirdStatus") Integer thirdStatus, @Param("bindStatus") Integer bindStatus);

    /**
     * 修改密码
     * @param pwd
     * @param salt
     * @param id
     * @return
     */
    int updatePwd(@Param("userPwd") String pwd,@Param("passwordSalt") String salt,@Param("id") Integer id);

    /**
     * 修改登录账号(手机号)
     * @param telephone
     * @param id
     * @return
     */
    int updateAccount(@Param("telephone") String telephone,@Param("id") Integer id);

    /**
     * 获取user详细信息
     * @param id
     * @return
     */
    UserInfo getUserById(int id);
}