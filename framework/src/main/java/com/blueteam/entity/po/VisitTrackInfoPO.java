/******************************************************************
 ** 类    名：VisitTrackInfoPO
 ** 描    述：
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-10-19 13:58:03
 ******************************************************************/

package com.blueteam.entity.po;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

/**
 * (VISITTRACKINFO )
 *
 * @author xiaojiang
 * @version 1.0.0 2017-10-19
 */
public class VisitTrackInfoPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 14246362832383701L;

    /**  */
    private Integer id;

    /**  */
    private Integer foreignid;

    /**  */
    private Integer userId;

    /**  */
    private Integer visittype;

    /**  */
    private Date visittime;

    /**  */
    private String useragent;

    /**  */
    private String clientaddress;

    /**
     * 获取
     *
     * @return
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getForeignid() {
        return this.foreignid;
    }

    /**
     * 设置
     *
     * @param foreignid
     */
    public void setForeignid(Integer foreignid) {
        this.foreignid = foreignid;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getUserId() {
        return this.userId;
    }

    /**
     * 设置
     *
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getVisittype() {
        return this.visittype;
    }

    /**
     * 设置
     *
     * @param visittype
     */
    public void setVisittype(Integer visittype) {
        this.visittype = visittype;
    }

    /**
     * 获取
     *
     * @return
     */
    public Date getVisittime() {
        return this.visittime;
    }

    /**
     * 设置
     *
     * @param visittime
     */
    public void setVisittime(Date visittime) {
        this.visittime = visittime;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getUseragent() {
        return this.useragent;
    }

    /**
     * 设置
     *
     * @param useragent
     */
    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getClientaddress() {
        return this.clientaddress;
    }

    /**
     * 设置
     *
     * @param clientaddress
     */
    public void setClientaddress(String clientaddress) {
        this.clientaddress = clientaddress;
    }
}