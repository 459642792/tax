package com.blueteam.entity.vo;

import com.blueteam.entity.po.UserAddressPO;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaojiang
 * @create 2018-01-05  16:07
 */
public class UserAddressVO extends UserAddressPO implements Serializable {
    public UserAddressVO() {
    }

    public UserAddressVO(Integer userAddressId, Integer userId, String contactName, String contactPhone, String addressName, String addressDesc, String inputAddress, String formattedAddress, String longitude, String latitude, String cityCode, String adCode, String townCode, Integer stateTag, Date createTime, Date updateTime) {
        super(userAddressId, userId, contactName, contactPhone, addressName, addressDesc, inputAddress, formattedAddress, longitude, latitude, cityCode, adCode, townCode, stateTag, createTime, updateTime);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
