package com.blueteam.entity.dto;

import com.blueteam.entity.bo.OrderStateBO;

import java.util.List;

/**
 * 交易列表查询dto
 * Created by  NastyNas on 2018/1/15.
 */
public class TradeListSearchDTO extends BasePageSearch {
    //交易地区
    String tradeArea;
    //交易时间from
    String tradeTimeFrom;
    //交易时间to
    String tradeTimeTo;
    //店铺名称
    String shopName;
    //交易状态(订单业务状态) 9-退款失败 10-退款成功  100-交易成功  null-全部
    Integer orderBusinessState;
    //交易来源
    Integer tradeSource;

    /*临时数据*/
    List<OrderStateBO> orderStateBOList;

    public String getTradeArea() {
        return tradeArea;
    }

    public void setTradeArea(String tradeArea) {
        this.tradeArea = tradeArea;
    }

    public String getTradeTimeFrom() {
        return tradeTimeFrom;
    }

    public void setTradeTimeFrom(String tradeTimeFrom) {
        this.tradeTimeFrom = tradeTimeFrom;
    }

    public String getTradeTimeTo() {
        return tradeTimeTo;
    }

    public void setTradeTimeTo(String tradeTimeTo) {
        this.tradeTimeTo = tradeTimeTo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getOrderBusinessState() {
        return orderBusinessState;
    }

    public void setOrderBusinessState(Integer orderBusinessState) {
        this.orderBusinessState = orderBusinessState;
    }

    public Integer getTradeSource() {
        return tradeSource;
    }

    public void setTradeSource(Integer tradeSource) {
        this.tradeSource = tradeSource;
    }

    public List<OrderStateBO> getOrderStateBOList() {
        return orderStateBOList;
    }

    public void setOrderStateBOList(List<OrderStateBO> orderStateBOList) {
        this.orderStateBOList = orderStateBOList;
    }

}
