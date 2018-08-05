package com.blueteam.base.util.json;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * �ַ�����Լ����ܹ����� huangxiaofeng 2011-7-29
 */
public class EncryptUtil {

    public static String UC_KEY = "12345";
    public static String USER_SERCRETKEY = "user_sercretkey";

    private static EncryptUtil instance = new EncryptUtil();

    private EncryptUtil() {
    }

    public static EncryptUtil getInstance() {
        return instance;
    }

    public String md5(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return byte2hex(md.digest(input.getBytes()));
    }

    public String base64_decode(String input) {
        try {
            return new String(Base64.decode(input, Base64.DEFAULT),
                    "iso-8859-1");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String base64_encode(String input) {
        try {
            return new String(Base64.encode(input.getBytes("iso-8859-1"),
                    Base64.DEFAULT));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else
                hs.append(stmp);
        }
        return hs.toString();
    }

    private String substr(String input, int begin, int length) {
        return input.substring(begin, begin + length);
    }

    private String substr(String input, int begin) {
        if (begin > 0) {
            return input.substring(begin);
        } else {
            return input.substring(input.length() + begin);
        }
    }

    private String sprintf(String format, long input) {
        String temp = "0000000000" + input;
        return temp.substring(temp.length() - 10);
    }

    /**
     * ����
     *
     * @param string
     * @param key    :��Կ
     * @return
     */
    public String encode(String string, String key) {
        String result = uc_authcode(string, "ENCODE", key);
        if (result.endsWith("\n"))
            result = result.substring(0, result.length() - 1);
        return result;
    }

    /**
     * ����
     *
     * @param string
     * @param key    :��Կ
     * @return
     */
    public String decode(String string, String key) {
        return uc_authcode(string, "DECODE", key);
    }

    private String uc_authcode(String string, String operation, String key) {
        return uc_authcode(string, operation, key, 0);
    }

    /**
     * �ַ�����Լ����ܺ���
     *
     * @param string string ԭ�Ļ�������
     * @param string operation ����(ENCODE | DECODE), Ĭ��Ϊ DECODE
     * @param string key ��Կ
     * @param expiry ������Ч��, ����ʱ����Ч�� ��λ �룬0 Ϊ������Ч
     * @return string ������ ԭ�Ļ��� ���� base64_encode ����������
     * @example a = authcode('abc', 'ENCODE', 'key'); b = authcode( a, 'DECODE',
     * 'key'); // b(abc)
     * <p>
     * a = authcode('abc', 'ENCODE', 'key', 3600); b = authcode('abc',
     * 'DECODE', 'key'); // ��һ��Сʱ�ڣ� b(abc)������ b Ϊ��
     */
    private String uc_authcode(String string, String operation, String key,
                               int expiry) {

        // ckey_length �����Կ���� ȡֵ 0-32;
        // ckey_length
        // ���������Կ���������������κι��ɣ�������ԭ�ĺ���Կ��ȫ��ͬ�����ܽ��Ҳ��ÿ�β�ͬ�������ƽ��Ѷȡ�
        // ckey_length ȡֵԽ�����ı䶯����Խ�����ı仯 = 16 �� ckey_length �η�
        // ckey_length ����ֵΪ 0 ʱ���򲻲��������Կ
        int ckey_length = 1;

        key = md5(key != null ? key : UC_KEY);
        String keya = md5(substr(key, 0, 16));
        String keyb = md5(substr(key, 16, 16));
        // String keyc = ckey_length > 0? ( operation.equals("DECODE") ? substr(
        // string, 0, ckey_length): substr(md5(microtime()), - ckey_length)) :
        // "";
        String keyc = "a";

        String cryptkey = keya + md5(keya + keyc);
        int key_length = cryptkey.length();

        string = operation.equals("DECODE") ? base64_decode(substr(string,
                ckey_length)) : sprintf("%010d", expiry > 0 ? expiry : 0)
                + substr(md5(string + keyb), 0, 16) + string;
        int string_length = string.length();

        StringBuffer result1 = new StringBuffer();

        int[] box = new int[256];
        for (int i = 0; i < 256; i++) {
            box[i] = i;
        }

        int[] rndkey = new int[256];
        for (int i = 0; i <= 255; i++) {
            rndkey[i] = (int) cryptkey.charAt(i % key_length);
        }

        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + box[i] + rndkey[i]) % 256;
            int tmp = box[i];
            box[i] = box[j];
            box[j] = tmp;
        }

        j = 0;
        int a = 0;
        for (int i = 0; i < string_length; i++) {
            a = (a + 1) % 256;
            j = (j + box[a]) % 256;
            int tmp = box[a];
            box[a] = box[j];
            box[j] = tmp;

            result1.append((char) (((int) string.charAt(i)) ^ (box[(box[a] + box[j]) % 256])));

        }

        if (operation.equals("DECODE")) {
            String result = result1.substring(0, result1.length());
            if ((Integer.parseInt(substr(result.toString(), 0, 10)) == 0 || Long
                    .parseLong(substr(result.toString(), 0, 10)) > 0)
                    && substr(result.toString(), 10, 16).equals(
                    substr(md5(substr(result.toString(), 26) + keyb),
                            0, 16))) {
                return substr(result.toString(), 26);
            } else {
                return "";
            }
        } else {
            return keyc + base64_encode(result1.toString()).replaceAll("=", "");
        }
    }

    public static void main(String[] args) {
        System.out.println(EncryptUtil.getInstance().encode("111", "user_sercretkey1"));
        System.out.println(EncryptUtil.getInstance().decode("aeCdgqzqG6/US6Yy3QxwOVdZLcCEfih8oOLpkh3M", "user_sercretkey1"));
    }
}