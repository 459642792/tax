package com.blueteam.wineshop.service.wechatapplet;

import com.alibaba.fastjson.JSON;
import com.blueteam.base.cache.redis.Redis;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.constant.OrderConstant;
import com.blueteam.base.util.AES;
import com.blueteam.base.util.StringUtil;
import com.blueteam.base.util.VerificationUtil;
import com.blueteam.base.util.weixin.PrePayMentDTO;
import com.blueteam.base.util.weixin.UUIDHexGenerator;
import com.blueteam.base.util.weixin.WeiXinUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.OrderPO;
import com.blueteam.entity.po.ThirdPartyUserInfo;
import com.blueteam.entity.po.TradeRecordPO;
import com.blueteam.wineshop.mapper.OrderMapper;

import com.blueteam.wineshop.mapper.ThirdPartyUserInfoMapper;
import com.blueteam.wineshop.mapper.TradeRecordMapper;
import com.blueteam.wineshop.service.MessageSubService;
import com.blueteam.wineshop.service.VendorInfoService;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCommands;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单支付相关
 *
 * @author xiaojiang
 * @create 2018-01-11  10:12
 */
@Service
public class OrderPayImpl implements  OrderPayService {

    /** 微信成功返回标示*/
    private static final  String CODE = "SUCCESS";
    /**
     * 订单相关
     */
    @Autowired
    private OrderMapper orderMapper;
    /**
     * 第三方信息表
     */
    @Autowired
    private ThirdPartyUserInfoMapper thirdPartyUserInfoMapper;

    /** 记忆记录明细 */
    @Autowired
    private TradeRecordMapper tradeRecordMapper;
    @Resource
    private MessageSubService messageSubService;
    @Resource
    private VendorInfoService vendorInfoService;
    /**
     * 方法的功能描述: 获取预支付信息
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/10 15:52
     *@modifier
     */
    @Override
    public BaseResult getForPay(String orderNo, String body, String ipAddr) {
        OrderPO order =  orderMapper.getByOrderNo(orderNo);
        if(order.getValidityState().equals(OrderPO.VALIDITY_STATE_INVALID)){
            return ApiResult.error("对不起，该订单以删除，请刷新");
        }
        if(!order.getOrderState().equals(OrderConstant.OrderStateEnum.TO_PAY.getState())){
            return ApiResult.error("对不起，该订单状态以改变，请刷新");
        }
        if (order.getOrderState().equals(OrderConstant.OrderStateEnum.TO_PAY.getState())
                && order.getCompleteState().equals(OrderConstant.CompleteStateEnum.CANCELED.getState())){
            return ApiResult.error("对不起，该订单状态以取消，请刷新");
        }
        List<ThirdPartyUserInfo> listTpui = thirdPartyUserInfoMapper.listThirdPartyUserInfo(order.getUserId(), Enums.UserType.Every, Enums.ThirdPartyUserInfo.WEI_XIN, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
        if (null == listTpui || listTpui.size() < 1) {
            return ApiResult.error("第三方数据错误");
        }
        return prePay(order, body, ipAddr, listTpui.get(0).getThirdPartyId());
    }
    /**
     * 方法的功能描述: 微信回调方法
     *@methodName  payBack
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/10 16:25
     *@modifier
     */
    @Override
    public String payBack(HttpServletRequest request, HttpServletResponse response) {

        Map<String, String> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        try {
            InputStream in = request.getInputStream();
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(in);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            @SuppressWarnings("unchecked")
            List<Element> elementList = root.elements();
            for (Element element : elementList) {
                map.put(element.getName(), element.getText());
            }
            String returnCode = map.get("return_code").toUpperCase();
            updateTradeRecord(map);
            if (CODE.equals(returnCode)) {
                String resultCode = map.get("result_code").toUpperCase();
                //记录详情
                if (CODE.equals(resultCode)) {
                    Long orderId = Long.valueOf(map.get("out_trade_no"));
                    OrderPO order = orderMapper.getByOrderId(orderId);
                    if (order != null && order.getOrderState().equals(OrderConstant.OrderStateEnum.TO_PROMISE.getState())) {
                        sb.append("<xml>");
                        sb.append("<return_code><![CDATA[SUCCESS]]></return_code>");
                        sb.append("<return_msg><![CDATA[OK]]></return_msg>");
                        sb.append("</xml>");
                    } else {
                        Map<String, String> sPara = WeiXinUtil.paraFilter(map);
                        String prestr = WeiXinUtil.createLinkString(sPara);
                        String key = "&key=" + WeiXinUtil.KEY;
                        String mysign = WeiXinUtil.sign(prestr, key, "utf-8").toUpperCase();
                        if (order != null && mysign.equals(map.get("sign"))) {
                            //状态改成待接单
                            OrderPO newOrder = new OrderPO();
                            newOrder.setOrderId(orderId);
                            if (order.getOrderSource().equals(OrderPO.ORDER_SOURCE_ORDINARY)){
                                newOrder.setOrderState(OrderConstant.OrderStateEnum.TO_PROMISE.getState());
                            }else{
                                newOrder.setOrderState(OrderConstant.OrderStateEnum.HAS_RECEIVE.getState());
                                newOrder.setCompleteState(OrderConstant.CompleteStateEnum.FINISHED.getState());
                                newOrder.setReceiveTime(new Date());
                                newOrder.setCompleteTime(new Date());
                            }
                            newOrder.setUpdateTime(new Date());
                            newOrder.setPayTime(new Date());
                            newOrder.setVersion(order.getVersion());
                            orderMapper.updateOrder(newOrder);
                            if (!order.getOrderSource().equals(OrderPO.ORDER_SOURCE_ORDINARY)) {
                                newOrder.setOrderState(OrderConstant.OrderStateEnum.TO_PROMISE.getState());
                                vendorInfoService.increaseOrderStatistics(order.getVendorId(), 1, 0, order.getPayPrice().intValue());
                            }
                                //发送消息
                            Map<String,Object> messageMap = new HashMap<>();
                            messageMap.put("orderId",order.getOrderNo());
                            messageMap.put("orderPrice", StringUtil.changeF2Y(order.getOriginalPrice().toString()));
                            messageSubService.sendVendormessage(1,order.getVendorId(),messageMap);
                            sb.append("<xml>");
                            sb.append("<return_code><![CDATA[SUCCESS]]></return_code>");
                            sb.append("<return_msg><![CDATA[OK]]></return_msg>");
                            sb.append("</xml>");
                        } else {
                            sb.append("<xml>");
                            sb.append("<return_code><![CDATA[FAIL]]></return_code>");
                            sb.append("<return_msg><![CDATA[签名验证失败]]></return_msg>");
                            sb.append("</xml>");
                        }
                    }
                } else {
                    sb.append("<xml>");
                    sb.append("<return_code><![CDATA[FAIL]]></return_code>");
                    sb.append("<return_msg><![CDATA[" + map.get("err_code") + "]]></return_msg>");
                    sb.append("</xml>");
                }
            } else {
                sb.append("<xml>");
                sb.append("<return_code><![CDATA[FAIL]]></return_code>");
                sb.append("<return_msg><![CDATA[" + map.get("return_msg") + "]]></return_msg>");
                sb.append("</xml>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 方法的功能描述: 面对面付款
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/15 11:44
     *@modifier
     */
    @Override
    public BaseResult facePay(String  orderNo, String ipAddr, String body){
        OrderPO order =  orderMapper.getByOrderNo(orderNo);
        List<ThirdPartyUserInfo> listTpui = thirdPartyUserInfoMapper.listThirdPartyUserInfo(order.getUserId(), Enums.UserType.Every, Enums.ThirdPartyUserInfo.WEI_XIN, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
        if (null == listTpui || listTpui.size() < 1) {
            return ApiResult.error("第三方数据错误");
        }
        return prePay(order, body, ipAddr, listTpui.get(0).getThirdPartyId());
    }


    /**
     * 方法的功能描述: 方法的功能描述:发起预支付
     *@methodName prePay
     * @param: orderInfo
     * @param: body
     * @param: spbillCreateIp
     * @param: openid
     *@return com.blueteam.entity.dto.BaseResult
     *@since 1.4.0
     *@author xiaojiang 2018/1/11 10:15
     *@modifier
     */
    private BaseResult prePay(OrderPO orderInfo, String body, String spbillCreateIp, String openid) {
        JedisCommands redis = Redis.getJedis();
        System.out.println("连接redis"+redis);
        Map<String, String> prepayTemp =new HashMap<>();
        if(redis.exists(orderInfo.getOrderNo())){
           String prePayId= redis.get(orderInfo.getOrderNo());
            prepayTemp = getPaySign(prePayId);
            prepayTemp.put("outTradeNo", orderInfo.getOrderNo());
            prepayTemp.remove("appId");
            return ApiResult.success(prepayTemp);
        }
        openid = AES.decrypt(VerificationUtil.TOEKN_KEY, openid);
        //支付开始 支付类
        PrePayMentDTO prePayMent = new PrePayMentDTO();
        prePayMent.setAppid(WeiXinUtil.APPID);
        prePayMent.setMch_id(WeiXinUtil.MCH_ID);
        //
        prePayMent.setNonce_str(UUIDHexGenerator.generate());
//        try {
//            String newbody = new String(body.getBytes("ISO-8859-1"), "UTF-8");
//            prePayMent.setBody(newbody);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        prePayMent.setBody(body);
        //订单编号
        prePayMent.setOut_trade_no(orderInfo.getOrderId().toString());
        //支付价格
        prePayMent.setTotal_fee(Integer.parseInt(orderInfo.getPayPrice().toString()));
        //用户ip
        prePayMent.setSpbill_create_ip(spbillCreateIp);
        //回调地址
        prePayMent.setNotify_url(WeiXinUtil.NOTIFY_URL);
        prePayMent.setTrade_type(WeiXinUtil.TRADE_TYPE);
        prePayMent.setOpenid(openid);
        // 把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<>();
        sParaTemp.put("appid", prePayMent.getAppid());
        sParaTemp.put("mch_id", prePayMent.getMch_id());
        sParaTemp.put("nonce_str", prePayMent.getNonce_str());
        sParaTemp.put("body", prePayMent.getBody());
        sParaTemp.put("out_trade_no", prePayMent.getOut_trade_no());
        sParaTemp.put("total_fee", prePayMent.getTotal_fee().toString());
        sParaTemp.put("spbill_create_ip", prePayMent.getSpbill_create_ip());
        sParaTemp.put("notify_url", prePayMent.getNotify_url());
        sParaTemp.put("trade_type", prePayMent.getTrade_type());
        sParaTemp.put("openid", prePayMent.getOpenid());
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = WeiXinUtil.paraFilter(sParaTemp);
        String preStr = WeiXinUtil.createLinkString(sPara);
        String key = "&key=" + WeiXinUtil.KEY;
        //MD5运算生成签名
        //密钥出来变大写
        String mySign = WeiXinUtil.sign(preStr, key, "utf-8").toUpperCase();
        prePayMent.setSign(mySign);
        //得到类的xml
        String param = WeiXinUtil.convertToXml(prePayMent);
        //第一次发起支付
        String result = WeiXinUtil.httpRequest(WeiXinUtil.PAY_URL, "POST", param);
        // 将解析结果存储在HashMap中
        Map<String, String> map = WeiXinUtil.readStringXmlOut(result);
        //返回状态码
        String returnCode = map.get("return_code").toUpperCase();
        //返回信息
        String returnMsg = map.get("return_msg");
        //通信标识
        if (CODE.equals(returnCode) ){
            //交易标识
            String resultCode = map.get("result_code").toUpperCase();
            if (CODE.equals(resultCode)) {
                //返回的预付单信息
                String prePayId = map.get("prepay_id");
                prepayTemp = getPaySign(prePayId);
                prepayTemp.put("outTradeNo", orderInfo.getOrderNo());
                redis.set(orderInfo.getOrderNo(),prePayId);
                redis.expire(orderInfo.getOrderNo(),3600);
                saveTradeRecord(TradeRecordPO.TRADE_TYP_PAY,orderInfo.getOrderId(),prepayTemp.toString());
                prepayTemp.remove("appId");
                return ApiResult.success(prepayTemp);
            } else {
                return ApiResult.error(map.get("err_code_des"), "微信参数错误");
            }
        } else {
            return ApiResult.error(returnCode, returnMsg);
        }
    }


    /**
     * 方法的功能描述:修改记录
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/11 11:04
     *@modifier
     */
    private void updateTradeRecord(Map<String, String> map) throws  Exception {
        Integer id =  tradeRecordMapper.getTradeRecord(Long.valueOf(map.get("out_trade_no")));
        TradeRecordPO tradeRecord = new TradeRecordPO();
        tradeRecord.setId(id);
        tradeRecord.setResultCode(map.get("result_code"));
        tradeRecord.setTradeEndTime(new Date());
        tradeRecord.setTradeEndRecord(JSON.toJSONString(map));
        tradeRecordMapper.updateTradeRecord(tradeRecord);
    }
    /**
     * 方法的功能描述: 保存订单发起支付记录
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/11 17:36
     *@modifier
     */
    private void saveTradeRecord(Integer type,Long orderId,String startRecond) {
        TradeRecordPO tradeRecord = new TradeRecordPO();
        tradeRecord.setTradeType(type);
        tradeRecord.setOrderId(orderId);
        tradeRecord.setTradeStartTime(new Date());
        tradeRecord.setTradeStartRecord(JSON.toJSONString(startRecond));
        tradeRecordMapper.saveTradeRecord(tradeRecord);
    }
    private  Map<String, String> getPaySign(String prePayId){
        Map<String, String> prepayTemp = new HashMap<>();
        // 把请求参数打包成数组
        prepayTemp.put("appId", WeiXinUtil.APPID);
        //时间戳
        Long timeStamp = System.currentTimeMillis() / 1000;
        prepayTemp.put("timeStamp", timeStamp + "");
        String nonceStr = UUIDHexGenerator.generate();
        //随机串
        prepayTemp.put("nonceStr", nonceStr);
        // 业务结果
        //数据包
        prepayTemp.put("package", "prepay_id=" + prePayId);
        //签名类型，默认为MD5，支持HMAC-SHA256和MD5。注意此处需与统一下单的签名类型一致
        prepayTemp.put("signType", "MD5");
        //再次签名
        Map<String, String> prePay = WeiXinUtil.paraFilter(prepayTemp);
        String prePayStr = WeiXinUtil.createLinkString(prePay);
        //记录详情
        String paySign = WeiXinUtil.sign(prePayStr, "&key=" + WeiXinUtil.KEY, "utf-8").toUpperCase();
        prepayTemp.put("paySign", paySign);
        return prepayTemp;
    }
}
