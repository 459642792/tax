package com.blueteam.entity.dto.wechatapplet;

import com.blueteam.entity.po.UserAddressPO;

import java.util.Date;

/**
 * 用户传输对象
 *
 * @author xiaojiang
 * @create 2018-01-05  14:48
 */
public class UserAddressDTO extends UserAddressPO implements java.io.Serializable {
    public UserAddressDTO() {
    }

    public UserAddressDTO(Integer userAddressId, Integer userId, String contactName, String contactPhone, String addressName, String addressDesc, String inputAddress, String formattedAddress, String longitude, String latitude, String cityCode, String adCode, String townCode, Integer stateTag, Date createTime, Date updateTime) {
        super(userAddressId, userId, contactName, contactPhone, addressName, addressDesc, inputAddress, formattedAddress, longitude, latitude, cityCode, adCode, townCode, stateTag, createTime, updateTime);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
