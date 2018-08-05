package com.blueteam.base.util.upush;

import com.blueteam.base.util.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 方法的功能描述:TODO  推送
 *
 * @param
 * @author xiaojiang 2017/5/19 16:22
 * @methodName
 * @return
 * @since 1.3.0
 */
public class PushClient {

    protected final String USER_AGENT = "Mozilla/5.0";

    protected HttpClient client = new DefaultHttpClient();

    protected static final String host = "http://msg.umeng.com";

    /*  推送地址**/
    protected static final String postPath = "/api/send";

    /**
     * 方法的功能描述:TODO  发起推送
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/19 16:23
     * @since 1.3.0
     */
    public Map<String, Object> send(UmengNotification msg) throws Exception {
        String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
        msg.setPredefinedKeyValue("timestamp", timestamp);
        String url = host + postPath;
        String postBody = msg.getPostBody();
        String sign = DigestUtils.md5Hex(("POST" + url + postBody + msg.getAppMasterSecret()).getBytes("utf8"));
        url = url + "?sign=" + sign;
        HttpPost post = new HttpPost(url);
        post.setHeader("User-Agent", USER_AGENT);
        StringEntity se = new StringEntity(postBody, "UTF-8");
        post.setEntity(se);
        HttpResponse response = client.execute(post);
        int status = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + status);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        Map<String, Object> map = new HashMap<>();
        if (status == 200) {
            String resultStr = "";
            if (null != result) {
                resultStr = result.toString();
            }
            JSONObject respJson = new JSONObject(resultStr.toString());
            String ret = respJson.getString("ret");
            if (ret.equalsIgnoreCase("SUCCESS")) {
                map.put("result", "true");
                map.put("message", "成功！");
            } else {
                map.put("result", "false");
                JSONObject respJson1 = new JSONObject(respJson.getString("data"));
                map.put("message", respJson1.getString("error_code"));
            }
        } else {
            map.put("result", "false");
            map.put("message", "请求失败！");
        }
        return map;
    }
}
