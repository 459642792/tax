package com.blueteam.entity.bo.wechatapplet;

/**
 * 订单图片
 *
 * @author xiaojiang
 * @create 2018-01-08  17:38
 */
public class GoodsImageBO {
    private String goodsImage;

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    @Override
    public String toString() {
        return "GoodsImageBO{" +
                "goodsImage='" + goodsImage + '\'' +
                '}';
    }
}
