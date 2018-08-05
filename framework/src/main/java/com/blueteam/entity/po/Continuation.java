package com.blueteam.entity.po;

/**
 * 关联数据实体数据结构
 *
 * @author Marx
 */
public class Continuation {

    /**
     * 商圈头条状态
     */
    public static final String Continuation_type = "HandState";//商圈头条状态

    /**
     * 商家链接
     */
    public static final String vendor_link_type = "vendorlink";

    /**
     * 主键Id
     */
    public Integer Id;

    /**
     * 关联外键Id
     */
    public Integer ForeignKey;

    /**
     * 类型状态
     */
    public String Type;

    /**
     * 拓展字段1
     */
    public String ExpandText1;

    /**
     * 拓展字段2
     */
    public String ExpandText2;

    /**
     * 创建日期
     */
    private String CreateDate;

    /**
     * 创建人
     */
    private String CreateBy;

    /**
     * 修改日期
     */
    private String UpdateDate;

    /**
     * 修改人
     */
    private String UpdateBy;

    /**
     * 状态
     */
    private String Status;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getForeignKey() {
        return ForeignKey;
    }

    public void setForeignKey(Integer foreignKey) {
        ForeignKey = foreignKey;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getExpandText1() {
        return ExpandText1;
    }

    public void setExpandText1(String expandText1) {
        ExpandText1 = expandText1;
    }

    public String getExpandText2() {
        return ExpandText2;
    }

    public void setExpandText2(String expandText2) {
        ExpandText2 = expandText2;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getUpdateBy() {
        return UpdateBy;
    }

    public void setUpdateBy(String updateBy) {
        UpdateBy = updateBy;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public static String getContinuationType() {
        return Continuation_type;
    }
}
