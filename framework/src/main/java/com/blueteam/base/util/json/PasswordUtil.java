package com.blueteam.base.util.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by clam on 2017/4/28.
 */
public class PasswordUtil {
    /**
     * 密码加盐
     *
     * @param password 密码
     * @param salt     盐
     * @return
     */
    public static String salt(String password, String salt) {
        return password + "_" + salt;
    }

    /**
     * 加密密码
     *
     * @param password 密码
     * @param salt     盐
     * @return
     */
    public static String encryptPassword(String password, String salt) {
        String result = salt(password, salt);
        String pwd = EncryptUtil.getInstance().md5(result);
        return pwd;
    }

    /**
     * 获取密码和盐
     *
     * @param password
     * @return KEY 为密码 value为盐
     */
    public static HashMap<String, String> buildUserPassword(String password) {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replaceAll("-", "");
        String resultPwd = salt(password, uuidStr);
        String enPwd = EncryptUtil.getInstance().md5(resultPwd);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(enPwd, uuidStr);
        return map;
    }

    public static void main(String[] args) {
        System.out.print("======>"+(2&4));
//        HashMap<String, String> map = buildUserPassword("123456");
//        Iterator iter = map.entrySet().iterator();
//        while (iter.hasNext()) {
//            Map.Entry entry = (Map.Entry) iter.next();
//            Object key = entry.getKey();
//            Object val = entry.getValue();
//            System.out.println("pwd:" + key);
//            System.out.println("salt:" + val);
//        }

    }
}
