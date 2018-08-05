package com.blueteam.base.util.weixin;

import com.blueteam.base.util.DiamondUtil;
import com.blueteam.base.util.ExceptionUtil;
import com.blueteam.entity.dto.BaseResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.*;

import static com.blueteam.base.constant.DiamondConstant.GLOBAL_DATA;

/**
 * 微信支付工具类  微信公众号授权获取信息类
 *
 * @author xiaojiang 2017年2月28日
 * @version 1.0
 * @since 1.0 2017年2月28日
 */
public class WeiXinUtil {
    /**
     * 小程序ID
     */
    public static final String APPID = "wx53714580af87b75c";
    /**
     * 商户号
     */
    public static final String MCH_ID = "1443855702";
    /**
     * 交易类型
     */
    public static final String TRADE_TYPE = "JSAPI";
    /**
     * 密钥
     */
    public static final String KEY = "fjjh3423asdfaesr2q3452asdfasdf22";
    /**
     * 回调地址
     */
    public static final String NOTIFY_URL = DiamondUtil.getPre(GLOBAL_DATA, "weixin.notifyUrl") + "/wechat/order/payBack";
    /**
     * 预支付地址
     */
    public static final String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 查询订单地址
     */
    public static final String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    /**
     * 关闭订单地址
     */
    public static final String ORDER_CLOSE_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
    /**
     * 退款地址
     */
    public static final String ORDER_REFUND_URL="https://api.mch.weixin.qq.com/secapi/pay/refund";
    /**
     * 退款失败 主动查询接口
     */
    public static final String ORDER_REFUND_QUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";

    private static Logger logger = LogManager.getLogger(WeiXinUtil.class);

    /**
     * 附近酒行公众号appid
     */
    public static final String FJJH_APPID = "wx92acd70cb9efefba";

    /**
     * 附近酒行公众号授权回调地址
     */
    public static final String REDIRECT_URL = "https://www.fjjh.shop/fjjh/wechat/login/user";

    /**
     * 附近酒行公众号appsecret
     */
    public static final String SECRET = "a1c7161f580144b84dc60afee7323f29";


    /**
     * MD5签名密钥
     *
     * @param text          需要签名的字符串
     * @param key           密钥
     * @param input_charset 编码格式
     * @return 签名结果
     * @author xiaojiang 2017年2月28日
     * @version 1.0
     * @since 1.0 2017年2月28日
     */
//	appid=wx53714580af87b75c&body=很好吃的很&mch_id=1443855702&nonce_str=36dc25c75aa35fb7015aa35fb7080000&notify_url=https://apis.ganjiuhui.com/Fjjh/order/payWXSyntony&openid=oyjT70E_illKOK4V5C48fAztihis&out_trade_no=201703061927236446757455&spbill_create_ip=125.70.57.128&total_fee=1&trade_type=JSAPI&key=gjh23423asdfaesr2q3452asdfasdf22
//	appid=wx53714580af87b75c&body=很好吃的很&mch_id=1443855702&nonce_str=36dc25c75aa35fb7015aa35fb7080000&notify_url=https://apis.ganjiuhui.com/Fjjh/order/payWXSyntony&openid=oyjT70E_illKOK4V5C48fAztihis&out_trade_no=201703061927236446757455&spbill_create_ip=125.70.57.128&total_fee=1&trade_type=JSAPI&key=gjh23423asdfaesr2q3452asdfasdf22
    public static String sign(String text, String key, String input_charset) {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    /**
     * 签名字符串
     *
     * @param
     * @param sign          签名结果
     * @param
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
        text = text + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
        if (mysign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    public static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * 生成6位或10位随机数 param codeLength(多少位)
     *
     * @param codeLength
     * @return
     * @author xiaojiang 2017年2月28日
     * @version 1.0
     * @since 1.0 2017年2月28日
     */
    public static String createCode(int codeLength) {
        String code = "";
        for (int i = 0; i < codeLength; i++) {
            code += (int) (Math.random() * 9);
        }
        return code;
    }

    private static boolean isValidChar(char ch) {
        if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
            return true;
        if ((ch >= 0x4e00 && ch <= 0x7fff) || (ch >= 0x8000 && ch <= 0x952f))
            return true;// 简体中文汉字编码
        return false;
    }


    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数
     * @author xiaojiang 2017年2月28日
     * @version 1.0
     * @since 1.0 2017年2月28日
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     * @author xiaojiang 2017年2月28日
     * @version 1.0
     * @since 1.0 2017年2月28日
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                if (key.equals("total_fee")) {
                    prestr = prestr + key + "=" + mathRoundDouble(value);
                } else {
                    prestr = prestr + key + "=" + value;
                }
            } else {
                if (key.equals("total_fee")) {
                    prestr = prestr + key + "=" + mathRoundDouble(value) + "&";
                } else {
                    prestr = prestr + key + "=" + value + "&";
                }
            }
        }
        return prestr;
    }

    public static void main(String[] args) {
//	//	String mysigna =sign("appid=wx53714580af87b75c&body=很好吃的很&mch_id=1443855702&nonce_str=36dc25c75aa35983015aa359834a0000&notify_url=https://apis.ganjiuhui.com/Fjjh/order/payWXSyntony&openid=oyjT70E_illKOK4V5C48fAztihis&out_trade_no=201703061920364751993589&spbill_create_ip=125.70.57.128&total_fee=1&trade_type=JSAPI", "&key=gjh23423asdfaesr2q3452asdfasdf22", "utf-8").toUpperCase();//密钥出来变大写
//
//				Map<String, String> sParaTemp = new HashMap<String, String>();
//		sParaTemp.put("is_subscribe","N");
//		sParaTemp.put("appid","wx53714580af87b75c");
//		sParaTemp.put("fee_type","CNY");
//		sParaTemp.put("nonce_str",  "36dc25c75aaba737015aaba737720000");
////		sParaTemp.put("out_trade_no","");
//		sParaTemp.put("out_trade_no","201703081047341437184219");
//		sParaTemp.put("transaction_id", "4001402001201703082629773232");
//		sParaTemp.put("trade_type","JSAPI");
//		sParaTemp.put("result_code", "SUCCESS");
//		sParaTemp.put("mch_id", "1443855702");
//		sParaTemp.put("total_fee", "1");
//		sParaTemp.put("time_end", "20170308104753");
//		sParaTemp.put("openid", "oyjT70E_illKOK4V5C48fAztihis");
//		sParaTemp.put("bank_type", "CFT");
//		sParaTemp.put("return_code", "SUCCESS");
//		sParaTemp.put("cash_fee", "1");
//		sParaTemp.put("sign_type", "MD5");
//		// 除去数组中的空值和签名参数
//		
//		Map<String, String> sPara = WeiXinUtil.paraFilter(sParaTemp);
//		String prestr = WeiXinUtil.createLinkString(sPara); 
//		String mysigna =sign(prestr, "&key=gjh23423asdfaesr2q3452asdfasdf22", "utf-8").toUpperCase();//密钥出来变大写
        System.out.println(NOTIFY_URL);
    }

    /**
     * 放回https接口
     *
     * @return
     * @author xiaojiang 2017年2月28日
     * @version 1.0
     * @since 1.0 2017年2月28日
     */
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        // 创建SSLContext
        StringBuffer buffer = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //往服务器端写内容
            if (null != outputStr) {
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }
            // 读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ExceptionUtil.stackTraceString(e));
        }
        if (buffer == null){
            return "";}
        return buffer.toString();
    }
    /**
     * 方法的功能描述: 退款工具类
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/23 17:23
     *@modifier
     */
    public static  Map<String, String> refundHttpRequest(String requestUrl, String data)throws Exception{
        //指定读取证书格式为PKCS12
        org.springframework.core.io.Resource fileRource = new ClassPathResource("apiclient_cert.p12");
        InputStream instream = fileRource.getInputStream();
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(instream, WeiXinUtil.MCH_ID.toCharArray());
        // 实例化密钥库 & 初始化密钥工厂
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, WeiXinUtil.MCH_ID.toCharArray());

        // 创建 SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                sslContext,
                new String[]{"TLSv1"},
                null,
                new DefaultHostnameVerifier());

        BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", sslConnectionSocketFactory)
                        .build(),
                null,
                null,
                null
        );

        HttpClient httpclient = HttpClientBuilder.create().setConnectionManager(connManager).build();
        try {
            // 设置响应头信息
            HttpPost httpost = new HttpPost(requestUrl);
            //设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
            httpost.setConfig(requestConfig);
            // 构建消息实体
            StringEntity entitys = new StringEntity(data, "UTF-8");
            httpost.addHeader("Content-Type", "text/xml");
            httpost.setEntity(entitys);
            HttpResponse response = httpclient.execute(httpost);
            HttpEntity entity = response.getEntity();
            String jsonStr = EntityUtils.toString(entity, "UTF-8");
            System.out.println("退款结果"+jsonStr);
            return readStringXmlOut(jsonStr);
        } finally {
            instream.close();
        }
    }

    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static String mathRoundDouble(Object o) {
        Double d = Double.parseDouble(o.toString());
        if (Math.round(d) - d == 0D) {
            return String.valueOf(Math.round(d));
        }
        return String.valueOf(d);
    }

    /**
     * 方法的功能描述:TODO 获取微信公众号授权功能 url
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/8/15 14:53
     * @since 1.4.0
     */
    public String getWeiXinAccredit() {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + FJJH_APPID + "" +
                "&redirect_uri=" + urlEncodeUTF8(REDIRECT_URL) + "&response_type= " +
                "code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        return url;
    }

    public BaseResult getWXUserOpenid(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String code = request.getParameter("code");
        String getAcctessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + FJJH_APPID + "" +
                "&secret=" + SECRET + "&code=" + code + "&grant_type=authorization_code ";
        String result = WeiXinUtil.httpRequest(getAcctessTokenUrl, "GET", null);


        return null;
    }
    /**
     * 方法的功能描述: 格式化xml
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/10 10:54
     *@modifier
     */
    public static String convertToXml(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }
    /**
     * 方法的功能描述: 把xml转化为map
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/10 13:51
     *@modifier
     */
    public static Map<String,String> readStringXmlOut(String xml) {
        Map<String,String> map = new HashMap<>();
        try {
            // 将字符串转为XML
            Document doc = DocumentHelper.parseText(xml);
            // 获取根节点
            Element rootElt = doc.getRootElement();
            //获取根节点下所有节点
            List<Element> list = rootElt.elements();
            //节点的name为map的key，text为map的value
            for (Element element : list) {
                map.put(element.getName(), element.getText());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
