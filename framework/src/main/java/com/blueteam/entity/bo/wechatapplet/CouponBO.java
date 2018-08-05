package com.blueteam.entity.bo.wechatapplet;

import java.util.Date;

/**
 * 优惠券
 *
 * @author xiaojiang
 * @create 2018-01-10  14:43
 */
public class CouponBO {
    /**
     * 优惠券ID
     */
    private Integer couponId;
    /**
     * 优惠券价格（元）
     */
    private Double money;
    /**
     * 优惠券名称
     */
    private String couponName;
    /** 是否是无条件使用 */
    private String condition;
    /** 满多少钱 */
    private Double costLimitMoney;

    /**
     * 优惠券开始日期
     */
    private Date startDate;
    /**
     * 优惠券截止日期）
     */
    private Date endDate;

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Double getCostLimitMoney() {
        return costLimitMoney;
    }

    public void setCostLimitMoney(Double costLimitMoney) {
        this.costLimitMoney = costLimitMoney;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
