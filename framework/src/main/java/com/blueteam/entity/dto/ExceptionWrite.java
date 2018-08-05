package com.blueteam.entity.dto;

import com.blueteam.base.util.ExceptionUtil;

/**
 * 日志记录类
 *
 * @author libra
 */
public class ExceptionWrite {
    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 请求参数
     */
    private String requestParamter;

    /**
     * 请求的Token
     */
    private String requestToken;

    /**
     * 请求的UserAgent
     */
    private String requestUserAgent;

    /**
     * 请求的IP
     */
    private String requestIp;

    /**
     * 当前登录的用户ID
     * 没有登录为0
     */
    private int userId;

    /**
     * 参数的异常
     */
    private Throwable ex;


    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestParamter() {
        return requestParamter;
    }

    public void setRequestParamter(String requestParamter) {
        this.requestParamter = requestParamter;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    public String getRequestUserAgent() {
        return requestUserAgent;
    }

    public void setRequestUserAgent(String requestUserAgent) {
        this.requestUserAgent = requestUserAgent;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Throwable getEx() {
        return ex;
    }

    public void setEx(Throwable ex) {
        this.ex = ex;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        String line = System.getProperty("line.separator");
        buffer.append(line);
        buffer.append("===========================EX BEGIN==================================");
        buffer.append(line);
        buffer.append("请求URL:");
        buffer.append(this.getRequestUrl());
        buffer.append(line);

        buffer.append("请求IP:");
        buffer.append(this.getRequestIp());
        buffer.append(line);

        buffer.append("UserAgent:");
        buffer.append(this.getRequestUserAgent());
        buffer.append(line);


        buffer.append("paramter:");
        buffer.append(this.getRequestParamter());
        buffer.append(line);

        buffer.append("Token:");
        buffer.append(this.getRequestToken());
        buffer.append(line);

        buffer.append("UserID:");
        buffer.append(this.getUserId());
        buffer.append(line);


        buffer.append(ExceptionUtil.stackTraceString(this.getEx()));

        buffer.append("===========================EX END==================================");
        buffer.append(line);
        return buffer.toString();
    }


}
