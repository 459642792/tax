package com.blueteam.wineshop.service;


import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.BillingInfo;

/**
 * 商户结算相关
 *
 * @author xiaojiang 2017年3月1日
 * @version 1.0
 * @since 1.0 2017年3月1日
 */
public interface BillingInfoService {
    /**
     * 新增结算信息
     *
     * @param billingInfo
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    BaseResult saveBillingInfo(BillingInfo billingInfo);

    /**
     * 获取当前结算信息
     *
     * @param billingInfoId
     * @return
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    BaseResult getBillingInfo(int billingInfoId);

    /**
     * 修改当前结算信息
     *
     * @param billingInfo
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    BaseResult updateBillingInfo(BillingInfo billingInfo);

    /**
     * 消费记录
     *
     * @param vendorId
     * @param
     * @return
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    BaseResult listOrderInfo(int vendorId, int pageIndex, int pageSize);

    /**
     * 获取最新的6条消费记录
     *
     * @param currentUserID
     * @return
     */
    BaseResult listOrderInfoSimple(int currentUserID);
}
