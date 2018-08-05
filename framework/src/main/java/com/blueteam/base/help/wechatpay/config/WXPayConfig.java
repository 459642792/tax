package com.blueteam.base.help.wechatpay.config;


import com.blueteam.base.help.wechatpay.IWXPayDomain;

import java.io.InputStream;

public abstract class WXPayConfig {


    /**
     * 获取 App ID
     *
     * @return App ID
     */
    public abstract String getAppID();


    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
    public abstract String getMchID();


    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    public abstract String getKey();


    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    public abstract InputStream getCertStream();

    /**
     * 支付回调地址
     *
     * @return
     */
    public abstract String getNotifyUrl();

    /**
     * 交易类型
     *
     * @return
     */
    public abstract String getTradeType();

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
    public int getHttpConnectTimeoutMs() {
        return 6 * 1000;
    }

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
    public int getHttpReadTimeoutMs() {
        return 8 * 1000;
    }

    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     *
     * @return
     */
    public abstract IWXPayDomain getWXPayDomain();

    /**
     * 如果不使用容灾机制 直接使用此可用域名
     */
    public abstract String getSingleDomain();

    /**
     * 是否自动上报。
     * 若要关闭自动上报，子类中实现该函数返回 false 即可。
     *
     * @return
     */
    public boolean shouldAutoReport() {
        return true;
    }

    /**
     * 进行健康上报的线程的数量
     *
     * @return
     */
    public int getReportWorkerNum() {
        return 6;
    }


    /**
     * 健康上报缓存消息的最大数量。会有线程去独立上报
     * 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
     *
     * @return
     */
    public int getReportQueueMaxSize() {
        return 10000;
    }

    /**
     * 批量上报，一次最多上报多个数据
     *
     * @return
     */
    public int getReportBatchSize() {
        return 10;
    }


    public abstract String getSandboxSignKey() throws Exception;


}
