package com.blueteam.base.util;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * JSON 序列化 反序列化 工具类
 *
 * @author libra
 */
public class JsonUtil {
    /**
     * JSON创建者
     */
    private static JsonGenerator jsonGenerator = null;
    /**
     * JSON
     */
    private static ObjectMapper objectMapper;

    /**
     * logger
     */
    private static Logger logger = LogManager.getLogger(JsonUtil.class);


    static {
        objectMapper = new ObjectMapper();
        try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

    /**
     * JSON 序列化
     *
     * @param obj 需要序列化的对象
     * @return
     */
    public static <E> String serialize(E obj) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(obj);
            return json;
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return "";
        }
    }


    /**
     * JSON 反序列化
     *
     * @param text JSON文本内容
     * @param type 反序列化的对象类型
     * @return
     */
    public static <E> E deserialize(String text, Class<E> type) {
        E result = null;
        try {
            Gson gson = new Gson();
            result = gson.fromJson(text, type);
        } catch (Exception e) {
            logger.error(ExceptionUtil.stackTraceString(e));
            e.printStackTrace();
        }
        return result;
    }
}
