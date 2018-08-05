package com.blueteam.entity.po;
/******************************************************************
 ** 类    名：Currency_recordEntity
 ** 描    述：
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-04-19 15:02:50
 ******************************************************************/

import java.util.Date;

/**
 * (currency_record)
 * 酒币明细表
 *
 * @author xiaojiang
 * @version 1.0.0 2017-04-19
 */
public class CurrencyRecord implements java.io.Serializable {
    /**
     * 增加
     */
    public static final int STATUS_INCREASE = 1;
    /**
     * 减少
     */
    public static final int STATUS_REDUCE = 0;


    /**
     * 版本号
     */
    private static final long serialVersionUID = -7799266816590669411L;

    /**  */
    private Integer id;

    /**
     * 订单id(增加 orderinfo 表 减少 goods_cash_record)
     */
    private Integer numberId;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 商家id
     */
    private Integer vendorinfoId;

    /**
     * 用户id
     */
    private Integer userinfoId;

    /**
     * 状态(0 减少，1新增)
     */
    private Integer status;

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
     * 获取订单id(增加 orderinfo 表 减少 goods_cash_record)
     *
     * @return 订单id(增加 orderinfo 表 减少 goods_cash_record)
     */
    public Integer getNumberId() {
        return this.numberId;
    }

    /**
     * 设置订单id(增加 orderinfo 表 减少 goods_cash_record)
     *
     * @param number_id 订单id(增加 orderinfo 表 减少 goods_cash_record)
     */
    public void setNumberId(Integer numberId) {
        this.numberId = numberId;
    }

    /**
     * 获取数量
     *
     * @return 数量
     */
    public Integer getAmount() {
        return this.amount;
    }

    /**
     * 设置数量
     *
     * @param amount 数量
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * 获取商家id
     *
     * @return 商家id
     */
    public Integer getVendorinfoId() {
        return this.vendorinfoId;
    }

    public Integer getUserinfoId() {
        return userinfoId;
    }

    public void setUserinfoId(Integer userinfoId) {
        this.userinfoId = userinfoId;
    }

    /**
     * 设置商家id
     *
     * @param vendorinfo_id 商家id
     */
    public void setVendorinfoId(Integer vendorinfoId) {
        this.vendorinfoId = vendorinfoId;
    }

    /**
     * 获取状态(0 减少，1新增)
     *
     * @return 状态(0 减少
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置状态(0 减少，1新增)
     *
     * @param status 状态(0 减少，1新增)
     */
    public void setStatus(Integer status) {
        this.status = status;
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
    public void setUpdateBy(String update_by) {
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