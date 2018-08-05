package com.blueteam.wineshop.service.wechatapplet;


import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.UserAddressPO;
import com.blueteam.entity.vo.UserAddressVO;

import java.util.List;

/**
 * 用户收货地址
 *
 * @author xiaojiang
 * @create 2018-01-05  11:24
 */
public interface UserAddressService {
    /**
     * 方法的功能描述:
     *
     * @return
     * @methodName 新增修改用户地址
     * @ * @param: record
     * @author xiaojiang 2018/1/5 15:21
     * @modifier
     * @since 1.4.0
     */
    BaseResult saveOrModifyUserAddress(UserAddressPO record);

    /**
     * 方法的功能描述: 获取用户说有的地址
     *
     * @return
     * @methodName
     * @ * @param: null
     * @author xiaojiang 2018/1/5 16:15
     * @modifier
     * @since 1.4.0
     */
    List<UserAddressVO> listUserAddress(Integer userId);

}
