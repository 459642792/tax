package com.blueteam.entity.vo;

import com.blueteam.base.cache.redis.Redis;
import com.blueteam.base.lang.RStr;
import redis.clients.jedis.JedisCommands;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * 商品列表详情
 *
 * @author xiaojiang
 * @create 2017-10-23  18:43
 */
public class GoodsVO implements Serializable {
    public final static JedisCommands redis = Redis.getJedis();
    public final static Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间

    private Integer vendorId;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品图片(单图)
     */
    private String goodsImage;
    /**
     * 商品价格
     */
    private String goodsPrice;
    /**
     * 商品月销量
     */
    private Integer goodsMonthlySales;
    /**
     * 商品状态
     */
    private Integer goodsStatus;
    /**
     * 商品条形码
     */
    private String goodsBarcode;
    /**
     * 商品属性集合
     */
    private List<GoodsAttrVO> goodsAttr;

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

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsMonthlySales() {
        int month = calendar.get(Calendar.MONTH) + 1;
        if (RStr.isNotEmpty(vendorId)){
            String str = month+"_"+vendorId+"_"+goodsId+"_monthly";
            return RStr.isNotEmpty(redis.get(str)) ? new Integer(redis.get(str)) : null;
        }else {
            return goodsMonthlySales;
        }
    }
    public void setGoodsMonthlySales(Integer goodsMonthlySales) {
        this.goodsMonthlySales = goodsMonthlySales;
    }

    public Integer getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(Integer goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public List<GoodsAttrVO> getGoodsAttr() {
        return goodsAttr;
    }

    public void setGoodsAttr(List<GoodsAttrVO> goodsAttr) {
        this.goodsAttr = goodsAttr;
    }

    public String getGoodsBarcode() {
        return goodsBarcode;
    }

    public void setGoodsBarcode(String goodsBarcode) {
        this.goodsBarcode = goodsBarcode;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }
}
