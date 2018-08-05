package com.blueteam.entity.dto;

import java.math.BigDecimal;

/**
 * 运营商统计
 *
 * @author libra
 */
public class CarriersStatistics {
    /**
     * 入驻数量
     */
    private int enterCount;
    /**
     * 认证数量
     */
    private int authCount;
    /**
     * 营业额
     */
    private BigDecimal salesMoney;

    public int getEnterCount() {
        return enterCount;
    }

    public void setEnterCount(int enterCount) {
        this.enterCount = enterCount;
    }

    public int getAuthCount() {
        return authCount;
    }

    public void setAuthCount(int authCount) {
        this.authCount = authCount;
    }

    public BigDecimal getSalesMoney() {
        return salesMoney;
    }

    public void setSalesMoney(BigDecimal salesMoney) {
        this.salesMoney = salesMoney;
    }

}
