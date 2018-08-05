package com.blueteam.entity.bo.wechatapplet;

import com.blueteam.entity.po.OrderAttrPO;
import com.blueteam.entity.po.OrderGoodsItemPO;

import java.util.List;

/**
 * 商品主描述相关
 *
 * @author xiaojiang
 * @create 2018-01-08  15:34
 */
public class OrderGoodsItemBO {
    private  Long goodsId;
    /**  订单商品实例表 */
    OrderGoodsItemPO orderGoodsItemPO;

    /**  订单商品属性表 */
    List<OrderAttrPO> orderAttrPO;

    /**  商家商品图片数据列表 */
    List<GoodsImageBO> goodsImageBOS;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public OrderGoodsItemPO getOrderGoodsItemPO() {
        return orderGoodsItemPO;
    }

    public void setOrderGoodsItemPO(OrderGoodsItemPO orderGoodsItemPO) {
        this.orderGoodsItemPO = orderGoodsItemPO;
    }

    public List<OrderAttrPO> getOrderAttrPO() {
        return orderAttrPO;
    }

    public void setOrderAttrPO(List<OrderAttrPO> orderAttrPO) {
        this.orderAttrPO = orderAttrPO;
    }

    public List<GoodsImageBO> getGoodsImageBOS() {
        return goodsImageBOS;
    }

    public void setGoodsImageBOS(List<GoodsImageBO> goodsImageBOS) {
        this.goodsImageBOS = goodsImageBOS;
    }

    @Override
    public String toString() {
        return "OrderGoodsItemBO{" +
                "goodsId=" + goodsId +
                ", orderGoodsItemPO=" + orderGoodsItemPO +
                ", orderAttrPO=" + orderAttrPO +
                ", goodsImageBOS=" + goodsImageBOS +
                '}';
    }
}
