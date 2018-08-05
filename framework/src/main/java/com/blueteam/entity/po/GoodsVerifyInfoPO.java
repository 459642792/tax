/******************************************************************
 ** 类    名：GoodsVerifyInfoPO
 ** 描    述：商品审核表
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-10-18 17:49:12
 ******************************************************************/

package com.blueteam.entity.po;

import com.blueteam.base.lang.RMap;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品审核表 (TF_B_GOODS_VERIFY)
 *
 * @author xiaojiang
 * @version 1.0.0 2017-10-18
 */
public class GoodsVerifyInfoPO implements java.io.Serializable {

    /**
     * 审核状态-未审核
     */
    public static final int VERIFY_STATE_TODO = 0;
    /**
     * 审核状态-失败
     */
    public static final int VERIFY_STATE_FAIL = 1;
    /**
     * 审核状态-成功
     */
    public static final int VERIFY_STATE_SUCCESS = 2;

    //审核商品状态-上架
    public static final int VERIFY_GOODS__LISTING = 1;
    //审核商品状态-下架
    public static final int VERIFY_GOODS_DELISTING = 0;

    //审核失败原因：0-条形码输入错误
    public static final int VERIFY_REFUSE_BARCODE = 0;
    //审核失败原因：1-图片违规
    public static final int VERIFY_REFUSE_PHOTO = 1;
    //审核失败原因：2-名称不合法
    public static final int VERIFY_REFUSE_NAME = 2;
    //审核失败原因：3-其他
    public static final int VERIFY_REFUSE_OTHER = 3;


    public static final List VERIFY_REFUSE_CODE_LIST = Arrays.asList(VERIFY_REFUSE_BARCODE, VERIFY_REFUSE_PHOTO, VERIFY_REFUSE_NAME, VERIFY_REFUSE_OTHER);

    public static final Map VERIFY_REFUSE_REASON_MAP = RMap.asMap(VERIFY_REFUSE_BARCODE, "条形码输入错误", VERIFY_REFUSE_PHOTO, "图片违规", VERIFY_REFUSE_NAME, "名称不合法", VERIFY_REFUSE_OTHER, "其他");
    /**
     * 版本号
     */
    private static final long serialVersionUID = 7680168471042789877L;

    /**
     * 审核编码
     */
    private Integer verifyId;

    /**
     * 审核条形码
     */
    private String verifyBarCode;

    /**
     * 审核商品名称
     */
    private String verifyGoodsName;

    /**
     * 审核商品状态：0-审核通过不上架 1-审核通过上架
     */
    private Integer verifyGoodsState;

    /**
     * 审核商品照片
     */
    private String verifyGoodsPhoto;

    /**
     * 发起商品审核的商家id
     */
    private Integer vendorId;

    /**
     * 商家销售价格
     */
    private Integer verifySalePrice;

    /**
     * 审核状态：0-未审核 1-审核不通过 2-审核通过
     */
    private Integer verifyState;

    /**
     * 审核不通过编码：0-条形码输入错误 1-图片违规 2-名称不合法 3-其他
     */
    private Integer verifyRefuseCode;

    /**
     * 审核不通过原因
     */
    private String verifyRefuseReason;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 冗余字段 用于前段传递参数
     */
    private Double price;


    public Integer getVerifyRefuseCode() {
        return verifyRefuseCode;
    }

    public void setVerifyRefuseCode(Integer verifyRefuseCode) {
        this.verifyRefuseCode = verifyRefuseCode;
    }

    public Integer getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(Integer verifyId) {
        this.verifyId = verifyId;
    }

    /**
     * 获取审核条形码
     *
     * @return 审核条形码
     */
    public String getVerifyBarCode() {
        return this.verifyBarCode;
    }

    /**
     * 设置审核条形码
     *
     * @param verifyBarCode 审核条形码
     */
    public void setVerifyBarCode(String verifyBarCode) {
        this.verifyBarCode = verifyBarCode;
    }

    /**
     * 获取审核商品名称
     *
     * @return 审核商品名称
     */
    public String getVerifyGoodsName() {
        return this.verifyGoodsName;
    }

    /**
     * 设置审核商品名称
     *
     * @param verifyGoodsName 审核商品名称
     */
    public void setVerifyGoodsName(String verifyGoodsName) {
        this.verifyGoodsName = verifyGoodsName;
    }

    public Integer getVerifyGoodsState() {
        return verifyGoodsState;
    }

    public void setVerifyGoodsState(Integer verifyGoodsState) {
        this.verifyGoodsState = verifyGoodsState;
    }

    /**
     * 获取审核商品照片
     *
     * @return 审核商品照片
     */
    public String getVerifyGoodsPhoto() {
        return this.verifyGoodsPhoto;
    }

    /**
     * 设置审核商品照片
     *
     * @param verifyGoodsPhoto 审核商品照片
     */
    public void setVerifyGoodsPhoto(String verifyGoodsPhoto) {
        this.verifyGoodsPhoto = verifyGoodsPhoto;
    }

    /**
     * 获取发起商品审核的商家id
     *
     * @return 发起商品审核的商家id
     */
    public Integer getVendorId() {
        return this.vendorId;
    }

    /**
     * 设置发起商品审核的商家id
     *
     * @param vendorId 发起商品审核的商家id
     */
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * 获取商家销售价格
     *
     * @return 商家销售价格
     */
    public Integer getVerifySalePrice() {
        return this.verifySalePrice;
    }

    /**
     * 设置商家销售价格
     *
     * @param verifySalePrice 商家销售价格
     */
    public void setVerifySalePrice(Integer verifySalePrice) {
        this.verifySalePrice = verifySalePrice;
    }

    /**
     * 获取审核状态：0-未审核 1-审核不通过 2-审核通过
     *
     * @return 审核状态
     */
    public Integer getVerifyState() {
        return this.verifyState;
    }

    /**
     * 设置审核状态：0-未审核 1-审核不通过 2-审核通过
     *
     * @param verifyState 审核状态：0-未审核 1-审核不通过 2-审核通过
     */
    public void setVerifyState(Integer verifyState) {
        this.verifyState = verifyState;
    }

    /**
     * 获取审核不通过原因
     *
     * @return 审核不通过原因
     */
    public String getVerifyRefuseReason() {
        return this.verifyRefuseReason;
    }

    /**
     * 设置审核不通过原因
     *
     * @param verifyRefuseReason 审核不通过原因
     */
    public void setVerifyRefuseReason(String verifyRefuseReason) {
        this.verifyRefuseReason = verifyRefuseReason;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}