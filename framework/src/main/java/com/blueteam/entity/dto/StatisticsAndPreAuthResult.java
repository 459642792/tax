package com.blueteam.entity.dto;

import com.blueteam.entity.po.VendorInfo;

import java.util.List;

/**
 * 运营商统计 和 待认证商家 结果
 *
 * @author libra
 */
public class StatisticsAndPreAuthResult {
    /**
     * 统计信息
     */
    private CarriersStatistics statistics;

    /**
     * 待认证商家的集合
     */
    private List<VendorInfo> vendorList;


    public CarriersStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(CarriersStatistics statistics) {
        this.statistics = statistics;
    }

    public List<VendorInfo> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<VendorInfo> vendorList) {
        this.vendorList = vendorList;
    }
}
