package com.blueteam.entity.vo;

/**
 * 交易信息-结算  结算列表页VO
 * Created by  NastyNas on 2018/1/27.
 */
public class AdminSettlementListVO {
    //商家id
    Integer vendorId;
    //商家
    String shopName;
    //所在地
    String tradeArea;
    //交易总额
    String tradeFee;
    //已结算金额
    String settledFee;
    //最后结算日子
    String latestSettleTime;
    //今日已结算
    String todaySettledFee;
    //结算剩余
    String settleRemainFee;

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTradeArea() {
        return tradeArea;
    }

    public void setTradeArea(String tradeArea) {
        this.tradeArea = tradeArea;
    }

    public String getTradeFee() {
        return tradeFee;
    }

    public void setTradeFee(String tradeFee) {
        this.tradeFee = tradeFee;
    }

    public String getSettledFee() {
        return settledFee;
    }

    public void setSettledFee(String settledFee) {
        this.settledFee = settledFee;
    }

    public String getLatestSettleTime() {
        return latestSettleTime;
    }

    public void setLatestSettleTime(String latestSettleTime) {
        this.latestSettleTime = latestSettleTime;
    }

    public String getTodaySettledFee() {
        return todaySettledFee;
    }

    public void setTodaySettledFee(String todaySettledFee) {
        this.todaySettledFee = todaySettledFee;
    }

    public String getSettleRemainFee() {
        return settleRemainFee;
    }

    public void setSettleRemainFee(String settleRemainFee) {
        this.settleRemainFee = settleRemainFee;
    }
}
