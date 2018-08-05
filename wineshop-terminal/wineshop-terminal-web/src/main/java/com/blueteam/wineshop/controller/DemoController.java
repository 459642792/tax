package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.Enums;
import com.blueteam.base.constant.OrderConstant;
import com.blueteam.base.help.generator.KeyGeneratorEnum;
import com.blueteam.base.help.generator.KeyGeneratorStrategy;
import com.blueteam.base.util.ProjectUtil;
import com.blueteam.base.util.VerificationUtil;
import com.blueteam.base.util.weixin.RefundBO;
import com.blueteam.base.util.weixin.RefundQueryBO;
import com.blueteam.base.util.weixin.UUIDHexGenerator;
import com.blueteam.base.util.weixin.WeiXinUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.entity.po.TradeRecordPO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/demo")
@Controller
public class DemoController extends BaseController {

    private static Logger logger = LogManager.getLogger(DemoController.class);

    @RequestMapping("/refund")
    @ResponseBody
    public BaseResult refundTest(){
        Long refundId = KeyGeneratorStrategy.match(KeyGeneratorEnum.REFUND_NO).generateKey();
        Map<String, String> sParaTemp = new HashMap<>();
        sParaTemp.put("appid", WeiXinUtil.APPID);
        sParaTemp.put("mch_id", WeiXinUtil.MCH_ID);
        sParaTemp.put("nonce_str", UUIDHexGenerator.generate());
        sParaTemp.put("sign_type", WeiXinUtil.TRADE_TYPE);
        sParaTemp.put("out_trade_no", "180125151319100180");
        sParaTemp.put("out_refund_no", refundId.toString());
        sParaTemp.put("total_fee","1");
        sParaTemp.put("refund_fee","1");
        sParaTemp.put("refund_desc", "123");
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = WeiXinUtil.paraFilter(sParaTemp);
        String preStr = WeiXinUtil.createLinkString(sPara);
        String key = "&key=" + WeiXinUtil.KEY;
        //MD5运算生成签名
        //密钥出来变大写
        String mySign = WeiXinUtil.sign(preStr, key, "utf-8").toUpperCase();
        RefundBO refund  = new RefundBO();
        refund.setAppid(sParaTemp.get("appid"));
        refund.setMch_id(sParaTemp.get("mch_id"));
        refund.setNonce_str(sParaTemp.get("nonce_str"));
        refund.setOut_refund_no(sParaTemp.get("out_refund_no"));
        refund.setOut_trade_no(sParaTemp.get("out_trade_no"));
        refund.setRefund_fee(sParaTemp.get("refund_fee"));
        refund.setTotal_fee(sParaTemp.get("total_fee"));
        refund.setRefund_desc(sParaTemp.get("refund_desc"));
        refund.setSign_type(sParaTemp.get("sign_type"));
        refund.setSign(mySign);
        //保存退款详情
        boolean flag = false;
        try {
            String param = WeiXinUtil.convertToXml(refund);
            Map<String, String> map = WeiXinUtil.refundHttpRequest(WeiXinUtil.ORDER_REFUND_URL, param);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ApiResult.success();
    }

    @RequestMapping("queryRefund")
    @ResponseBody
    public BaseResult queryRefund(){
        RefundQueryBO refundQueryBO = new RefundQueryBO();
        Map<String, String> refundM = new HashMap<>();
        refundQueryBO.setAppid(WeiXinUtil.APPID);
        refundQueryBO.setMch_id(WeiXinUtil.MCH_ID);
        refundQueryBO.setNonce_str(UUIDHexGenerator.generate());
        refundQueryBO.setOut_trade_no("180125151319100180");
        refundM.put("nonce_str",refundQueryBO.getNonce_str());
        refundM.put("out_trade_no",refundQueryBO.getOut_trade_no());
        refundM.put("mch_id",refundQueryBO.getMch_id());
        refundM.put("appid",refundQueryBO.getAppid());
        // 除去数组中的空值和签名参数
        Map<String, String> rm = WeiXinUtil.paraFilter(refundM);
        String rStr = WeiXinUtil.createLinkString(rm);
        String key = "&key=" + WeiXinUtil.KEY;
        //MD5运算生成签名
        //密钥出来变大写
        String rSign = WeiXinUtil.sign(rStr, key, "utf-8").toUpperCase();
        refundQueryBO.setSign(rSign);
        //得到类的xml
        String paramQuery = WeiXinUtil.convertToXml(refundQueryBO);
        String resultQ = WeiXinUtil.httpRequest(WeiXinUtil.ORDER_REFUND_QUERY_URL, "POST", paramQuery);
        System.out.println("保存记录2"+resultQ);
        return ApiResult.success();
    }

    @RequestMapping("/success")
    @ResponseBody
    public BaseResult getResult() {
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
        BaseResult result = BaseResult.success();
        return result;
    }

    @RequestMapping("/list")
    @ResponseBody
    public BaseResult getList() {
        BaseResult result = ApiResult.success("测试一个");
        return result;
    }

    @ResponseBody
    @RequestMapping("/error")
    public BaseResult error() {
        BaseResult result = BaseResult.error("就是一个错误");
        return result;
    }

    @ResponseBody
    @RequestMapping("/token/{token}")
    public BaseResult token(@PathVariable("token") String token) {
        if (!ProjectUtil.isDebug())
            return BaseResult.success();
        UserIdentify identify = VerificationUtil.getUserIdentify(token);
        return ApiResult.success(identify);

//	    if(!ProjectUtil.isDebug())
//	    	return BaseResult.success();
//	    UserIdentify identify =	super.getIdentify();
//	    return ApiResult.success(identify);
    }

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public BaseResult test(String key, String value) {
        return ApiResult.success(key + "___" + value);
    }

    @ResponseBody
    @RequestMapping("/ex")
    public BaseResult ex() {

        String s = null;
        s.trim();
        return null;
    }


    public static void main(String[] args) {
        System.out.println("begin");
        int k = 7;
        int i = 3;
        if (Enums.FlagEnumHelper.HasFlag(12, 6))
            System.out.println("has");
    }
}
