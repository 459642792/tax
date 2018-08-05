/******************************************************************
 ** 类    名：GoodsTypeInfoPO
 ** 描    述：商品类型表
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-10-18 17:49:12
 ******************************************************************/

package com.blueteam.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品类型表(TD_G_GOODS_TYPE)
 *
 * @author xiaojiang
 * @version 1.0.0 2017-10-18
 */

public class GoodsTypeInfoPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 3625532965169287108L;

    /**
     * 商品类型id:四位数字,将商品分成三个层级level1(1位表示，区分实体、虚拟等商品)-level2（1位表示，区分商品大类，当前只有酒水类）-level3（2位表示，最小粒度商品类别：当前有白酒，红酒等等）
     */
    private Integer goodsTypeId;

    /**
     * 上级商品类别id：9999表示root类别
     */
    private Integer parentTypeId;

    /**
     * 商品类型名称
     */
    private String goodsTypeName;

    /**
     * 展示顺序
     */
    private Integer shwoOrder;

    /**
     * 获取商品类型id:四位数字,将商品分成三个层级level1(1位表示，区分实体、虚拟等商品)-level2（1位表示，区分商品大类，当前只有酒水类）-level3（2位表示，最小粒度商品类别：当前有白酒，红酒等等）
     *
     * @return 商品类型id:四位数字,将商品分成三个层级level1(1位表示
     */
    public Integer getGoodsTypeId() {
        return this.goodsTypeId;
    }

    /**
     * 设置商品类型id:四位数字,将商品分成三个层级level1(1位表示，区分实体、虚拟等商品)-level2（1位表示，区分商品大类，当前只有酒水类）-level3（2位表示，最小粒度商品类别：当前有白酒，红酒等等）
     *
     * @param goodsTypeId 商品类型id:四位数字,将商品分成三个层级level1(1位表示，区分实体、虚拟等商品)-level2（1位表示，区分商品大类，当前只有酒水类）-level3（2位表示，最小粒度商品类别：当前有白酒，红酒等等）
     */
    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    /**
     * 获取上级商品类别id：9999表示root类别
     *
     * @return 上级商品类别id
     */
    public Integer getParentTypeId() {
        return this.parentTypeId;
    }

    /**
     * 设置上级商品类别id：9999表示root类别
     *
     * @param parentTypeId 上级商品类别id：9999表示root类别
     */
    public void setParentTypeId(Integer parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    /**
     * 获取商品类型名称
     *
     * @return 商品类型名称
     */
    public String getGoodsTypeName() {
        return this.goodsTypeName;
    }

    /**
     * 设置商品类型名称
     *
     * @param goodsTypeName 商品类型名称
     */
    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    /**
     * 获取展示顺序
     *
     * @return 展示顺序
     */
    public Integer getShwoOrder() {
        return this.shwoOrder;
    }

    /**
     * 设置展示顺序
     *
     * @param shwoOrder 展示顺序
     */
    public void setShwoOrder(Integer shwoOrder) {
        this.shwoOrder = shwoOrder;
    }
}