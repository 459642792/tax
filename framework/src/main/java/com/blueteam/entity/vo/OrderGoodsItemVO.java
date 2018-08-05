package com.blueteam.entity.vo;

import com.blueteam.base.lang.RStr;
import com.blueteam.base.util.StringUtil;
import com.blueteam.entity.po.OrderGoodsItemPO;

import java.util.List;

/**
 * 订单显示实例
 *
 * @author xiaojiang
 * @create 2018-01-26  11:00
 */
public class OrderGoodsItemVO {
    /**
     * 商品图片 多张以^分隔
     */
    private String goodsPhotos;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品数量
     */
    private Integer goodsNum;

    /**
     * 实际支付金额(单位-分)
     */
    private Long payPrice;
    /**
     * 商品属性
     */
    private List<OrderAttrVO> orderAttr;

    public OrderGoodsItemVO() {
    }

    public OrderGoodsItemVO(String goodsPhotos, String goodsName, Integer goodsNum, List<OrderAttrVO> orderAttr) {
        this.goodsPhotos = goodsPhotos;
        this.goodsName = goodsName;
        this.goodsNum = goodsNum;
        this.orderAttr = orderAttr;
    }

    public String getGoodsPhotos() {
        return goodsPhotos;
    }

    public void setGoodsPhotos(String goodsPhotos) {
        this.goodsPhotos = goodsPhotos;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public List<OrderAttrVO> getOrderAttr() {
        return orderAttr;
    }

    public void setOrderAttr(List<OrderAttrVO> orderAttr) {
        this.orderAttr = orderAttr;
    }

    public String getGoodsImage() {
        if(RStr.isNotEmpty(this.goodsPhotos)){
            String[] images = this.goodsPhotos.split("\\^");
            return images[0];
        }
        return goodsPhotos;
    }

    public Long getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Long payPrice) {
        this.payPrice = payPrice;
    }
    /**
     * 价格处理
     * @return
     */
    public String getRealPayPrice(){
        if (RStr.isNotEmpty(this.getPayPrice())){
            return StringUtil.changeF2Y(this.getPayPrice().toString());
        }
        return null;
    }
}
