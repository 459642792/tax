package com.blueteam.wineshop.controller.vendor;


import com.alibaba.fastjson.JSON;
import com.blueteam.base.constant.VendorApiLogin;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.VendorBankPO;
import com.blueteam.entity.po.VendorInfo;
import com.blueteam.entity.vo.VendorBankDetailVO;
import com.blueteam.wineshop.controller.BaseController;
import com.blueteam.wineshop.service.VendorBankService;
import com.blueteam.wineshop.service.VendorInfoService;
import com.jfinal.weixin.sdk.utils.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/vendor/wallet")
@VendorApiLogin
public class VendorWalletController extends BaseController{
    Logger logger= LoggerFactory.getLogger(VendorWalletController.class);

    @Resource
    private VendorBankService vendorBankService;

    @Resource
    private VendorInfoService vendorInfoService;

    /**
     * 验证银行卡号
     * @param cardNo
     * @return
     */
    @RequestMapping("/validateBankCard")
    public BaseResult validateBankCard(String cardNo){
        String URL="https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo="+cardNo+"&cardBinCheck=true";
        String res=HttpUtils.get(URL);
        return ApiResult.success(JSON.parseObject(res));
    }


    /**
     * 添加银行账户
     * @return
     * /vendor/wallet/addAccountInfo
     */
    @RequestMapping(value = "/addAccountInfo",method = RequestMethod.POST)
    public BaseResult addAccountInfo(@RequestBody VendorBankPO po){
        po.setVendorId(this.getIdentify().getExtendId());
        int res=vendorBankService.add(po);
        if (res>0)
        return BaseResult.success();
        return BaseResult.error("保存失败");
    }

    /**
     * 获取绑定账户
     * @return
     * /vendor/wallet/getAccountInfo
     */
    @RequestMapping(value = "/getAccountInfo",method = RequestMethod.GET)
    public BaseResult getAccountInfo(){
        VendorInfo vendorInfo= vendorInfoService.getNewVendorById(this.getIdentify().getExtendId());
        VendorBankPO po=vendorBankService.getInfoByVendorId(this.getIdentify().getExtendId());
        VendorBankDetailVO vo=new VendorBankDetailVO();
        vo.setName(vendorInfo.getLegalPerson());
        vo.setIdCard(dealIDCard(vendorInfo.getLegalPersonIdCard()));
        vo.setBankInfo(po);
        return ApiResult.success(vo);
    }

    private String dealIDCard(String idCard){

        if (StringUtils.isNotBlank(idCard)){
            if (idCard.length()>15){
                String res="";
                for (int i=0;i<idCard.length();i++){
                    if (i<4||i>13) {
                        res += idCard.charAt(i);
                    }else {
                        res += "*";
                    }
                }
                return res;
            }
        }
        return idCard;
    }

    /**
     * 修改绑定账户
     * @return
     * /vendor/wallet/updateAccountInfo
     * 请求参数：{"id":1}
     */
    @RequestMapping(value = "/updateAccountInfo",method = RequestMethod.POST)
    public BaseResult updateAccountInfo(@RequestBody VendorBankPO po){
        po.setVendorId(this.getIdentify().getExtendId());
        int res=vendorBankService.update(po);
        if (res>0)
            return BaseResult.success();
        return BaseResult.error("修改失败");
    }

    /**
     *删除绑定账户
     * @return
     * /vendor/wallet/delAccountInfo
     * 请求参数：{"id":1}
     */
    @RequestMapping(value = "/delAccountInfo",method = RequestMethod.POST)
    public BaseResult delAccountInfo(@RequestBody VendorBankPO po){
        po.setVendorId(this.getIdentify().getExtendId());
        int res=vendorBankService.del(po);
        if (res>0)
            return BaseResult.success();
        return BaseResult.error("删除失败");
    }

}
