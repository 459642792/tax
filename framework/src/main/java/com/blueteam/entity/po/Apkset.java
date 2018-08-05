package com.blueteam.entity.po;


/**
 * apk更新实体
 *
 * @author libra
 */
public class Apkset {

    /**
     * apk主键Id
     */
    private Integer apk_id;

    /**
     * apk名称
     */
    private String apk_name;

    /**
     * apk描述
     */
    private String apk_des;

    /**
     * apkurl
     */
    private String apk_url;

    /**
     * apk版本号
     */
    private String apk_version;

    /**
     * 创建人
     */
    private String createby;

    /**
     * 创建人时间
     */
    private String createdate;

    public Integer getApk_id() {
        return apk_id;
    }

    public void setApk_id(Integer apk_id) {
        this.apk_id = apk_id;
    }

    public String getApk_name() {
        return apk_name;
    }

    public void setApk_name(String apk_name) {
        this.apk_name = apk_name;
    }

    public String getApk_des() {
        return apk_des;
    }

    public void setApk_des(String apk_des) {
        this.apk_des = apk_des;
    }

    public String getApk_url() {
        return apk_url;
    }

    public void setApk_url(String apk_url) {
        this.apk_url = apk_url;
    }

    public String getApk_version() {
        return apk_version;
    }

    public void setApk_version(String apk_version) {
        this.apk_version = apk_version;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
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

    /**
     * 修改时间
     */

    private String UpdateDate;

    /**
     * 修改人
     */
    private String UpdateBy;
}
