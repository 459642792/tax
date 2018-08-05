package com.blueteam.entity.vo;

import com.blueteam.base.util.StringUtil;

import java.io.Serializable;

public class OrderStatisticsVO implements Serializable{
    private Integer waitReceiveOrder;
    private Integer lowerOrder;

    private Integer volume;//成交量

    private Integer pageViews;//浏览量

    private Integer todaySalesAmount;//今日销售额

    private Integer salesAmount;//总销售额

    public OrderStatisticsVO(Integer waitReceiveOrder,Integer lowerOrder){
        this.waitReceiveOrder=waitReceiveOrder;
        this.lowerOrder=lowerOrder;
    }

    public Integer getWaitReceiveOrder() {
        return waitReceiveOrder;
    }

    public void setWaitReceiveOrder(Integer waitReceiveOrder) {
        this.waitReceiveOrder = waitReceiveOrder;
    }

    public Integer getLowerOrder() {
        return lowerOrder;
    }

    public void setLowerOrder(Integer lowerOrder) {
        this.lowerOrder = lowerOrder;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getPageViews() {
        return pageViews;
    }

    public void setPageViews(Integer pageViews) {
        this.pageViews = pageViews;
    }

    public Integer getTodaySalesAmount() {
        return todaySalesAmount;
    }

    public void setTodaySalesAmount(Integer todaySalesAmount) {
        this.todaySalesAmount = todaySalesAmount;
    }

    public Integer getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Integer salesAmount) {
        this.salesAmount = salesAmount;
    }

    public String getSales(){
        return StringUtil.changeF2Y(this.getSalesAmount()+"");
    }

    public String getTodaySales(){
        return StringUtil.changeF2Y(this.getTodaySalesAmount()+"");
    }
}
