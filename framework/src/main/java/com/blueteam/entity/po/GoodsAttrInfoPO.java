/******************************************************************
 ** 类    名：GoodsAttrInfoPO
 ** 描    述：商品属性表'
 /*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
 PARTITIONS 10 *
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-10-18 14:04:45
 ******************************************************************/

package com.blueteam.entity.po;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品属性表'
 * /*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
 * PARTITIONS 10 *(TF_G_GOODS_ATTR)
 *
 * @author xiaojiang
 * @version 1.0.0 2017-10-18
 */

public class GoodsAttrInfoPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -6297747540689019390L;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 属性编码
     */
    private String attrCode;

    /**
     * 属性值编码:属性值类型为input框自定义输入时，该字段不落值
     */
    private String attrValueCode;

    /**
     * 属性值
     */
    private String attrValueName;

    /**
     * 属性值应用端展示
     */
    private String attrValueShowName;

    /**
     * 获取商品id
     *
     * @return 商品id
     */
    public Long getGoodsId() {
        return this.goodsId;
    }

    /**
     * 设置商品id
     *
     * @param goodsId 商品id
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取属性编码
     *
     * @return 属性编码
     */
    public String getAttrCode() {
        return this.attrCode;
    }

    /**
     * 设置属性编码
     *
     * @param attrCode 属性编码
     */
    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    /**
     * 获取属性值编码:属性值类型为input框自定义输入时，该字段不落值
     *
     * @return 属性值编码:属性值类型为input框自定义输入时
     */
    public String getAttrValueCode() {
        return this.attrValueCode;
    }

    /**
     * 设置属性值编码:属性值类型为input框自定义输入时，该字段不落值
     *
     * @param attrValueCode 属性值编码:属性值类型为input框自定义输入时，该字段不落值
     */
    public void setAttrValueCode(String attrValueCode) {
        this.attrValueCode = attrValueCode;
    }

    /**
     * 获取属性值
     *
     * @return 属性值
     */
    public String getAttrValueName() {
        return this.attrValueName;
    }

    /**
     * 设置属性值
     *
     * @param attrValueName 属性值
     */
    public void setAttrValueName(String attrValueName) {
        this.attrValueName = attrValueName;
    }

    /**
     * 获取属性值应用端展示
     *
     * @return 属性值应用端展示
     */
    public String getAttrValueShowName() {
        return this.attrValueShowName;
    }

    /**
     * 设置属性值应用端展示
     *
     * @param attrValueShowName 属性值应用端展示
     */
    public void setAttrValueShowName(String attrValueShowName) {
        this.attrValueShowName = attrValueShowName;
    }
}