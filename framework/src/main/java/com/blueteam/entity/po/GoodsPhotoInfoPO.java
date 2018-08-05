/******************************************************************
 ** 类    名：GoodsPhotoInfoPO
 ** 描    述：商品图片表'
 /*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
 PARTITIONS 10 *
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-10-18 17:49:12
 ******************************************************************/

package com.blueteam.entity.po;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 商品图片表'
 * /*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
 * PARTITIONS 10 *(TF_G_GOODS_PHOTO)
 *
 * @author xiaojiang
 * @version 1.0.0 2017-10-18
 */
@Entity
@Table(name = "TF_G_GOODS_PHOTO")
public class GoodsPhotoInfoPO implements java.io.Serializable {

    //图片-有效
    public static final Integer PHOTO_STATE_VALID = 1;
    //图片-失效
    public static final Integer PHOTO_STATE_INVALID = 0;

    /**
     * 版本号
     */
    private static final long serialVersionUID = -8506190269351474478L;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 商品照片
     */
    private String goodsPhoto;

    /**
     * 图片状态:0-无效 1-有效
     */
    private Integer photoState;

    /**
     * 图片色调
     */
    private String photoTone;

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
     * 获取商品照片
     *
     * @return 商品照片
     */
    public String getGoodsPhoto() {
        return this.goodsPhoto;
    }

    /**
     * 设置商品照片
     *
     * @param goodsPhoto 商品照片
     */
    public void setGoodsPhoto(String goodsPhoto) {
        this.goodsPhoto = goodsPhoto;
    }

    /**
     * 获取图片状态:0-无效 1-有效
     *
     * @return 图片状态
     */
    public Integer getPhotoState() {
        return photoState;
    }

    public void setPhotoState(Integer photoState) {
        this.photoState = photoState;
    }

    /**
     * 获取图片色调
     *
     * @return 图片色调
     */
    public String getPhotoTone() {
        return this.photoTone;
    }

    /**
     * 设置图片色调
     *
     * @param photoTone 图片色调
     */
    public void setPhotoTone(String photoTone) {
        this.photoTone = photoTone;
    }
}