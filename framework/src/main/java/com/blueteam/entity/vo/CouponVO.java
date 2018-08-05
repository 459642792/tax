package com.blueteam.entity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券显示
 *
 * @author xiaojiang
 * @create 2018-01-05  16:42
 */
public class CouponVO implements Serializable {
    /**
     * 优惠券描述
     */
    private String describe;
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
    /**
     * 优惠券开始日期
     */
    private Date startDate;
    /**
     * 优惠券截止日期）
     */
    private Date endDate;
    /**
     * 是否可用 Y 可以使用 N 不可使用
     */
    private String flag;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
