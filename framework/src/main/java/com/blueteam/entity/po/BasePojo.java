package com.blueteam.entity.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by libra on 2017/5/15.
 * <p>
 * 基础Pojo 字段
 */
public class BasePojo implements Serializable {
    /**
     * ID
     */
    private long id;
    /**
     * 创建人
     */
    private long createBy;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改人
     */
    private long lastUpdateBy;

    /**
     * 最后修改时间
     */
    private Date lastUpdateDate;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public long getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
