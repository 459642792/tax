package com.blueteam.base.util.json;

import jodd.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AES {

    private static Logger logger = LoggerFactory.getLogger(AES.class);

    public static byte[] encryptToByte(String content) {
        try {
            /*KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();*/

            //为了跟IOS保持一致，key直接用byte数组
            SecretKeySpec key = new SecretKeySpec(new byte[]{121, 100, 77, 52, 43, 127, 99, 73, 85, 39, 27, 103, 6, 48, 87, 68}, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decryptToByte(byte[] content) {
        try {
            /*KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();*/
            SecretKeySpec key = new SecretKeySpec(new byte[]{121, 100, 77, 52, 43, 127, 99, 73, 85, 39, 27, 103, 6, 48, 87, 68}, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    //解密并替换url中会被转义的"+"等等
    public static String decrypt(String str) {
        try {
            return new String(decryptToByte(Base64.decode(str.replace("-", "+").replace("_", "/"))), "UTF-8");
        } catch (Exception e) {
            logger.info("解密失败" + str, e);
        }
        return "";
    }

    //加密并替换url中会被转义的"+"等等
    public static String encrypt(String str) {
        try {
            byte[] encryptResult = encryptToByte(str);
            String af_str = Base64.encodeToString(encryptResult);
            return af_str == null ? null : af_str.replace("+", "-").replace("/", "_");
        } catch (Exception e) {
            logger.info("加密失败" + str, e);
        }
        return "";
    }

    public static void main(String[] args) throws Exception {
        String af_str = encrypt("{\"username\":\"yade\",\"password\":\"yade\",\"address\":\"15889688879\",\"message\":\"夏可泽测试西藏短信是否正常，向小圆美女问好\"}");
        System.out.println("加密后：" + af_str);
        System.out.println("解密后：" + decrypt(af_str));
    }

}
