package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.ReVendor;

import java.util.List;

/**
 * @author Marx
 * <p>
 * CouponInfoService.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface ReVendorService {

    /**
     * 新增推荐商家
     *
     * @param reVendor
     * @return
     */
    int insert(ReVendor reVendor);

    /**
     * 修改推荐商家
     *
     * @param reVendor
     * @return
     */
    int update(ReVendor reVendor);

    /**
     * @param VendorName
     * @param TradingArea
     * @param pageSize
     * @param pageIndex
     * @return
     */
    List<ReVendor> listReVendor(String VendorName, String TradingArea, Integer pageSize, Integer pageIndex);

    /**
     * @param VendorName
     * @param TradingArea
     * @return
     */
    int ReVendorCount(String VendorName, String TradingArea);

    /**
     * @return
     */
    int MaxOrderField(String AreaAddr);

    /**
     * 删除推荐商家
     *
     * @param Id
     * @return
     */
    int DeleteReVendor(Integer Id);

    /**
     * 推荐店铺（按序号进行默认排序）
     *
     * @param
     * @return
     */
    List<ReVendor> listReVendor2(String AreaAddr);

    /**
     * 按区域查询推荐商家的信息
     *
     * @param Id
     * @return
     */
    ReVendor revendorDetail(Integer Id);

    /**
     * 修改点击数量
     *
     * @param reVendor
     * @return
     */
    int updateClick(ReVendor reVendor);

    /**
     * 推荐 修改GYS方法
     */
    MessageRecipient getMessageRecipient(Integer vendorId);
}
