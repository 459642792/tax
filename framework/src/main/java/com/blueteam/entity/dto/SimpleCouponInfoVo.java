package com.blueteam.entity.dto;

import java.math.BigDecimal;

/**
 * 此VO用于展示一张优惠券及其使用记录的简单信息
 *
 * @author Eric Lee 2017-03-03
 */

public class SimpleCouponInfoVo {
    private Integer id;// 优惠券ID
    private String title;// 优惠券名称
    private String status;// 优惠券使用状态
    private BigDecimal costLimitMoney;// 满多少元可使用优惠券
    private BigDecimal money;// 优惠券面额
    private String beginTime;// 开始时间
    private String endTime;// 失效时间
    private Integer usedCount; // 已使用张数
    private BigDecimal driveMoney; // 带动消费
    private String condition; //优惠券使用条件
    private Integer totalCount;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getCostLimitMoney() {
        return costLimitMoney;
    }

    public void setCostLimitMoney(BigDecimal costLimitMoney) {
        this.costLimitMoney = costLimitMoney;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String biginTime) {
        this.beginTime = biginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }

    public BigDecimal getDriveMoney() {
        return driveMoney;
    }

    public void setDriveMoney(BigDecimal driveMoney) {
        this.driveMoney = driveMoney;
    }

}
