package com.blueteam.entity.po;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Marx
 * <p>
 * CouponInfo.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public class CouponInfo {
    /**
     * 优惠券商家停止发放
     */
    public static final int STATUS_STOP = 90;
    /**
     * 优惠券未使用
     */
    public static final String COST_STATUS_UNUSED = "N";
    /**
     * 优惠券已使用
     */
    public static final String COST_STATUS_USED = "Y";

    //����ID
    private Integer Id;
    //�������
    private String Type;

    private String Title;

    private String CostStatus;

    private List<CouponRecord> couponRecords;

    private Integer ExpandId;

    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getExpandId() {
        return ExpandId;
    }

    public void setExpandId(Integer expandId) {
        ExpandId = expandId;
    }

    /**
     * 标致是否已经领取
     */
    private String State;

    /***
     * 查看优惠券是否已经领取完
     *
     * @return
     */
    private String EndState;

    public String getEndState() {
        return EndState;
    }

    public void setEndState(String endState) {
        EndState = endState;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    /**
     * 扩展字段：带动消费
     */
    private BigDecimal driveMoney;

    /**
     * 扩展字段：已使用张数
     */
    private int usedCont;

    private String Addr;

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public int getUsedCont() {
        return usedCont;
    }

    public void setUsedCont(int usedCont) {
        this.usedCont = usedCont;
    }

    public BigDecimal getDriveMoney() {
        return driveMoney == null ? BigDecimal.ZERO : driveMoney;
    }

    public void setDriveMoney(BigDecimal driveMoney) {
        this.driveMoney = driveMoney;
    }

    public List<CouponRecord> getCouponRecords() {
        return couponRecords;
    }

    public void setCouponRecords(List<CouponRecord> couponRecords) {
        this.couponRecords = couponRecords;
    }

    public String getCostStatus() {
        return CostStatus;
    }

    public void setCostStatus(String costStatus) {
        CostStatus = costStatus;
    }

    private Integer UserId;

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    //����ƴ��
    private BigDecimal Money;
    //���и���code
    private String BeginTime;
    //����code
    private String EndTime;
    //���б�־
    private Integer Count;
    //����ʱ��
    private String Detail;
    //������
    private String Condition;
    //�޸�ʱ��
    private BigDecimal CostLimitMoney;
    //�޸���
    private String CityCode;

    private Integer ForeignKey;

    private String CreateBy;

    private String CreateDate;

    private String UpdateDate;

    private String UpdateBy;

    private String StatusStr;

    private VendorInfo vendorInfo;

    private Integer Status;


    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public VendorInfo getVendorInfo() {
        return vendorInfo;
    }

    public void setVendorInfo(VendorInfo vendorInfo) {
        this.vendorInfo = vendorInfo;
    }

    /**
     * 商家设置优惠券状态
     *
     * @param date
     * @throws Exception
     */
    public void setCouponStatus(Date date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        boolean b1 = sdf.parse(sdf.format(date)).getTime() < sdf.parse(getBeginTime()).getTime();
        boolean b2 = sdf.parse(sdf.format(date)).getTime() <= sdf.parse(getEndTime()).getTime();

        if (null == getStatus() || 0 == getStatus()) {
            if (b1) {
                setStatusStr("未开始");
            } else if (b2) {
                setStatusStr("进行中");
            } else {
                setStatusStr("已结束");
            }
        } else {
            if (getStatus() == STATUS_STOP) {
                setStatusStr("已停止");
            }
        }

    }

    public String getStatusStr() {
        return StatusStr;
    }

    public void setStatusStr(String statusStr) {
        StatusStr = statusStr;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public BigDecimal getMoney() {
        return Money;
    }

    public void setMoney(BigDecimal money) {
        Money = money;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public BigDecimal getCostLimitMoney() {
        return CostLimitMoney;
    }

    public void setCostLimitMoney(BigDecimal costLimitMoney) {
        CostLimitMoney = costLimitMoney;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public Integer getForeignKey() {
        return ForeignKey;
    }

    public void setForeignKey(Integer foreignKey) {
        ForeignKey = foreignKey;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
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
}
