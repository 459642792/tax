package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.VendorApiLogin;
import com.blueteam.base.util.Validator;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.BillingInfo;
import com.blueteam.wineshop.mapper.BillingInfoMapper;
import com.blueteam.wineshop.service.BillingInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 结算信息
 *
 * @author xiaojiang 2017年3月1日
 * @version 1.0
 * @since 1.0 2017年3月1日
 */
@Controller
@RequestMapping("/billingInfo")
@VendorApiLogin
public class BillingInfoController extends BaseController {
    @Autowired
    BillingInfoService billingInfoService;
    @Autowired
    BillingInfoMapper billingInfoMapper;

    private static Logger logger = LoggerFactory.getLogger(BillingInfoController.class);

    /**
     * 保存商户结算信息
     *
     * @param billingInfoType      结算类型
     * @param name                 姓名
     * @param identityCard         省份证号码
     * @param alipayCardBank       开户卡号或支付宝号
     * @param bankName             开户行名称
     * @param accountOpeningCity   开户行城市
     * @param accountOpeningBranch 开户行支行
     * @return BaseResult
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    @RequestMapping(value = "/saveBillingInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult saveBillingInfo(@RequestParam("billingInfoType") String billingInfoType,
                                      @RequestParam("name") String name, @RequestParam("identityCard") String identityCard,
                                      @RequestParam("alipayCardBank") String alipayCardBank,
                                      @RequestParam(value = "bankName", required = false) String bankName,
                                      @RequestParam(value = "accountOpeningCity", required = false) String accountOpeningCity,
                                      @RequestParam(value = "accountOpeningBranch", required = false) String accountOpeningBranch, HttpServletResponse response) {
        if (null == billingInfoType || "".equals(billingInfoType)) {
            return ApiResult.error("类型参数为null");
        }
        if (null == name || "".equals(name)) {
            return ApiResult.error("姓名参数为null");
        }
        if (!Validator.decideIdcard(identityCard)) {
            return ApiResult.error("身份证参数错误");
        }
        BillingInfo billingInfo = new BillingInfo();
        billingInfo.setUpdateBy(this.getUserName());
        billingInfo.setCreateBy(this.getUserName());
        billingInfo.setVendorInfoId(this.getIdentify().getExtendId());
//		billingInfo.setUpdateBy("system");
//		billingInfo.setCreateBy("system");
//		billingInfo.setVendorInfoId(17);
        if ("1".equals(billingInfoType)) {
            if (null == alipayCardBank || "".equals(alipayCardBank)) {
                return ApiResult.error("银行卡参数为null");
            }
            if (null == bankName || "".equals(bankName)) {
                return ApiResult.error("所属银行参数为null");
            }
            if (null == accountOpeningCity || "".equals(accountOpeningCity)) {
                return ApiResult.error("开户城市参数为null");
            }
            if (null == accountOpeningBranch || "".equals(accountOpeningBranch)) {
                return ApiResult.error("开户支行参数为null");
            }
            billingInfo.setBillingInfoType(Integer.parseInt(billingInfoType));
            billingInfo.setName(name);
            billingInfo.setIdentityCard(identityCard);
            billingInfo.setAlipayCardBank(alipayCardBank);
            billingInfo.setBankName(bankName);
            billingInfo.setAccountOpeningCity(accountOpeningCity);
            billingInfo.setAccountOpeningBranch(accountOpeningBranch);
            return billingInfoService.saveBillingInfo(billingInfo);
        } else {
            if (null == alipayCardBank || "".equals(alipayCardBank)) {
                return ApiResult.error("支付宝参数为null");
            }
            billingInfo.setBillingInfoType(Integer.parseInt(billingInfoType));
            billingInfo.setName(name);
            billingInfo.setIdentityCard(identityCard);
            billingInfo.setAlipayCardBank(alipayCardBank);
            return billingInfoService.saveBillingInfo(billingInfo);
        }
    }

    /**
     * 获取当前商户结算信息资料
     *
     * @return
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    @RequestMapping(value = "/getBillingInfo", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getBillingInfo(HttpServletResponse response) {
        return billingInfoService.getBillingInfo(this.getIdentify().getExtendId());
    }

    /**
     * 修改商户结算信息
     *
     * @param billingInfoType      结算类型
     * @param name                 姓名
     * @param identityCard         省份证号码
     * @param alipayCardBank       开户卡号或支付宝号
     * @param bankName             开户行名称
     * @param accountOpeningCity   开户行城市
     * @param accountOpeningBranch 开户行支行
     * @return BaseResult
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    @RequestMapping(value = "/updateBillingInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateBillingInfo(@RequestParam("id") String id, @RequestParam("billingInfoType") String billingInfoType,
                                        @RequestParam("name") String name, @RequestParam("identityCard") String identityCard,
                                        @RequestParam("alipayCardBank") String alipayCardBank,
                                        @RequestParam(value = "bankName", required = false) String bankName,
                                        @RequestParam(value = "accountOpeningCity", required = false) String accountOpeningCity,
                                        @RequestParam(value = "accountOpeningBranch", required = false) String accountOpeningBranch, HttpServletResponse response) {
        if (null == id || "".equals(id)) {
            return ApiResult.error("id参数为null");
        }
        if (null == billingInfoType || "".equals(billingInfoType)) {
            return ApiResult.error("类型参数为null");
        }
        if (null == name || "".equals(name)) {
            return ApiResult.error("姓名参数为null");
        }
        if (null == identityCard || "".equals(identityCard)) {
            return ApiResult.error("省份证参数为null");
        }
        BillingInfo billingInfo = billingInfoMapper.getBillingInfo(Integer.parseInt(id));
        billingInfo.setUpdateBy(this.getUserName());
        billingInfo.setCreateBy(this.getUserName());
        billingInfo.setVendorInfoId(this.getIdentify().getExtendId());
        if ("1".equals(billingInfoType)) {
            if (null == bankName || "".equals(bankName)) {
                return ApiResult.error("所属银行参数为null");
            }
            if (null == accountOpeningCity || "".equals(accountOpeningCity)) {
                return ApiResult.error("开户城市参数为null");
            }
            if (null == accountOpeningBranch || "".equals(accountOpeningBranch)) {
                return ApiResult.error("开户支行参数为null");
            }
            billingInfo.setBillingInfoType(Integer.parseInt(billingInfoType));
            billingInfo.setName(name);
            billingInfo.setIdentityCard(identityCard);
            billingInfo.setAlipayCardBank(alipayCardBank);
            billingInfo.setBankName(bankName);
            billingInfo.setAccountOpeningCity(accountOpeningCity);
            billingInfo.setAccountOpeningBranch(accountOpeningBranch);
            return billingInfoService.updateBillingInfo(billingInfo);
        } else {
            if (null == alipayCardBank || "".equals(alipayCardBank)) {
                return ApiResult.error("支付宝参数为null");
            }
            billingInfo.setBillingInfoType(Integer.parseInt(billingInfoType));
            billingInfo.setName(name);
            billingInfo.setIdentityCard(identityCard);
            billingInfo.setAlipayCardBank(alipayCardBank);
            return billingInfoService.updateBillingInfo(billingInfo);
        }
    }

    /**
     * 获取消费记录
     *
     * @param response
     * @return
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    @RequestMapping(value = "/listOrderInfo", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listOrderInfo(@RequestParam("pageSize") String pageSize, @RequestParam("pageIndex") String pageIndex, HttpServletResponse response) {
        int pageSizes = Integer.parseInt(pageSize) < 2 ? 1 : Integer.parseInt(pageSize);
        int pageIndexs = Integer.parseInt(pageIndex) < 2 ? 1 : Integer.parseInt(pageIndex);
        return billingInfoService.listOrderInfo(this.getCurrentUserID(), pageIndexs, pageSizes);
    }

    /**
     * 获取最新的6条消费记录
     *
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listOrderInfoSimple", method = RequestMethod.GET)
    @VendorApiLogin
    public BaseResult listOrderInfoSimple(HttpServletResponse response) {
        return billingInfoService.listOrderInfoSimple(this.getCurrentUserID());
    }
}
