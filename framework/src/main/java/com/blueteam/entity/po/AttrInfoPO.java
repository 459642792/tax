/******************************************************************
 ** 类    名：AttrInfoPO
 ** 描    述：商品属性参数表
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-10-18 17:49:12
 ******************************************************************/

package com.blueteam.entity.po;

/**
 * 商品属性参数表(TD_G_ATTR)
 *
 * @author xiaojiang
 * @version 1.0.0 2017-10-18
 */
public class AttrInfoPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -2980137242608787599L;

    /**
     * 属性编码
     */
    private String attrCode;

    /**
     * 父属性编码, 999代表酒水类商品根属性
     */
    private String parentAttrCode;

    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 属性值类型：0-select选择 1-input自定义输入
     */
    private Integer attrType;

    /**
     * 属性商品类型ID：关联TD_G_GOODS_TYPE表GOODS_TYPE_ID
     */
    private Integer attrGoodsType;

    /**
     * 属性是否必选，0-非必选 1-必选
     */
    private Integer necessaryTag;

    /**
     * 后台管理属性列表页展示标记，0-不展示 1-展示
     */
    private Integer adminShowTag;


    /**
     * 后台管理属性展示顺序，值越小展示优先级越高
     */
    private Integer adminShowOrder;

    /**
     * 属性操作类型 0-不可添加 1-可添加
     */
    private Integer operateTag;

    /**
     * 第一套商品属性应用端展示标记：0-不显示 1-列表页详情页都显示 2-仅列表页展示 3-仅详情页展示
     */
    private Integer firstShowTag;

    /**
     * 第一套商品属性应用端展示顺序，值越小展示优先级越高
     */
    private Integer firstShowOrder;

    /**
     * 第二套商品属性应用端展示标记：0-不显示 1-列表页详情页都显示 2-仅列表页展示 3-仅详情页展示
     */
    private Integer secondShowTag;

    /**
     * 第二套商品属性应用端展示顺序，值越小展示优先级越高
     */
    private Integer secondShowOrder;

    /**
     * 第三套商品属性应用端展示标记：0-不显示 1-列表页详情页都显示 2-仅列表页展示 3-仅详情页展示
     */
    private Integer thirdShowTag;

    /**
     * 第三套商品属性应用端展示顺序，值越小展示优先级越高
     */
    private Integer thirdShowOrder;

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getParentAttrCode() {
        return parentAttrCode;
    }

    public void setParentAttrCode(String parentAttrCode) {
        this.parentAttrCode = parentAttrCode;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public Integer getAttrType() {
        return attrType;
    }

    public void setAttrType(Integer attrType) {
        this.attrType = attrType;
    }

    public Integer getAttrGoodsType() {
        return attrGoodsType;
    }

    public void setAttrGoodsType(Integer attrGoodsType) {
        this.attrGoodsType = attrGoodsType;
    }

    public Integer getNecessaryTag() {
        return necessaryTag;
    }

    public void setNecessaryTag(Integer necessaryTag) {
        this.necessaryTag = necessaryTag;
    }

    public Integer getAdminShowOrder() {
        return adminShowOrder;
    }

    public void setAdminShowOrder(Integer adminShowOrder) {
        this.adminShowOrder = adminShowOrder;
    }

    public Integer getOperateTag() {
        return operateTag;
    }

    public void setOperateTag(Integer operateTag) {
        this.operateTag = operateTag;
    }

    public Integer getFirstShowTag() {
        return firstShowTag;
    }

    public void setFirstShowTag(Integer firstShowTag) {
        this.firstShowTag = firstShowTag;
    }

    public Integer getFirstShowOrder() {
        return firstShowOrder;
    }

    public void setFirstShowOrder(Integer firstShowOrder) {
        this.firstShowOrder = firstShowOrder;
    }

    public Integer getSecondShowTag() {
        return secondShowTag;
    }

    public void setSecondShowTag(Integer secondShowTag) {
        this.secondShowTag = secondShowTag;
    }

    public Integer getSecondShowOrder() {
        return secondShowOrder;
    }

    public void setSecondShowOrder(Integer secondShowOrder) {
        this.secondShowOrder = secondShowOrder;
    }

    public Integer getThirdShowTag() {
        return thirdShowTag;
    }

    public void setThirdShowTag(Integer thirdShowTag) {
        this.thirdShowTag = thirdShowTag;
    }

    public Integer getThirdShowOrder() {
        return thirdShowOrder;
    }

    public void setThirdShowOrder(Integer thirdShowOrder) {
        this.thirdShowOrder = thirdShowOrder;
    }

    public Integer getAdminShowTag() {
        return adminShowTag;
    }

    public void setAdminShowTag(Integer adminShowTag) {
        this.adminShowTag = adminShowTag;
    }
}