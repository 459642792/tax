package com.blueteam.entity.dto;

import com.blueteam.entity.bo.OrderStateBO;

import java.util.List;

/**
 * 结算列表也分页查询DTO
 * Created by  NastyNas on 2018/1/27.
 */
public class SettlementListSearchDTO extends BasePageSearch {
    //交易地区
    String tradeArea;
    //结算时间from
    String timeFrom;
    //结算时间to
    String timeTo;
    //店铺名称
    String shopName;


    /*临时数据*/
    List<OrderStateBO> orderStateBOList;


    public String getTradeArea() {
        return tradeArea;
    }

    public void setTradeArea(String tradeArea) {
        this.tradeArea = tradeArea;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<OrderStateBO> getOrderStateBOList() {
        return orderStateBOList;
    }

    public void setOrderStateBOList(List<OrderStateBO> orderStateBOList) {
        this.orderStateBOList = orderStateBOList;
    }
}
