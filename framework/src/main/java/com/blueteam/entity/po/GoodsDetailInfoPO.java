/******************************************************************
 ** 类    名：GoodsDetailInfoPO
 ** 描    述：商品详情表'
 /*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
 PARTITIONS 10 *
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-10-18 17:49:12
 ******************************************************************/

package com.blueteam.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 商品详情表'
 * /*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
 * PARTITIONS 10 *(TF_G_GOODS_DETAIL)
 *
 * @author xiaojiang
 * @version 1.0.0 2017-10-18
 */
public class GoodsDetailInfoPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -8418414702076312474L;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 获取商品ID
     *
     * @return 商品ID
     */
    public Long getGoodsId() {
        return this.goodsId;
    }

    /**
     * 设置商品ID
     *
     * @param goodsId 商品ID
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取商品描述
     *
     * @return 商品描述
     */
    public String getGoodsDesc() {
        return this.goodsDesc;
    }

    /**
     * 设置商品描述
     *
     * @param goodsDesc 商品描述
     */
    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }
}