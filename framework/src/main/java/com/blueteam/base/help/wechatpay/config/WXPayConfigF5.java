package com.blueteam.base.help.wechatpay.config;


import com.blueteam.base.help.wechatpay.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

/**
 * 附近酒行F5支付具体配置信息，新工程用diamond获取配置
 */

public class WXPayConfigF5 extends WXPayConfig {

    private byte[] certData;
    private static WXPayConfigF5 INSTANCE;

    //    @TODO  公共号证书在哪？
    private WXPayConfigF5() throws Exception {
//        String certPath = "D://CERT/common/apiclient_cert.p12";
//        File file = new File(certPath);
//        InputStream certStream = new FileInputStream(file);
//        this.certData = new byte[(int) file.length()];
//        certStream.read(this.certData);
//        certStream.close();
    }


    public static WXPayConfigF5 getInstance() throws Exception {
        if (INSTANCE == null) {
            synchronized (WXPayConfigF5.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigF5();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public String getAppID() {
        return WXPayConstants.F5_APP_ID;
    }

    @Override
    public String getMchID() {
        return WXPayConstants.F5_MCH_ID;
    }

    @Override
    public String getKey() {
        return WXPayConstants.F5_KEY;
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public String getNotifyUrl() {
        return WXPayConstants.F5_NOTIFY_URL;
    }

    @Override
    public String getTradeType() {
        return WXPayConstants.F5_TRADE_TYPE;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return WXPayConstants.CONNECT_TIMEOUT_MS;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return WXPayConstants.READ_TIMEOUT_MS;
    }


    @Override
    public String getSingleDomain() {
        return WXPayConstants.DOMAIN_API;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }

    @Override
    public String getSandboxSignKey() throws Exception {
        WXPayConfigF5 config = WXPayConfigF5.getInstance();
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("mch_id", config.getMchID());
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        String sign = WXPayUtil.generateSignature(data, config.getKey());
        data.put("sign", sign);
        WXPay wxPay = new WXPay(config);
        String result = wxPay.requestWithoutCert("https://api.mch.weixin.qq.com/sandbox/pay/getsignkey", data, 10000, 10000);
        return result;
    }
}
