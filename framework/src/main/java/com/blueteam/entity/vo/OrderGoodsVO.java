package com.blueteam.entity.vo;

import com.blueteam.base.lang.RStr;
import com.blueteam.base.util.StringUtil;

/**
 * 订单商品
 *
 * @author xiaojiang
 * @create 2018-01-13  11:41
 */
public class OrderGoodsVO {
    private Long goodsId;
    private String goodsName;
    /** 商品价格*/
    private String  goodsPrice;
    /** 商品数量 */
    private Integer goodsNum;
    /** 商品图片 */
    private String goodsImage;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        if(RStr.isNotEmpty(this.goodsPrice)){
            return  StringUtil.changeF2Y(this.goodsPrice);
        }
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }


    public OrderGoodsVO() {
    }

    public OrderGoodsVO(String goodsName, String goodsPrice, Integer goodsNum) {
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsNum = goodsNum;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsImage() {
        if(RStr.isNotEmpty(this.goodsImage)){
            String[] images = this.goodsImage.split("\\^");
            return images[0];
        }
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    @Override
    public String toString() {
        return "OrderGoodsVO{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", goodsNum=" + goodsNum +
                ", goodsImage='" + goodsImage + '\'' +
                '}';
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
