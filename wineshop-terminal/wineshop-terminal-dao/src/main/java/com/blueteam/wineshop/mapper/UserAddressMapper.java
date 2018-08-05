package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.UserAddressPO;
import com.blueteam.entity.vo.UserAddressVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 方法的功能描述: 用户收货地址相关
 *
 * @author xiaojiang 2018/1/5 16:57
 * @methodName
 * @ * @param: null
 * @return
 * @modifier
 * @since 1.4.0
 */
@Repository
public interface UserAddressMapper {
    /**
     * 方法的功能描述: 新增修改用户地址
     *
     * @return
     * @methodName
     * @param: record
     * @author xiaojiang 2018/1/5 14:40
     * @modifier
     * @since 1.4.0
     */
    int saveUserAddress(UserAddressPO record);

    int modifyUserAddress(UserAddressPO record);

    /**
     * 方法的功能描述: 获取用户所有的地址
     *
     * @return
     * @methodName
     * @ * @param: null
     * @author xiaojiang 2018/1/5 16:15
     * @modifier
     * @since 1.4.0
     */
    List<UserAddressVO> listUserAddress(@Param("userId") Integer userId, @Param("stateTag") Integer stateTag);
    /**
     * 方法的功能描述: 获取用户当前地址
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/9 17:12
     *@modifier
     */
    UserAddressPO getUserAddress(@Param("userId") Integer userId ,@Param("userAddressId") Integer userAddressId);
}