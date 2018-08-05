package com.blueteam.entity.vo;

import com.blueteam.base.cache.redis.Redis;
import com.blueteam.base.lang.RStr;
import redis.clients.jedis.JedisCommands;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * 商品具体详情
 *
 * @author xiaojiang
 * @create 2017-10-24  14:46
 */
public class GoodsDetailVO extends GoodsVO implements Serializable {
    /**
     * 商品所属商家名称
     */
    private String vendorName;
    /**
     * 商品所属商家id(冗余)
     */
    private Integer vendorId;
    /**
     * 电话号码
     */
    private String telephone;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 商品所属品牌
     */
    private String brandName;
    /**
     * 商品所属品牌id(冗余)
     */
    private Integer brandId;
    /**
     * 商品所属类型id(冗余)
     */
    private Integer typeId;
    /**
     * 商品年销量
     */
    private Integer goodsYearSales;
    /**
     * 商品详情
     **/
    private String goodsDetail;
    /**
     * 商品状态 0 下架 1 上架
     **/
    private Integer goodsVendorStatus;
    /**
     * 商品图片列表
     **/
    private List<GoodsPhotoVO> goodsImages;

    private Integer major;

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }


    public Integer getGoodsYearSales() {
        return goodsYearSales;
    }

    public void setGoodsYearSales(Integer goodsYearSales) {
        this.goodsYearSales = goodsYearSales;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public Integer getGoodsVendorStatus() {
        return goodsVendorStatus;
    }

    public void setGoodsVendorStatus(Integer goodsVendorStatus) {
        this.goodsVendorStatus = goodsVendorStatus;
    }

    public List<GoodsPhotoVO> getGoodsImages() {
        return goodsImages;
    }

    public void setGoodsImages(List<GoodsPhotoVO> goodsImages) {
        this.goodsImages = goodsImages;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }
    public Integer getGoodsMonthlySales() {
        int month = calendar.get(Calendar.MONTH) + 1;
        String str = month+"_"+vendorId+"_"+super.getGoodsId()+"_monthly";
        return RStr.isNotEmpty(redis.get(str)) ? new Integer(redis.get(str)) : null;
    }
}
