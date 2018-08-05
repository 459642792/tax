package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.lang.RMap;
import com.blueteam.base.util.ExceptionUtil;
import com.blueteam.base.util.FieldValidateUtil;
import com.blueteam.base.util.StringUtil;
import com.blueteam.entity.dto.*;
import com.blueteam.entity.po.GoodsVerifyInfoPO;
import com.blueteam.wineshop.service.GoodsVerifyService;
import com.blueteam.wineshop.service.MessageSubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.blueteam.entity.po.GoodsVerifyInfoPO.*;

/**
 * 商品审核
 * Created by  NastyNas on 2017/11/8.
 */
@Controller
@RequestMapping("/goodsVerify")
@AdminApiLogin
public class GoodsVerifyController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsVerifyController.class);

    @Autowired
    GoodsVerifyService goodsVerifyService;
    @Autowired
    MessageSubService messageSubService;
    /**
     * 跳转页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listGoodsVerify", method = RequestMethod.GET)
    public ModelAndView listGoodsVerify() throws Exception {
        ModelAndView mav = new ModelAndView("goods/verify/goods_verify_list");
        return mav;
    }

    /**
     * 待审核列表页展示
     *
     * @param goodsVerifySearchDTO
     * @return
     */
    @RequestMapping(value = "/listVerify", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult listVerify(GoodsVerifySearchDTO goodsVerifySearchDTO) {
        PageResult<List<Map>> verifyVOList = goodsVerifyService.listVerifyInfo(goodsVerifySearchDTO);
        return ApiResult.success(verifyVOList.getList(), verifyVOList.getCount());
    }


    /**
     * 编辑审核信息，审核条形码对应商家审核信息列表
     *
     * @param vendorVerifySearchDTO
     * @return
     */
    @RequestMapping(value = "/listVendorVerify", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult listVendorVerify(VendorVerifySearchDTO vendorVerifySearchDTO) {
        PageResult<List<Map>> vendorVerifyList = goodsVerifyService.listVendorVerifyInfo(vendorVerifySearchDTO);
        return ApiResult.success(vendorVerifyList.getList(), vendorVerifyList.getCount());
    }

    /**
     * 审核不通过
     *
     * @param verifyRefuseDTO
     * @return
     */
    @RequestMapping(value = "/verifyRefuse", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult verifyRefuse(VerifyRefuseDTO verifyRefuseDTO) {
        //请求完整性校验
        FieldValidateUtil.validate(verifyRefuseDTO);
        //入库更新参数
        GoodsVerifyInfoPO verifyPO = new GoodsVerifyInfoPO();
        verifyPO.setVerifyBarCode(verifyRefuseDTO.getVerifyBarCode());
        verifyPO.setVerifyState(VERIFY_STATE_FAIL);
        verifyPO.setVerifyRefuseCode(obtainRefuseCode(verifyRefuseDTO));
        verifyPO.setVerifyRefuseReason(obtainRefuseReason(verifyRefuseDTO));
        try {
            //发送消息
            messageSubService.sendVendormessage(4,verifyRefuseDTO.getVerifyBarCode(),new HashMap<String,Object>());
            Integer count = goodsVerifyService.updateVerifyInfo(verifyPO);
            if (count == 0) {
                throw new BusinessException("审核不通过数据更新失败");
            }
        } catch (Exception e) {
            logger.error("商品上下架失败!失败原因:{},请求参数:{},入库参数:{}", ExceptionUtil.stackTraceString(e), verifyRefuseDTO, verifyPO);
            throw new BusinessException("审核不通过数据更新失败");
        }
        return BaseResult.success();
    }

    private Integer obtainRefuseCode(VerifyRefuseDTO verifyRefuseDTO) {
        if (!VERIFY_REFUSE_CODE_LIST.contains(verifyRefuseDTO.getVerifyRefuseCode())) {
            verifyRefuseDTO.setVerifyRefuseCode(VERIFY_REFUSE_OTHER);
            return VERIFY_REFUSE_OTHER;
        }
        return verifyRefuseDTO.getVerifyRefuseCode();
    }

    private String obtainRefuseReason(VerifyRefuseDTO verifyRefuseDTO) {
        if (VERIFY_REFUSE_OTHER == verifyRefuseDTO.getVerifyRefuseCode()) {
            return verifyRefuseDTO.getVerifyRefuseReason();
        }
        return RMap.getStr(VERIFY_REFUSE_REASON_MAP, verifyRefuseDTO.getVerifyRefuseCode());
    }

}
