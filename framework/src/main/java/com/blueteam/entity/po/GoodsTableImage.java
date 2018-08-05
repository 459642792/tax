package com.blueteam.entity.po;
/******************************************************************
 ** 类    名：Goods_table_imageEntity
 ** 描    述：
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-04-19 15:02:50
 ******************************************************************/

import java.util.Date;

/**
 * (goods_table_image)
 * 商品图片中间表
 *
 * @author xiaoijiang
 * @version 1.0.0 2017-04-19
 */
public class GoodsTableImage implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -4012581796163224342L;

    /**  */
    private Integer id;

    /**
     * 商品表id
     */
    private Integer tradedGoodsId;

    /**
     * 商品图片id
     */
    private Integer tradedGoodsImageId;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改者
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 获取id
     *
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品表id
     *
     * @return 商品表id
     */
    public Integer getTradedGoodsId() {
        return this.tradedGoodsId;
    }

    /**
     * 设置商品表id
     *
     * @param traded_goods_id 商品表id
     */
    public void setTradedGoodsId(Integer tradedGoodsId) {
        this.tradedGoodsId = tradedGoodsId;
    }

    /**
     * 获取商品图片id
     *
     * @return 商品图片id
     */
    public Integer getTradedGoodsImageId() {
        return this.tradedGoodsImageId;
    }

    /**
     * 设置商品图片id
     *
     * @param traded_goods_image_id 商品图片id
     */
    public void setTradedGoodsImageId(Integer tradedGoodsImageId) {
        this.tradedGoodsImageId = tradedGoodsImageId;
    }

    /**
     * 获取创建者
     *
     * @return 创建者
     */
    public String getCreateBy() {
        return this.createBy;
    }

    /**
     * 设置创建者
     *
     * @param create_by 创建者
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 设置创建时间
     *
     * @param create_date 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改者
     *
     * @return 修改者
     */
    public String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 设置修改者
     *
     * @param update_by 修改者
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取修改时间
     *
     * @return 修改时间
     */
    public Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 设置修改时间
     *
     * @param update_date 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}