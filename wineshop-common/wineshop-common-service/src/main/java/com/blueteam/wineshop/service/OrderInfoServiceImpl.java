package com.blueteam.wineshop.service;

import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.AES;
import com.blueteam.base.util.Coder;
import com.blueteam.base.util.ExceptionUtil;
import com.blueteam.base.util.VerificationUtil;
import com.blueteam.base.util.weixin.PrePayMentDTO;
import com.blueteam.base.util.weixin.UUIDHexGenerator;
import com.blueteam.base.util.weixin.WeiXinUtil;
import com.blueteam.entity.dto.*;
import com.blueteam.entity.po.*;
import com.blueteam.wineshop.mapper.*;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    CouponRecordMapper couponRecordMapper;

    @Autowired
    CarriersInfoMapper carriersInfoDao;

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    ThirdPartyUserInfoMapper thirdPartyUserInfoMapper;
    @Autowired
    private CouponInfoService couponInfoService;

    @Autowired
    private VendorInfoMapper vendorInfoMapper;

    @Autowired
    CityInfoMapper cityInfoMapper;

    @Autowired
    CurrencyRecordMapper currencyRecorddao;
    /**
     * 酒币明细
     */

    @Autowired
    private MqService mqService;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Logger logger = LogManager.getLogger(this.getClass());


    public List<OrderInfo> OrderInfoList(int userid) {
        return orderInfoMapper.OrderInfoList(userid);
    }


    public Map<String, Object> sumjifen(int UserId) {
        return orderInfoMapper.sumjifen(UserId);
    }

    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 保存订单
     *
     * @param orderInfo      新的订单
     * @param spbillCreateIp 用户ip
     * @param body           商品描述
     * @return
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    public synchronized BaseResult saveOrderInfo(OrderInfo orderInfo, String spbillCreateIp, String body) {
        orderInfo.setCreatedate(new Date());
        orderInfo.setUpdatedate(new Date());
        VendorInfo vendorInfo = vendorInfoMapper.vendorDetail(orderInfo.getVendorid());
        if (null != vendorInfo) {
            orderInfo.setTitle(vendorInfo.getName());
        }
        BigDecimal totalMoney = orderInfo.getTotalmoney();
        logger.info("支付开始：" + totalMoney.toString());
        CouponInfo couponInfo = new CouponInfo();
        if (null != orderInfo.getCouponid() && !"".equals(orderInfo.getCouponid())) {
            couponInfo = couponInfoService.couponDetail(orderInfo.getCouponid());
            if (orderInfo.getUserid() != null && !orderInfo.getUserid().equals(couponInfo.getUserId())) {
                return ApiResult.error("fail", "不能使用他人优惠券!");
            }
            if (orderInfo.getVendorid() != null && !(orderInfo.getVendorid().equals(couponInfo.getForeignKey()) || couponInfo.getForeignKey().equals(0))) {
                return ApiResult.error("fail", "不能使用其他商家优惠券!");
            }
            if (null != totalMoney && decideCoupon(totalMoney.doubleValue(), couponInfo) && !CouponInfo.COST_STATUS_USED.equals(couponInfo.getCostStatus())) {
                BigDecimal money = couponInfo.getMoney();
                if (totalMoney.subtract(money).doubleValue() <= 0) {//判断优惠券是否大于总金额
                    orderInfo.setDiscountamount(new BigDecimal("0.01"));//最后金额
                    orderInfo.setPrice(new BigDecimal("0.01"));
                } else {
                    orderInfo.setDiscountamount(totalMoney.subtract(money));//最后金额
                    orderInfo.setPrice(totalMoney.subtract(money));
                }
            } else {
                if (totalMoney.doubleValue() * 100 <= couponInfo.getCostLimitMoney().intValue() * 100) {
                    return ApiResult.error("fail", "付款金额不满足优惠券使用条件！");
                } else {
                    return ApiResult.error("fail", "优惠券已使用!");
                }
            }
            if (couponInfo.getForeignKey().equals(0)) {
                orderInfo.setSettlement_money(orderInfo.getTotalmoney());
            } else {
                orderInfo.setSettlement_money(orderInfo.getDiscountamount());
            }
        } else {
            orderInfo.setDiscountamount(orderInfo.getTotalmoney());//最后金额
            orderInfo.setPrice(orderInfo.getTotalmoney());
            orderInfo.setSettlement_money(orderInfo.getTotalmoney());
        }
        orderInfo.setCount(1);
        String serialCode = Coder.getSerialCode20();
        serialCode += Coder.getSerialCode4();
        orderInfo.setOrderno(serialCode);//订单号
        orderInfo.setOrderstatus(OrderInfo.ORDER_STATUS_WAITING_PAY);
        orderInfo.setPayway("WX");
        String[] strsArea = vendorInfo.getCityCode().toString().split("\\_", -1);
        CityInfo cityInfo = null;
        String area = "";
        for (int i = 0; i < strsArea.length; i++) {
            switch (i) {
                case 0:
                    cityInfo = cityInfoMapper.selectCityName(strsArea[i]);
                    area += cityInfo != null ? cityInfo.getName() : "";
                    break;
                case 1:
                    cityInfo = cityInfoMapper.selectCityName(strsArea[0] + "_" + strsArea[i]);
                    area += cityInfo != null ? cityInfo.getName() : "";
                    break;
                case 2:
                    cityInfo = cityInfoMapper.selectCityName(vendorInfo.getCityCode());
                    area += cityInfo != null ? cityInfo.getName() : "";
                    break;
                default:
                    break;
            }
        }
        orderInfo.setTradingArea(area);
        int status = orderInfoMapper.insert(orderInfo);
        if (status == 1) {
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(orderInfo.getUserid());

            if (null != orderInfo.getCouponid() && !"".equals(orderInfo.getCouponid())) {
                // TODO Auto-generated method stub
//				InetAddress ia=null;
                try {
//					ia=ia.getLocalHost();
//					String localip=ia.getHostAddress();
//					if(null == localip){
//						localip = "";
//					}
//					logger.info("ip地址是多少:"+localip);
//					String ip = ReadFile.getValue("weixin.payIp");
                    boolean flag = true;
//					if (ip != null ) {
//						String[] strs = ip.split("\\|",-1);
//						for (String string : strs) {
//							if(null != string && localip.equals(string.trim())){
//								flag = true;
//								break;
//							}
//						}
//					}
                    if (flag) {
                        OrderInfo orderInfo1 = orderInfoMapper.getOrderInfo(orderInfo.getOrderno());
                        Map<String, Object> ipMap = new HashMap<>();
                        ipMap.put("fail", "IP");
                        orderInfo1.setOrderstatus(OrderInfo.ORDER_STATUS_FINISH_FINISHED);
                        orderInfo1.setUpdatedate(new Date());
                        orderInfo1.setUpdateby("system");
                        orderInfo1.setPaytime(new Date());
                        orderInfoMapper.updateByPrimaryKey(orderInfo1);
                        //回调修改优惠券状态 保存优惠券使用表 处理酒币
                        payWXSyntonyoRDERInfo(orderInfo1);
                        return ApiResult.success(ipMap);
                    } else {
                        List<ThirdPartyUserInfo> listTpui = thirdPartyUserInfoMapper.listThirdPartyUserInfo(orderInfo.getUserid(), Enums.UserType.Every, Enums.ThirdPartyUserInfo.WEI_XIN, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
                        if (null == listTpui || listTpui.size() < 1)
                            return ApiResult.error("第三方数据错误");
                        return prePay(orderInfo, body, spbillCreateIp, listTpui.get(0).getThirdPartyId());
                    }
                } catch (Exception e) {
                    return ApiResult.error("fail", "错误");
                }
            } else {
//				InetAddress ia=null;
                try {
//					ia=ia.getLocalHost();
//					String localip=ia.getHostAddress();
//					if(null == localip){
//						localip = "";
//					}
//					logger.info("ip地址是多少:"+localip);
//					String ip = ReadFile.getValue("weixin.payIp");
                    boolean flag = true;
//					if (ip != null )
//					String[] strs = ip.split("\\|",-1);
//					if (ip != null ) {
//						String[] strs = ip.split("\\|",-1);
//						for (String string : strs) {
//							if(null != string && localip.equals(string.trim())){
//								flag = true;
//								break;
//							}
//						}
//					}
                    if (flag) {
                        OrderInfo orderInfo1 = orderInfoMapper.getOrderInfo(orderInfo.getOrderno());
                        Map<String, Object> ipMap = new HashMap<>();
                        ipMap.put("fail", "IP");
                        orderInfo1.setOrderstatus(OrderInfo.ORDER_STATUS_FINISH_FINISHED);
                        orderInfo1.setUpdatedate(new Date());
                        orderInfo1.setUpdateby("system");
                        orderInfo1.setPaytime(new Date());
                        orderInfoMapper.updateByPrimaryKey(orderInfo1);
                        //回调修改优惠券状态 保存优惠券使用表 处理酒币
                        payWXSyntonyoRDERInfo(orderInfo);
                        return ApiResult.success(ipMap);
                    } else {
                        List<ThirdPartyUserInfo> listTpui = thirdPartyUserInfoMapper.listThirdPartyUserInfo(orderInfo.getUserid(), Enums.UserType.Every, Enums.ThirdPartyUserInfo.WEI_XIN, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
                        if (null == listTpui || listTpui.size() < 1)
                            return ApiResult.error("第三方数据错误");
                        return prePay(orderInfo, body, spbillCreateIp, listTpui.get(0).getThirdPartyId());
                    }
                } catch (Exception e) {
                    logger.info("localip" + e);
                    return ApiResult.error("fail", "错误");
                }
            }
        } else {
            logger.error("FJJH_PAY_131:参数错误,保存信息");
            return ApiResult.error("fail", "参数错误,保存信息");
        }
    }

    /**
     * @param orderInfo
     * @param body           支付已经没有使用
     * @param spbillCreateIp
     * @param openid
     * @return
     * @author xiaojiang 2017年2月28日
     * @version 1.0
     * @since 1.0 2017年2月28日
     */
    public BaseResult prePay(OrderInfo orderInfo, String body, String spbillCreateIp, String openid) {
        logger.info("FJJH_PAY_BEGIN_148:支付开始");
        BaseResult baseResult = new BaseResult();
        Map<String, String> prepayTemp = new HashMap<String, String>();
        //支付开始
        PrePayMentDTO prePayMent = new PrePayMentDTO();
        prePayMent.setAppid(WeiXinUtil.APPID);
        prePayMent.setMch_id(WeiXinUtil.MCH_ID);
        prePayMent.setNonce_str(UUIDHexGenerator.generate());
        try {
            String newbody = new String(body.getBytes("ISO-8859-1"), "UTF-8");
            prePayMent.setBody(newbody);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        prePayMent.setOut_trade_no(orderInfo.getOrderno());//订单编号

//        prePayMent.setTotal_fee(orderInfo.getDiscountamount().doubleValue() * 100);
        prePayMent.setSpbill_create_ip(spbillCreateIp);//用户ip
        prePayMent.setNotify_url(WeiXinUtil.NOTIFY_URL);//回调地址
        prePayMent.setTrade_type(WeiXinUtil.TRADE_TYPE);
        prePayMent.setOpenid(openid);
        // 把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("appid", prePayMent.getAppid());
        sParaTemp.put("mch_id", prePayMent.getMch_id());
        sParaTemp.put("nonce_str", prePayMent.getNonce_str());
        sParaTemp.put("body", prePayMent.getBody());
        sParaTemp.put("out_trade_no", prePayMent.getOut_trade_no());
//        sParaTemp.put("total_fee", prePayMent.getTotal_fee());
        sParaTemp.put("spbill_create_ip", prePayMent.getSpbill_create_ip());
        sParaTemp.put("notify_url", prePayMent.getNotify_url());
        sParaTemp.put("trade_type", prePayMent.getTrade_type());
        sParaTemp.put("openid", prePayMent.getOpenid());
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = WeiXinUtil.paraFilter(sParaTemp);
        String prestr = WeiXinUtil.createLinkString(sPara);
        logger.info("签名之前：" + prestr);
        String key = "&key=" + WeiXinUtil.KEY;
        //MD5运算生成签名
        String mysign = WeiXinUtil.sign(prestr, key, "utf-8").toUpperCase();//密钥出来变大写
        logger.info("签名之前：" + mysign);
        prePayMent.setSign(mysign);
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        //打包要发送的xml
        //启用反射获取数据
        //获取对象
        Class<? extends PrePayMentDTO> prePayMentClass = (Class<? extends PrePayMentDTO>) prePayMent.getClass();
        Field[] fs = prePayMentClass.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true);
            try {
                Object val = f.get(prePayMent);
                if (null != val && !"".equals(val)) {
                    if (f.getName().equals("total_fee")) {
                        sb.append("<" + f.getName() + ">" + mathRoundDouble(val) + "</" + f.getName() + ">");
                    } else {
                        sb.append("<" + f.getName() + ">" + val + "</" + f.getName() + ">");

                    }
                }
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
        }
        sb.append("</xml>");
        //反射结束 等到xml请求包 sb.toString()
        String param = sb.toString();
        logger.info("FJJH_PAY_BEGIN_210:第一次签名开始" + param.toString());
        String result = WeiXinUtil.httpRequest(WeiXinUtil.PAY_URL, "POST", param);
        logger.info(result);
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        InputStream in = new ByteArrayInputStream(result.getBytes());
        // 读取输入流
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(in);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            @SuppressWarnings("unchecked")
            List<Element> elementList = root.elements();
            for (Element element : elementList) {
                map.put(element.getName(), element.getText());
            }
            // 返回信息
            String return_code = map.get("return_code");//返回状态码
            String return_msg = map.get("return_msg");//返回信息
            logger.info("FJJH_PAY_BEGIN_210:第一次签名结束--状态码:" + return_code + "||返回信息:" + return_msg);
            if ("SUCCESS".equals(return_code.toUpperCase())) {
                if ("SUCCESS".equals(map.get("result_code").toUpperCase())) {
                    // 把请求参数打包成数组
                    prepayTemp.put("appId", prePayMent.getAppid());
                    Long timeStamp = System.currentTimeMillis() / 1000;
                    prepayTemp.put("timeStamp", timeStamp + "");
                    String nonceStr = UUIDHexGenerator.generate();
                    prepayTemp.put("nonceStr", nonceStr);
                    // 业务结果
                    String prepay_id = map.get("prepay_id");//返回的预付单信息
                    prepayTemp.put("package", "prepay_id=" + prepay_id);
                    prepayTemp.put("signType", "MD5");
                    //再次签名
                    Map<String, String> prePay = WeiXinUtil.paraFilter(prepayTemp);
                    String prePayStr = WeiXinUtil.createLinkString(prePay);
                    logger.info("FJJH_PAY_BEGIN_255:第二次签名结束" + prePayStr);
                    //					 prePayStr = "appid="+prePayMent.getAppid()+"&nonceStr=" + nonceStr + "&package=prepay_id=" + prepay_id+ "&signType=MD5&timeStamp=" + timeStamp;
                    //					logger.info("FJJH_PAY_BEGIN_257:第二次签名结束"+prePayStr);
                    String paySign = WeiXinUtil.sign(prePayStr, "&key=" + WeiXinUtil.KEY, "utf-8").toUpperCase();
                    prepayTemp.put("paySign", paySign);
                    prepayTemp.put("outTradeNo", orderInfo.getOrderno());
                    logger.info("FJJH_PAY_END_260:第二次签名结束:" + prepayTemp.toString());
                    baseResult = ApiResult.success(prepayTemp);
                } else {
                    logger.error("FJJH_PAY_251:参数错误:" + map.get("err_code_des"));
                    baseResult = ApiResult.error(map.get("err_code_des"), "微信参数错误");
                }
            } else {
                baseResult = ApiResult.error(return_code, return_msg);
            }
        } catch (DocumentException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return baseResult;
    }


    /**
     * 判断优惠券是否可用
     *
     * @param num
     * @return true可用
     * @author xiaojiang 2017年2月27日
     * @version 1.0
     * @since 1.0 2017年2月27日
     */

    public boolean decideCoupon(Double num, CouponInfo couponInfo) {
        logger.info("支付开始：" + num);
        SimpleDateFormat dataS = new SimpleDateFormat("yyyy-MM-dd");
        boolean flag = false;
        Date date = new Date();
        try {
            date = dataS.parse(dataS.format(date));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        String beginTimeStr = couponInfo.getBeginTime();
        String endTimeStr = couponInfo.getEndTime();
        if (null != couponInfo.getCondition() && !"".equals(couponInfo.getCondition())) {//无条件
            try {
                if (SDF.parse(beginTimeStr).getTime() <= date.getTime() && date.getTime() <= SDF.parse(endTimeStr).getTime()) {
                    flag = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {//满多少
            if (num * 100 >= couponInfo.getCostLimitMoney().intValue() * 100) {
                try {
                    logger.info("支付开始numdate：" + num);
                    if (SDF.parse(beginTimeStr).getTime() <= date.getTime() && date.getTime() <= SDF.parse(endTimeStr).getTime()) {
                        flag = true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            logger.info("支付开始flag：" + flag);
        }
        return flag;
    }

    /**
     * 微信回调函数
     *
     * @param request
     * @return
     * @author xiaojiang 2017年2月28日
     * @version 1.0
     * @since 1.0 2017年2月28日
     */

    public synchronized String payWXSyntony(HttpServletRequest request) {
        logger.info("微信回掉开始！");
        Map<String, String> map = new HashMap<String, String>();
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
            if ("SUCCESS".equals(map.get("return_code").toUpperCase())) {
                logger.info("回掉result_code！" + map.get("result_code"));
                if ("SUCCESS".equals(map.get("result_code").toUpperCase())) {
                    OrderInfo orderInfo = orderInfoMapper.getOrderInfo(map.get("out_trade_no"));
                    if (orderInfo != null && orderInfo.getOrderstatus() == OrderInfo.ORDER_STATUS_FINISH_FINISHED) {
                        sb.append("<xml>");
                        sb.append("<return_code><![CDATA[SUCCESS]]></return_code>");
                        sb.append("<return_msg><![CDATA[OK]]></return_msg>");
                        sb.append("</xml>");

                        //推送消息
                        try {
                            logger.info("开始准备推送消息 消息ID=" + orderInfo.getId());
                            SendMessage message = new SendMessage();
                            //设置
                            message.setController("OrderInfoController");
                            message.setAction("paySuccess");
                            HashMap paramterMap = new HashMap();
                            paramterMap.put("id", orderInfo.getId());
                            message.setParamters(paramterMap);
                            message.setDataKey(orderInfo.getId().toString());
                            message.setDataSource("");
                            message.setPlatform("");
                            message.setPushServerVersion("");
                            message.setSoftApp("");
                            mqService.sendMessage(message);
                        } catch (Exception ex) {
                            logger.error(ExceptionUtil.stackTraceString(ex));
                        }


                    } else {
                        Map<String, String> sPara = WeiXinUtil.paraFilter(map);
                        String prestr = WeiXinUtil.createLinkString(sPara);
                        String key = "&key=" + WeiXinUtil.KEY;
                        String mysign = WeiXinUtil.sign(prestr, key, "utf-8").toUpperCase();
                        if (orderInfo != null && mysign.equals(map.get("sign")) && orderInfo.getOrderno().equals(map.get("out_trade_no"))) {
                            orderInfo.setOrderstatus(OrderInfo.ORDER_STATUS_FINISH_FINISHED);
                            orderInfo.setUpdatedate(new Date());
                            orderInfo.setUpdateby("system");
                            orderInfo.setPaytime(new Date());
                            orderInfoMapper.updateByPrimaryKey(orderInfo);
                            //回调修改优惠券状态 保存优惠券使用表 处理酒币
                            payWXSyntonyoRDERInfo(orderInfo);
                            sb.append("<xml>");
                            sb.append("<return_code><![CDATA[SUCCESS]]></return_code>");
                            sb.append("<return_msg><![CDATA[OK]]></return_msg>");
                            sb.append("</xml>");
                        } else {
                            sb.append("<xml>");
                            sb.append("<return_code><![CDATA[FAIL]]></return_code>");
                            logger.info("回掉签名失败");
                            sb.append("<return_msg><![CDATA[签名验证失败]]></return_msg>");
                            sb.append("</xml>");
                        }
                    }
                } else {
                    sb.append("<xml>");
                    sb.append("<return_code><![CDATA[FAIL]]></return_code>");
                    logger.info("回掉return_msg！" + map.get("err_code"));
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
     * 格式化小数
     *
     * @param o
     * @return
     * @author xiaojiang 2017年3月7日
     * @version 1.0
     * @since 1.0 2017年3月7日
     */
    public String mathRoundDouble(Object o) {
        Double d = Double.parseDouble(o.toString());
        if (Math.round(d) - d == 0D) {
            return String.valueOf(Math.round(d));
        }
        return String.valueOf(d);
    }

    /**
     * 查询优惠券订单状态
     *
     * @author xiaojiang 2017年3月7日
     * @version 1.0
     * @since 1.0 2017年3月7日
     */

    public void queryCouponsOrderStatus() {
        //查询所有当前10分钟之前未完成的订单  暂时只用管 代付款
        List<OrderInfo> orderInfos = orderInfoMapper.listConditionOrderInfo(OrderInfo.ORDER_STATUS_WAITING_PAY, -15);
        if (null != orderInfos && orderInfos.size() != 0) {
            for (OrderInfo orderInfo : orderInfos) {
                ckiseWXOrderInfo(orderInfo.getOrderno());

            }
        }
    }

    /**
     * 根据商户订单号查询微信预支付订单状态
     *
     * @param outTradeNo
     * @return
     * @author xiaojiang 2017年3月7日
     * @version 1.0
     * @since 1.0 2017年3月7日
     */

    public Map<String, Object> listWXOrderInfo(String outTradeNo) {
        String result = WeiXinUtil.httpRequest(WeiXinUtil.ORDER_QUERY_URL, "POST", requestParameters(outTradeNo));
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        InputStream in = new ByteArrayInputStream(result.getBytes());
        // 读取输入流
        SAXReader reader = new SAXReader();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Document document = reader.read(in);
            Element root = document.getRootElement();
            @SuppressWarnings("unchecked")
            List<Element> elementList = root.elements();
            for (Element element : elementList) {
                map.put(element.getName(), element.getText());
            }
            // 返回信息
            String return_code = map.get("return_code");//返回状态码
            if ("SUCCESS".equals(return_code.toUpperCase())) {
                if ("SUCCESS".equals(map.get("result_code").toUpperCase())) {
                    /**
                     * 交易状态 SUCCESS—支付成功
                     * REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭
                     *REVOKED—已撤销（刷卡支付）USERPAYING--用户支付中 PAYERROR--支付失败(其他原因，如银行返回失败)
                     */
                    returnMap.put("trade_state", map.get("trade_state"));
                    returnMap.put("total_fee", map.get("total_fee"));//订单金额(注意:单位为分)
                    returnMap.put("time_end", map.get("time_end"));//支付完成时间
                    returnMap.put("return_code", map.get("return_code"));
                } else {
                    returnMap.put("return_code", map.get("result_code"));
                    returnMap.put("err_code", map.get("err_code"));
                }
            } else {
                returnMap.put("return_code ", map.get("return_code"));
                returnMap.put("return_msg", map.get("return_msg"));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return returnMap;
    }

    /**
     * 关闭微信订单
     *
     * @param outTradeNo
     * @return
     * @author xiaojiang 2017年3月8日
     * @version 1.0
     * @since 1.0 2017年3月8日
     */
    public BaseResult ckiseWXOrderInfo(String outTradeNo) {
        String result = WeiXinUtil.httpRequest(WeiXinUtil.ORDER_QUERY_URL, "POST", requestParameters(outTradeNo));
        Map<String, String> map = new HashMap<String, String>();
        InputStream in = new ByteArrayInputStream(result.getBytes());
        SAXReader reader = new SAXReader();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Document document = reader.read(in);
            Element root = document.getRootElement();
            @SuppressWarnings("unchecked")
            List<Element> elementList = root.elements();
            for (Element element : elementList) {
                map.put(element.getName(), element.getText());
            }
            String return_code = map.get("return_code");
            if ("SUCCESS".equals(return_code.toUpperCase())) {
                if ("SUCCESS".equals(map.get("result_code").toUpperCase())) {
                    /**
                     * 交易状态 SUCCESS—支付成功
                     * REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭
                     *REVOKED—已撤销（刷卡支付）USERPAYING--用户支付中 PAYERROR--支付失败(其他原因，如银行返回失败)
                     */
                    returnMap.put("trade_state", map.get("trade_state"));
                    switch (map.get("trade_state").toString().toUpperCase()) {
                        case "NOTPAY":
                            String resultN = WeiXinUtil.httpRequest(WeiXinUtil.ORDER_QUERY_URL, "POST", requestParameters(outTradeNo));
                            // 将解析结果存储在HashMap中
                            Map<String, String> mapN = new HashMap<String, String>();
                            InputStream inN = new ByteArrayInputStream(resultN.getBytes());
                            // 读取输入流
                            SAXReader readerN = new SAXReader();
                            Document documentN = readerN.read(inN);
                            Element rootN = documentN.getRootElement();
                            @SuppressWarnings("unchecked")
                            List<Element> elementListN = rootN.elements();
                            for (Element element : elementListN) {
                                mapN.put(element.getName(), element.getText());
                            }
                            // 返回信息
                            String return_codeN = mapN.get("return_code");//返回状态码
                            if ("SUCCESS".equals(return_codeN.toUpperCase())) {
                                if ("SUCCESS".equals(mapN.get("result_code").toUpperCase())) {
                                    OrderInfo orderInfo = orderInfoMapper.getOrderInfo(map.get("out_trade_no"));
                                    if (orderInfo != null) {
                                        returnMap.put("result_code", mapN.get("result_code"));
                                        returnMap.put("result_msg", "订单已关闭!");
                                        orderInfo.setOrderstatus(OrderInfo.ORDER_STATUS_SHUT_DOWN);
                                        orderInfo.setUpdatedate(new Date());
                                        orderInfo.setUpdateby("system");
                                        orderInfoMapper.updateByPrimaryKey(orderInfo);
                                        if (null != orderInfo.getCouponid()) {//修改优惠券状态
                                            couponInfoService.updateCouponInfo(CouponInfo.COST_STATUS_UNUSED, orderInfo.getCouponid());
                                            couponRecordMapper.removeCouponRecord(orderInfo.getCouponid());
                                        }
                                    }
                                }
                            }
                            break;
                        case "PAYERROR":
                            OrderInfo orderInfo = orderInfoMapper.getOrderInfo(map.get("out_trade_no"));
                            if (orderInfo != null) {
                                returnMap.put("result_code", map.get("result_code"));
                                returnMap.put("result_msg", "订单已关闭!");
                                orderInfo.setOrderstatus(OrderInfo.ORDER_STATUS_SHUT_DOWN);
                                orderInfo.setUpdatedate(new Date());
                                orderInfo.setUpdateby("system");
                                orderInfoMapper.updateByPrimaryKey(orderInfo);
                                if (null != orderInfo.getCouponid()) {//修改优惠券状态
                                    couponInfoService.updateCouponInfo(CouponInfo.COST_STATUS_UNUSED, orderInfo.getCouponid());
                                    couponRecordMapper.removeCouponRecord(orderInfo.getCouponid());
                                }
                            }
                            break;
                        case "SUCCESS":
                            OrderInfo orderInfo1 = orderInfoMapper.getOrderInfo(map.get("out_trade_no"));
                            if (orderInfo1 != null) {
                                returnMap.put("result_code", map.get("result_code"));
                                returnMap.put("result_msg", "订单已关闭!");
                                orderInfo1.setOrderstatus(OrderInfo.ORDER_STATUS_FINISH_FINISHED);
                                orderInfo1.setUpdatedate(new Date());
                                orderInfo1.setUpdateby("system");
                                orderInfo1.setPaytime(new Date());
                                orderInfoMapper.updateByPrimaryKey(orderInfo1);
                                //回调修改优惠券状态 保存优惠券使用表 处理酒币
                                payWXSyntonyoRDERInfo(orderInfo1);
                            }
                        default:
                            break;
                    }
                }
            }
        } catch (Exception e) {
        }
        return ApiResult.success(returnMap);
    }

    /**
     * 用于查询 关闭等封装请求参数
     *
     * @param outTradeNo
     * @return
     * @author xiaojiang 2017年3月8日
     * @version 1.0
     * @since 1.0 2017年3月8日
     */
    public String requestParameters(String outTradeNo) {
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("appid", WeiXinUtil.APPID);
        sParaTemp.put("mch_id", WeiXinUtil.MCH_ID);
        sParaTemp.put("nonce_str", UUIDHexGenerator.generate());
        sParaTemp.put("out_trade_no", outTradeNo);
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = WeiXinUtil.paraFilter(sParaTemp);
        String prestr = WeiXinUtil.createLinkString(sPara);
        logger.info("签名之前：" + prestr);
        String key = "&key=" + WeiXinUtil.KEY;
        //MD5运算生成签名
        String mysign = WeiXinUtil.sign(prestr, key, "utf-8").toUpperCase();//密钥出来变大写
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<appid>" + WeiXinUtil.APPID + "</appid>");
        sb.append("<mch_id>" + WeiXinUtil.MCH_ID + "</mch_id>");
        sb.append("<nonce_str>" + sParaTemp.get("nonce_str") + "</nonce_str>");
        sb.append("<out_trade_no>" + outTradeNo + "</out_trade_no>");
        sb.append("<sign>" + mysign + "</sign>");
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 根据订单编号查询状态
     *
     * @param outTradeNo
     * @return
     * @author xiaojiang 2017年3月8日
     * @version 1.0
     * @since 1.0 2017年3月8日
     */
    @Override
    public BaseResult getOrderInfo(String outTradeNo) {
        ApiResult apiResult = new ApiResult();
        OrderInfo orderInfo = orderInfoMapper.getOrderInfo(outTradeNo);
        Map<String, Object> map = new HashMap<>();
        if (null != orderInfo && orderInfo.getOrderstatus() == OrderInfo.ORDER_STATUS_FINISH_FINISHED) {
            map.put("result_code", "success");
            map.put("result_msg", "交易成功");
            apiResult.setReturnId(String.valueOf(orderInfo.getId()));
        } else {
            Map<String, Object> result = listWXOrderInfo(outTradeNo);
            if ("SUCCESS".equals(result.get("trade_state"))) {
                OrderInfo newOrderInfo = orderInfoMapper.getOrderInfo(outTradeNo);
                if (null != newOrderInfo && newOrderInfo.getOrderstatus() == OrderInfo.ORDER_STATUS_FINISH_FINISHED) {
                    map.put("result_code", "success");
                    map.put("result_msg", "交易成功");
                    apiResult.setReturnId(String.valueOf(orderInfo.getId()));
                } else {
                    map.put("result_code", "fail");
                    map.put("result_msg", "交易占未成功！");
                }
            } else {
                map.put("result_code", "fail");
                switch (result.get("err_code").toString().toUpperCase()) {
                    case "PAYERROR":
                        map.put("result_msg", "支付失败(其他原因，如银行返回失败)");
                        break;
                    case "USERPAYING":
                        map.put("result_msg", "用户支付中");
                        break;
                    case "REVOKED":
                        map.put("result_msg", "已撤销");
                        break;
                    case "NOTPAY":
                        map.put("result_msg", "未支付");
                        break;
                    case "REFUND":
                        map.put("result_msg", "转入退款");
                        break;
                    default:
                        break;
                }
            }
        }
        apiResult.setSuccess(true);
        apiResult.setData(map);
        apiResult.setMessage("成功");
        apiResult.setStatus("200");
        return apiResult;
    }


    /**
     * 根据条件查询总交易次数 交易额
     *
     * @param status
     * @param tradingArea
     * @param type
     * @param vendorName
     * @param beginTime
     * @param endTime
     * @return
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    @Override
    public Map<String, Object> countPrices(int status, String tradingArea, int type, String vendorName, Date beginTime, Date endTime) {
        // TODO Auto-generated method stub
        return orderInfoMapper.countPrices(status, tradingArea, type, vendorName == null || vendorName.equals("") ? null : vendorName, beginTime, endTime);
    }


    /**
     * 交易数据
     * 返回交易地区 商家名称等
     *
     * @param pageSize
     * @param pageIndex
     * @param tradingArea
     * @param vendorName
     * @param beginTime
     * @param endTime
     * @param type
     * @return
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    @Override
    public BaseResult listLimitOrderInfo(Integer pageSize, Integer pageIndex, String tradingArea,
                                         String vendorName, Date beginTime, Date endTime, int type) {
        List<Map<String, Object>> list = orderInfoMapper.listLimitOrderInfo(pageSize, pageIndex, tradingArea, vendorName, beginTime, endTime, type);
        int count = orderInfoMapper.listCountOrderInfo(tradingArea, vendorName, beginTime, endTime, type);
        return ApiResult.success(list, count);
    }


    /**
     * 根据id查询交易金额
     *
     * @param ids
     * @return
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    @Override
    public Map<String, Object> countPrice(String ids) {
        List<Integer> list = new ArrayList<>();
        if (null != ids && !ids.isEmpty()) {
            String[] idss = ids.split("\\,", -1);
            for (int i = 0; i < idss.length; i++) {
                String inte = idss[i];
                list.add(Integer.parseInt(inte));
            }
        }
        Map<String, Object> map = orderInfoMapper.countPrice(list);
        return map;
    }

    /**
     * 获取用户的消费记录
     *
     * @param search 用户ID
     * @return
     */
    @Override
    public PageResult<List<OrderInfo>> selectUserOrderRecord(BasePageSearch search) {
        PageResult<List<OrderInfo>> result = orderInfoMapper.selectUserOrderRecord(search);
        if (result == null)
            return PageResult.empty();
        return result;
    }

    /**
     * 处理回调函数
     * 优惠券  酒币等o
     *
     * @param orderInfo
     * @author xiaojiang 2017年5月4日
     * @version 1.0
     * @since 1.0 2017年5月4日
     */
    public void payWXSyntonyoRDERInfo(OrderInfo orderInfo) {
        if (null != orderInfo.getCouponid() && !"".equals(orderInfo.getCouponid())) {
            CouponRecord couponRecord = new CouponRecord();
            couponRecord.setUserId(orderInfo.getUserid().toString());
            couponRecord.setCouponId(orderInfo.getCouponid().toString());
            couponRecord.setCreateBy(orderInfo.getCreateby() == "" ? "system" : orderInfo.getUpdateby());
            couponRecord.setCreateDate(sdf.format(new Date()));
            couponRecord.setUpdateBy(orderInfo.getUpdateby() == "" ? "system" : orderInfo.getUpdateby());
            couponRecord.setUpdateDate(sdf.format(new Date()));
            couponRecord.setTotalMoney(orderInfo.getTotalmoney());
            CouponInfo couponInfo = couponInfoService.couponDetail(orderInfo.getCouponid());
            couponRecord.setMoney(couponInfo.getMoney());
            couponRecord.setTitle(couponInfo.getTitle());
            couponRecord.setOrderNo(orderInfo.getOrderno());
            couponRecord.setVendorInfoCouponId(couponInfo.getExpandId());
            couponRecordMapper.insertCouponRecord(couponRecord);
            couponInfoService.updateCouponInfo(CouponInfo.COST_STATUS_USED, orderInfo.getCouponid());
        }
        //酒币相关begin
        Integer counts = currencyRecorddao.countUserInfoCurrencyRecord(orderInfo.getUserid(), orderInfo.getVendorid(), CurrencyRecord.STATUS_INCREASE);
        counts = null == counts ? 0 : counts;
        if (counts < 100) {
            CurrencyRecord currencyRecord = new CurrencyRecord();
            BigDecimal discountamount = orderInfo.getDiscountamount();
            if (discountamount.intValue() > 0) {
                if (counts + discountamount.intValue() <= 100) {
                    currencyRecord.setAmount(discountamount.intValue());
                } else {
                    currencyRecord.setAmount(100 - counts);
                }
                currencyRecord.setNumberId(orderInfo.getId());
                currencyRecord.setVendorinfoId(orderInfo.getVendorid());
                currencyRecord.setUserinfoId(orderInfo.getUserid());
                currencyRecord.setCreateBy(orderInfo.getCreateby());
                currencyRecord.setCreateDate(new Date());
                currencyRecord.setUpdateBy(orderInfo.getUpdateby());
                currencyRecord.setUpdateDate(new Date());
                currencyRecord.setStatus(CurrencyRecord.STATUS_INCREASE);
                currencyRecorddao.saveCurrencyRecord(currencyRecord);
            }
        }
        //酒币相关end

    }

    /**
     * 专属C端接口
     * * @return
     */
    @Override
    public OrderInfo getOrderInfoC(String orderno) {
        return orderInfoMapper.getOrderInfoC(orderno);
    }

    /**
     * 方法的功能描述:TODO 获取 消息接收者实体
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/22 15:11
     * @since 1.3.0
     */
    @Override
    public MessageRecipient getMessageRecipient(Integer orderInfoId) {
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderInfoId);
        MessageRecipient m = new MessageRecipient();
        if (null != orderInfo) {
            m.setUserId(orderInfo.getUserid());
            if (null != orderInfo.getVendorid()) {
                m.setVendorId(orderInfo.getVendorid());
                VendorInfo vendorInfo = vendorInfoMapper.getVendorById(orderInfo.getVendorid());
                if (null != vendorInfo) {
                    Integer id = carriersInfoDao.selectByManagerArea(vendorInfo.getCityCode());
                    m.setCarriersId(null != id ? id : 0);
                }
            } else {
                m.setVendorId(0);
            }

        } else {
            m.setVendorId(0);
            m.setUserId(0);
            m.setCarriersId(0);
        }
        return m;
    }


    /**
     * 根据ID获取支付用户昵称和支付款信息
     *
     * @param id
     * @return
     */
    @Override
    public OrderInfo selectPayUserInfo(Integer id) {

//        Orderinfo orderinfo =new Orderinfo();
//        orderinfo.setOrderno("2017061614334659365079");
//        orderinfo.setVendorid(1380);
//        orderinfo.setUserid(1356);
//        orderinfo.setDiscountamount(BigDecimal.valueOf(1.00));
//        orderinfo.setNickName("as");

        return orderInfoMapper.selectPayUserInfoByID(id);
//        return orderinfo;
    }

    /**
     * 根据订单ID获取普通用户接收者信息
     *
     * @param id
     * @return
     */
    @Override
    public MessageRecipient getEveryMessageRecipient(Integer id) {
        MessageRecipient messageRecipient = new MessageRecipient();
        OrderInfo orderInfo = orderInfoMapper.selectPayUserInfoByID(id);
        if (orderInfo != null)
            messageRecipient.setUserId(orderInfo.getUserid());
        return messageRecipient;
    }


    /**
     * 根据订单ID获取商家用户接收者信息
     *
     * @param id
     * @return
     */
    @Override
    public MessageRecipient getVendorMessageRecipient(Integer id) {
        MessageRecipient messageRecipient = new MessageRecipient();
        OrderInfo orderInfo = orderInfoMapper.selectPayUserInfoByID(id);
        if (orderInfo != null) {
            int vendorId = orderInfo.getVendorid();
            VendorInfo vendorInfo = vendorInfoMapper.getVendorById(vendorId);
            if (vendorInfo == null)
                return messageRecipient;
            messageRecipient.setUserId(vendorInfo.getUserId());
            messageRecipient.setVendorId(vendorId);
        }

        return messageRecipient;
    }

    /**
     * @param code
     * @return
     */
    @Override
    public BaseResult getWXOpenid(String code) {
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("appid", "wx53714580af87b75c");
        sParaTemp.put("secret", "7279941a25687593a006b9a04b0d0687");
        sParaTemp.put("js_code", code);
        sParaTemp.put("grant_type", "authorization_code");
        Map<String, String> sPara = WeiXinUtil.paraFilter(sParaTemp);
        String prestr = WeiXinUtil.createLinkString(sPara);
        String result = WeiXinUtil.httpRequest("https://api.weixin.qq.com/sns/jscode2session", "GET", prestr);
        JSONObject jasonObject = JSONObject.fromObject(result);
        Map maps = (Map) jasonObject;
        maps.put("newOpenid",maps.get("openid").toString());
        maps.put("openid", AES.encrypt(VerificationUtil.TOEKN_KEY, maps.get("openid").toString()));
        return ApiResult.success(maps);
    }
    @Override
    public BaseResult getWXAccessToken() {
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("appid", "wx53714580af87b75c");
        sParaTemp.put("secret", "7279941a25687593a006b9a04b0d0687");
        sParaTemp.put("grant_type", "client_credential");
        Map<String, String> sPara = WeiXinUtil.paraFilter(sParaTemp);
        String prestr = WeiXinUtil.createLinkString(sPara);
        String result = WeiXinUtil.httpRequest("https://api.weixin.qq.com/cgi-bin/token", "GET", prestr);
        JSONObject jasonObject = JSONObject.fromObject(result);
        Map maps = (Map) jasonObject;
        return ApiResult.success(maps);
    }
    public static void main(String[] args){
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("appid", "wx707d6334aaaa6b6b");
        sParaTemp.put("secret", "62c011e33d6c9a51c690b408d64003cc");
        sParaTemp.put("grant_type", "client_credential");
        Map<String, String> sPara = WeiXinUtil.paraFilter(sParaTemp);
        String prestr = WeiXinUtil.createLinkString(sPara);
        String result = WeiXinUtil.httpRequest("https://api.weixin.qq.com/cgi-bin/token", "GET", prestr);
        JSONObject jasonObject = JSONObject.fromObject(result);
        Map maps = (Map) jasonObject;
        System.out.println(maps.toString());

    }



}
