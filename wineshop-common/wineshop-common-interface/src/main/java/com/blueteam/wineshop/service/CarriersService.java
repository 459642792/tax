package com.blueteam.wineshop.service;


import com.blueteam.base.constant.Enums;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.CarriersSearch;
import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.dto.CarriersStatistics;
import com.blueteam.entity.po.CarriersInfo;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.po.VendorInfo;

import java.util.List;

/**
 * 运营商服务
 *
 * @author libra
 */
public interface CarriersService {
    /**
     * 入驻
     *
     * @param phone      电话号码
     * @param vendor     商家信息
     * @param carriersId 运营商用户ID
     * @return 是否成功
     */
    BaseResult enter(String phone, VendorInfo vendor, int carriersId) throws Exception;


    /**
     * 提交认证商家资料
     *
     * @param userId 运营商用户ID
     * @param param  认证信息
     * @return
     */
    BaseResult authenticateVendor(int userId, VendorInfo param);

    /**
     * 认证商家，加V
     *
     * @param userId     执行认证用户ID
     * @param vendorId   待认证商家ID
     * @param authStatus 认证的目标状态
     * @param reason     认证通过/不通过 的理由
     * @return
     */
    BaseResult authenticate(int userId, int vendorId, Enums.VendorStatus authStatus, String reason);

    /**
     * 根据用户ID 查询运营商信息
     *
     * @param userId 用户ID
     * @return
     */
    CarriersInfo selectForUser(int userId);

    /**
     * 获取指定运营商的统计信息
     *
     * @param carriersId 运营商ID
     * @return
     */
    CarriersStatistics getStatistics(int carriersId);

    /**
     * 根据查询条件分页查询运营商
     *
     * @param search
     * @return
     */
    PageResult<List<CarriersInfo>> selectCarriersByWhere(CarriersSearch search);

    /**
     * 添加运营商
     *
     * @param carriersInfo
     * @return
     */
    BaseResult add(CarriersInfo carriersInfo);

    /**
     * 编辑运营商
     *
     * @param carriersInfo
     * @return
     */
    BaseResult edit(CarriersInfo carriersInfo);

    /**
     * 根据运营商ID获取运营商信息
     *
     * @param id 运营商ID
     * @return
     */
    CarriersInfo getCarriersByID(int id);

    /**
     * 根据运营商ID获取运营商信息
     *
     * @param id 运营商ID
     * @return
     */
    CarriersInfo getCarriersByIDInteger(Integer id);

    /**
     * 根据城市code查询运营商信息
     *
     * @param CityCode
     * @return
     */
    CarriersInfo getCarriersByCode(String CityCode);


    /**
     * 获取管理区域的商家接收者信息
     *
     * @param cityCode
     * @return
     */
    MessageRecipient getManagerAreatMessageRecipient(String cityCode);

    MessageRecipient getMessageRecipient(Integer carrierId, Integer userId);
}
