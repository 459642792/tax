package com.blueteam.entity.po;

import java.math.BigDecimal;

/**
 * C端发现实体
 *
 * @author libra
 */
public class Vendordiscover {

    /**
     * 总评分
     */
    private BigDecimal outScore;

    /**
     * 商家名称
     */
    private String vendorName;

    /**
     * 商家Id
     */
    private int id;

    /**
     * 商家图片
     */
    private String image;

    /**
     * 商家地址
     */
    private String vendorAddr;


    public String getVendorName() {
        return vendorName;
    }

    public BigDecimal getOutScore() {
        return outScore;
    }

    public void setOutScore(BigDecimal outScore) {
        this.outScore = outScore;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVendorAddr() {
        return vendorAddr;
    }

    public void setVendorAddr(String vendorAddr) {
        this.vendorAddr = vendorAddr;
    }

}
