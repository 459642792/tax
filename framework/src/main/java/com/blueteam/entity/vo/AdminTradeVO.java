package com.blueteam.entity.vo;

import java.util.List;

/**
 * 交易管理vo
 * Created by  NastyNas on 2018/1/17.
 */
public class AdminTradeVO {
    //交易次数
    Integer count;
    //平台优惠券金额(元)
    String platformFee;
    //商家优惠券金额(元)
    String vendorFee;
    //退款金额(元)
    String refundFee;
    //交易总额(元)
    String totalFee;
    //流水金额(元)
    String statementFee;
    //当前页交易列表
    List<AdminTradeListVO> adminTradeListVOList;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<AdminTradeListVO> getAdminTradeListVOList() {
        return adminTradeListVOList;
    }

    public void setAdminTradeListVOList(List<AdminTradeListVO> adminTradeListVOList) {
        this.adminTradeListVOList = adminTradeListVOList;
    }

    public String getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(String platformFee) {
        this.platformFee = platformFee;
    }

    public String getVendorFee() {
        return vendorFee;
    }

    public void setVendorFee(String vendorFee) {
        this.vendorFee = vendorFee;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getStatementFee() {
        return statementFee;
    }

    public void setStatementFee(String statementFee) {
        this.statementFee = statementFee;
    }
}
