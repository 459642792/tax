package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.BillingInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 结算信息
 *
 * @author xiaojiang 2017年3月1日
 * @version 1.0
 * @since 1.0 2017年3月1日
 */
public interface BillingInfoMapper {
    /**
     * 保存结算信息
     *
     * @param billingInfo
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    int saveBillingInfo(BillingInfo billingInfo);

    /**
     * 获取结算信息
     *
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    BillingInfo getBillingInfo(@Param("vendorInfoId") Integer vendorInfoId);

    /**
     * 修改结算信息
     *
     * @param billingInfo
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    int updateBillingInfo(BillingInfo billingInfo);

}
