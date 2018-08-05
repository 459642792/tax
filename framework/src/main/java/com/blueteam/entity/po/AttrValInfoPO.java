/******************************************************************
 ** 类    名：TdGAttrVal
 ** 描    述：商品属性值参数表(自定义属性值属性不需要关联该表)
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-10-18 17:49:12
 ******************************************************************/

package com.blueteam.entity.po;

import java.util.Date;

/**
 * 商品属性值参数表(自定义属性值属性不需要 关联该表)(TD_G_ATTR_VAL)
 *
 * @author xiaojiang
 * @version 1.0.0 2017-10-18
 */
public class AttrValInfoPO implements java.io.Serializable {
    //属性值可编辑删除
    public static final Integer OPERATE_TAG_VALID = 1;
    //属性值不可操作
    public static final Integer OPERATE_TAG_INVALID = 0;

    /**
     * 版本号
     */
    private static final long serialVersionUID = -1944397626916362336L;

    /**
     * 属性编码
     */
    private String attrCode;

    /**
     * 属性值编码：属性编码拼接VALUE_INDEX字段，保证唯一性
     */
    private String attrValueCode;

    /**
     * 属性值编码后四位(要求>1000)，用于拼接生成新增属性值编码，每次新增属性值，获取当前属性下的所有属性值编码后四位VALUE_INDEX，取最大值加一作为当前属性值编码后四位
     */
    private Integer valueIndex;

    /**
     * 属性值
     */
    private String attrValueName;

    /**
     * 操作类型：0-不可编辑 1-可修改可删除 2-只可修改 3-只可删除
     */
    private Integer operateTag;

    /**
     * 属性值展示顺序，值越小展示优先级越高
     */
    private Integer attrValueShowOrder;

    /**
     * 创建员工ID
     */
    private Integer createStaffId;

    /**
     * 最近一次更新的员工ID
     */
    private Integer updateStaffId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

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
     * 获取属性值编码：属性编码拼接VALUE_INDEX字段，保证唯一性
     *
     * @return 属性值编码：属性编码拼接VALUE_INDEX字段
     */
    public String getAttrValueCode() {
        return this.attrValueCode;
    }

    /**
     * 设置属性值编码：属性编码拼接VALUE_INDEX字段，保证唯一性
     *
     * @param attrValueCode 属性值编码：属性编码拼接VALUE_INDEX字段，保证唯一性
     */
    public void setAttrValueCode(String attrValueCode) {
        this.attrValueCode = attrValueCode;
    }

    /**
     * 获取属性值编码后四位(要求>1000)，用于拼接生成新增属性值编码，每次新增属性值，获取当前属性下的所有属性值编码后四位VALUE_INDEX，取最大值加一作为当前属性值编码后四位
     *
     * @return 属性值编码后四位(要求>1000)
     */
    public Integer getValueIndex() {
        return this.valueIndex;
    }

    /**
     * 设置属性值编码后四位(要求>1000)，用于拼接生成新增属性值编码，每次新增属性值，获取当前属性下的所有属性值编码后四位VALUE_INDEX，取最大值加一作为当前属性值编码后四位
     *
     * @param valueIndex 属性值编码后四位(要求>1000)，用于拼接生成新增属性值编码，每次新增属性值，获取当前属性下的所有属性值编码后四位VALUE_INDEX，取最大值加一作为当前属性值编码后四位
     */
    public void setValueIndex(Integer valueIndex) {
        this.valueIndex = valueIndex;
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
     * 获取操作类型：0-不可编辑 1-可修改可删除 2-只可修改 3-只可删除
     *
     * @return 操作类型
     */
    public Integer getOperateTag() {
        return this.operateTag;
    }

    /**
     * 设置操作类型：0-不可编辑 1-可修改可删除 2-只可修改 3-只可删除
     *
     * @param operateTag 操作类型：0-不可编辑 1-可修改可删除 2-只可修改 3-只可删除
     */
    public void setOperateTag(Integer operateTag) {
        this.operateTag = operateTag;
    }

    /**
     * 获取属性值展示顺序，值越小展示优先级越高
     *
     * @return 属性值展示顺序
     */
    public Integer getAttrValueShowOrder() {
        return this.attrValueShowOrder;
    }

    /**
     * 设置属性值展示顺序，值越小展示优先级越高
     *
     * @param attrValueShowOrder 属性值展示顺序，值越小展示优先级越高
     */
    public void setAttrValueShowOrder(Integer attrValueShowOrder) {
        this.attrValueShowOrder = attrValueShowOrder;
    }

    /**
     * 获取创建员工ID
     *
     * @return 创建员工ID
     */
    public Integer getCreateStaffId() {
        return this.createStaffId;
    }

    /**
     * 设置创建员工ID
     *
     * @param createStaffId 创建员工ID
     */
    public void setCreateStaffId(Integer createStaffId) {
        this.createStaffId = createStaffId;
    }

    /**
     * 获取最近一次更新的员工ID
     *
     * @return 最近一次更新的员工ID
     */
    public Integer getUpdateStaffId() {
        return this.updateStaffId;
    }

    /**
     * 设置最近一次更新的员工ID
     *
     * @param updateStaffId 最近一次更新的员工ID
     */
    public void setUpdateStaffId(Integer updateStaffId) {
        this.updateStaffId = updateStaffId;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}