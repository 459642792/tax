package com.blueteam.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangqijun on 18/1/26.
 */
public class PromotionGoodsVO implements Serializable {
    private Integer vendorId;//参与店铺ID
    private String vendorName;//参与店铺名称
    private Long goodsId;//参与店铺商品ID
    private String goodsName;//参与店铺商品名称
    private Integer weight;//权重
    private Integer price;//售价
    private Double longitude;//店铺经度
    private Double latitude;//店铺纬度
    private Double distance;//距离
    private String vendorAddress;//店铺地址
    private List<String> goodsImage;//商品图片
    private List<String> goodsAttrName;//商品属性


    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public List<String> getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(List<String> goodsImage) {
        this.goodsImage = goodsImage;
    }

    public List<String> getGoodsAttrName() {
        return goodsAttrName;
    }

    public void setGoodsAttrName(List<String> goodsAttrName) {
        this.goodsAttrName = goodsAttrName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }
}
