package com.blueteam.entity.dto;

import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;

/**
 * 商家新增商品
 *
 * @author xiaojiang
 * @create 2017-11-02  11:04
 */
public class GoodsVendorDTO implements Serializable {
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 商家价格
     */
    private String price;
    /**
     * 是否上架商品状态
     */
    private Integer goodsVendroStatus;
    /**
     * 商家id
     */
    private Integer vendorId;
    /**
     * 新增 1，修改 2
     */
    private Integer genre;

    /**
     * 是1否0是主品牌
     */
    private Integer major;

    public GoodsVendorDTO() {
    }

    public GoodsVendorDTO(Long goodsId, String price, Integer goodsVendroStatus, Integer vendorId, Integer genre) {
        this.goodsId = goodsId;
        this.price = price;
        this.goodsVendroStatus = goodsVendroStatus;
        this.vendorId = vendorId;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "GoodsVendorDTO{" +
                "goodsId=" + goodsId +
                ", price='" + price + '\'' +
                ", goodsVendroStatus=" + goodsVendroStatus +
                ", vendorId=" + vendorId +
                ", genre=" + genre +
                '}';
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getGoodsVendroStatus() {
        return goodsVendroStatus;
    }

    public void setGoodsVendroStatus(Integer goodsVendroStatus) {
        this.goodsVendroStatus = goodsVendroStatus;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getGenre() {
        return genre;
    }

    public void setGenre(Integer genre) {
        this.genre = genre;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }
}
