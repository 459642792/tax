package com.blueteam.entity.po;
/******************************************************************
 ** 类    名：Traded_goods_imageEntity
 ** 描    述：
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-04-19 15:02:50
 ******************************************************************/

import java.util.Date;

/**
 * (traded_goods_image)
 * 商品图片表
 *
 * @author xiaojiang
 * @version 1.0.0 2017-04-19
 */
public class TradedGoodsImage implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 1737603310397897951L;

    /**
     * 酒币商品头图
     */
    public static final int TYPE_HOME = 1;

    /**
     * 酒币商品详情
     */
    public static final int TYPE_DETAILS = 2;

    /**
     * 其他：如用户店家商品
     */
    public static final int TYPE_OTHER = 3;

    /**  */
    private Integer id;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 图片类型(1 商品头图，2商品详情)
     */
    private Integer type;

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
     * 获取图片地址
     *
     * @return 图片地址
     */
    public String getImageUrl() {
        return this.imageUrl;
    }

    /**
     * 设置图片地址
     *
     * @param image_url 图片地址
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 获取 图片类型(1 商品头图，2商品详情)
     *
     * @return 图片类型(1 商品头图
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置 图片类型(1 商品头图，2商品详情)
     *
     * @param type 图片类型(1 商品头图，2商品详情)
     */
    public void setType(Integer type) {
        this.type = type;
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