package com.blueteam.wineshop.service.wechatapplet;

import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.UserAddressPO;
import com.blueteam.entity.vo.UserAddressVO;
import com.blueteam.wineshop.mapper.UserAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户收货地址
 *
 * @author xiaojiang
 * @create 2018-01-05  11:25
 */
@Service
public class UserAddressImpl implements UserAddressService {
    @Autowired
    UserAddressMapper userAddressMapper;

    /**
     * 方法的功能描述: 新增或修改用户地址
     *
     * @return
     * @methodName
     * @ * @param: null
     * @author xiaojiang 2018/1/5 14:40
     * @modifier
     * @since 1.4.0
     */
    @Override
    public BaseResult saveOrModifyUserAddress(UserAddressPO record) {
        record.setStateTag(UserAddressPO.STATE_TAG_VALID);
        record.setUpdateTime(new Date());
        Map<String,Object> map = new HashMap<>();
        if (null != record.getUserAddressId() && !record.getUserAddressId().equals("")) {
            int i = userAddressMapper.modifyUserAddress(record);
            switch (i) {
                case 0:
                    return ApiResult.error("失败");
                case 1:
                    map.put("userAddressId", record.getUserAddressId());
                    map.put("message","修改成功");
                    return ApiResult.success(map);
                default:
                    return ApiResult.error("错误");
            }
        } else {
            record.setCreateTime(new Date());
            int i = userAddressMapper.saveUserAddress(record);
            switch (i) {
                case 0:
                    return ApiResult.error("失败");
                case 1:
                    map.put("userAddressId", record.getUserAddressId());
                    map.put("message","新增成功");
                    return ApiResult.success(map);
                default:
                    return ApiResult.error("错误");
            }
        }

    }

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
    @Override
    public List<UserAddressVO> listUserAddress(Integer userId) {
        return userAddressMapper.listUserAddress(userId, UserAddressPO.STATE_TAG_VALID);
    }
}
