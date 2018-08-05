package com.blueteam.entity.dto;

/**
 * 品牌商品列表
 *
 * @author xiaojiang
 * @create 2017-10-23  10:58
 */
public class BrandGoodsDTO {
    /** 商品名称 */
    private String goodsName;
    /** 商品酒精度数 **/
    private String goodsDegree;
    /** 商品气味类型 **/
    private String goodsOdorType;
    /** 商品体积 **/
    private String goodsBulk;
    /** 商品类型 **/
    private String goodsGenre;
    /** 商品月销量 **/
    private String goodsMonthlySales;
    /** 商品年销量 **/
    private String goodsYearSales;
    /** 商品价格 **/
    private String goodsPrice;
    /** 商品图片 **/
    private String goodsImage;

    public BrandGoodsDTO() {
    }

    public BrandGoodsDTO(String goodsName, String goodsDegree, String goodsOdorType, String goodsBulk, String goodsGenre, String goodsMonthlySales, String goodsYearSales, String goodsPrice, String goodsImage) {
        this.goodsName = goodsName;
        this.goodsDegree = goodsDegree;
        this.goodsOdorType = goodsOdorType;
        this.goodsBulk = goodsBulk;
        this.goodsGenre = goodsGenre;
        this.goodsMonthlySales = goodsMonthlySales;
        this.goodsYearSales = goodsYearSales;
        this.goodsPrice = goodsPrice;
        this.goodsImage = goodsImage;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDegree() {
        return goodsDegree;
    }

    public void setGoodsDegree(String goodsDegree) {
        this.goodsDegree = goodsDegree;
    }

    public String getGoodsOdorType() {
        return goodsOdorType;
    }

    public void setGoodsOdorType(String goodsOdorType) {
        this.goodsOdorType = goodsOdorType;
    }

    public String getGoodsBulk() {
        return goodsBulk;
    }

    public void setGoodsBulk(String goodsBulk) {
        this.goodsBulk = goodsBulk;
    }

    public String getGoodsGenre() {
        return goodsGenre;
    }

    public void setGoodsGenre(String goodsGenre) {
        this.goodsGenre = goodsGenre;
    }

    public String getGoodsMonthlySales() {
        return goodsMonthlySales;
    }

    public void setGoodsMonthlySales(String goodsMonthlySales) {
        this.goodsMonthlySales = goodsMonthlySales;
    }

    public String getGoodsYearSales() {
        return goodsYearSales;
    }

    public void setGoodsYearSales(String goodsYearSales) {
        this.goodsYearSales = goodsYearSales;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }
}
