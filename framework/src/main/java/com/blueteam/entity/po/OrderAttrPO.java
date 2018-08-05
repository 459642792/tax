
package com.blueteam.entity.po;

;

/**
 * 订单商品属性表(TF_B_ORDER_ATTR)
 *
 * @author
 * @version 1.0.0 2018-01-04
 */
public class OrderAttrPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 2576868016705458423L;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 属性id
     */
    private String attrCode;

    /**
     * 父属性id
     */
    private String parentAttrCode;

    /**
     * 属性名
     */
    private String attrName;

    /**
     * 属性类型
     */
    private Integer attrType;

    /**
     * 属性商品类型
     */
    private Integer attrGoodsType;

    /**
     * 属性必选标志  0-非必选 1-必选
     */
    private Integer necessaryTag;

    /**
     * 属性值编码
     */
    private String attrValueCode;

    /**
     * 属性值名
     */
    private String attrValueName;

    /**
     * 属性值展示名
     */
    private String attrValueShowName;

    /**
     * 属性展示标记 0-不展示 1-列表和详情都展示  2-仅列表  3-仅详情
     */
    private Integer showTag;

    /**
     * 属性展示顺序，值越小展示优先级越高
     */
    private Integer showOrder;

    /**
     * 获取订单id
     *
     * @return 订单id
     */
    public Long getOrderId() {
        return this.orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

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
     * 获取属性id
     *
     * @return 属性id
     */
    public String getAttrCode() {
        return this.attrCode;
    }

    /**
     * 设置属性id
     *
     * @param attrCode 属性id
     */
    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    /**
     * 获取父属性id
     *
     * @return 父属性id
     */
    public String getParentAttrCode() {
        return this.parentAttrCode;
    }

    /**
     * 设置父属性id
     *
     * @param parentAttrCode 父属性id
     */
    public void setParentAttrCode(String parentAttrCode) {
        this.parentAttrCode = parentAttrCode;
    }

    /**
     * 获取属性名
     *
     * @return 属性名
     */
    public String getAttrName() {
        return this.attrName;
    }

    /**
     * 设置属性名
     *
     * @param attrName 属性名
     */
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    /**
     * 获取属性类型
     *
     * @return 属性类型
     */
    public Integer getAttrType() {
        return this.attrType;
    }

    /**
     * 设置属性类型
     *
     * @param attrType 属性类型
     */
    public void setAttrType(Integer attrType) {
        this.attrType = attrType;
    }

    /**
     * 获取属性商品类型
     *
     * @return 属性商品类型
     */
    public Integer getAttrGoodsType() {
        return this.attrGoodsType;
    }

    /**
     * 设置属性商品类型
     *
     * @param attrGoodsType 属性商品类型
     */
    public void setAttrGoodsType(Integer attrGoodsType) {
        this.attrGoodsType = attrGoodsType;
    }

    /**
     * 获取属性必选标志  0-非必选 1-必选
     *
     * @return 属性必选标志  0-非必选 1-必选
     */
    public Integer getNecessaryTag() {
        return this.necessaryTag;
    }

    /**
     * 设置属性必选标志  0-非必选 1-必选
     *
     * @param necessaryTag 属性必选标志  0-非必选 1-必选
     */
    public void setNecessaryTag(Integer necessaryTag) {
        this.necessaryTag = necessaryTag;
    }

    /**
     * 获取属性值编码
     *
     * @return 属性值编码
     */
    public String getAttrValueCode() {
        return this.attrValueCode;
    }

    /**
     * 设置属性值编码
     *
     * @param attrValueCode 属性值编码
     */
    public void setAttrValueCode(String attrValueCode) {
        this.attrValueCode = attrValueCode;
    }

    /**
     * 获取属性值名
     *
     * @return 属性值名
     */
    public String getAttrValueName() {
        return this.attrValueName;
    }

    /**
     * 设置属性值名
     *
     * @param attrValueName 属性值名
     */
    public void setAttrValueName(String attrValueName) {
        this.attrValueName = attrValueName;
    }

    /**
     * 获取属性值展示名
     *
     * @return 属性值展示名
     */
    public String getAttrValueShowName() {
        return this.attrValueShowName;
    }

    /**
     * 设置属性值展示名
     *
     * @param attrValueShowName 属性值展示名
     */
    public void setAttrValueShowName(String attrValueShowName) {
        this.attrValueShowName = attrValueShowName;
    }

    /**
     * 获取属性展示标记 0-不展示 1-列表和详情都展示  2-仅列表  3-仅详情
     *
     * @return 属性展示标记 0-不展示 1-列表和详情都展示  2-仅列表  3-仅详情
     */
    public Integer getShowTag() {
        return this.showTag;
    }

    /**
     * 设置属性展示标记 0-不展示 1-列表和详情都展示  2-仅列表  3-仅详情
     *
     * @param showTag 属性展示标记 0-不展示 1-列表和详情都展示  2-仅列表  3-仅详情
     */
    public void setShowTag(Integer showTag) {
        this.showTag = showTag;
    }

    /**
     * 获取属性展示顺序，值越小展示优先级越高
     *
     * @return 属性展示顺序
     */
    public Integer getShowOrder() {
        return this.showOrder;
    }

    /**
     * 设置属性展示顺序，值越小展示优先级越高
     *
     * @param showOrder 属性展示顺序，值越小展示优先级越高
     */
    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }
}