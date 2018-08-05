/**
 *
 */
package com.blueteam.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author xia_kze
 */
public class HttpUtils {

    protected static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static String send(String dispatchURL, String requestStr) {
        log.info("请求地址：" + dispatchURL);
        log.info("请求内容：" + requestStr);
        StringBuffer sbReturn = new StringBuffer("");
        URL url = null;
        HttpURLConnection httpConnection = null;
        InputStream in = null;
        OutputStream out = null;
        BufferedReader br = null;
        int timeout = 30000;
        try {
            url = new URL(dispatchURL);
            httpConnection = (HttpURLConnection) url.openConnection();

            //设置其他请求参数
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpConnection.setRequestProperty("Content-Encoding", "UTF-8");
            httpConnection.setRequestProperty("Cache-Control", "no-cache");
            httpConnection.setRequestProperty("Content-Length", String.valueOf(requestStr.length()));
            httpConnection.setRequestProperty("Cache-Control", "no-cache");
            httpConnection.setRequestProperty("accept", "*/*");

            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setConnectTimeout(timeout);
            httpConnection.setReadTimeout(timeout);

            out = httpConnection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
            osw.write(requestStr);
            osw.flush();
            osw.close();

            in = httpConnection.getInputStream();
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String strRead = "";
            while ((strRead = br.readLine()) != null) {
                sbReturn.append(strRead);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception fx) {
                fx.printStackTrace();
            }
            try {
                if (in != null)
                    in.close();
            } catch (Exception fx) {
                fx.printStackTrace();
            }
            try {
                if (br != null)
                    br.close();
            } catch (Exception fx) {
                fx.printStackTrace();
            }
            try {
                if (httpConnection != null)
                    httpConnection.disconnect();
            } catch (Exception fx) {
                fx.printStackTrace();
            }
        }
        log.info("响应内容：" + sbReturn.toString());
        return sbReturn.toString();
    }

}
