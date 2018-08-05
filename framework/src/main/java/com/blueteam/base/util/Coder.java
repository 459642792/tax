package com.blueteam.base.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Coder {

    // 生成20位系统主键
    public static String getSerialCode20() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date()) + (int) ((Math.random() * 9 + 1) * 100000);
    }

    // 生成4位随机数
    public static String getSerialCode4() {
        return (int) ((Math.random() * 9 + 1) * 1000) + "";
    }

    // 获取系统时间格式自定义
    public static String getTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    // 获取系统时间格式自定义, 秒增加自定义
    public static String getTimeAdd(String format, int second) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + second);
        return sdf.format(cal.getTime());
    }

    // 计算用户年龄
    public static int getAge(Date dateBirth) {
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(dateBirth);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayNow < dayBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    public static String NullToBlank(String value) {
        if (value == null || "".equals(value) || "null".equals(value)) {
            return "";
        } else {
            return value.trim();
        }
    }

    /**
     * 四舍五入取整
     *
     * @param temp
     * @return
     * @Description: TODO
     */
    public static String random(double temp) {
        return new BigDecimal(temp).setScale(0, BigDecimal.ROUND_HALF_UP) + "";
    }

    /**
     * @param mobile 登录账号
     * @return 加密key
     * @throws Exception
     * @Title: findEncodeKey
     * @Description: 获得百业加密key
     * @author SunXiaoYONG.Inc
     * @date 2015-12-2 下午3:20:43
     */
    public static String findEncodeKey(String mobile) throws Exception {

        return "";
    }

    /**
     * @param n 随机数量
     * @return 随机数
     * @Title: random
     * @Description: 获得自定义随机数
     * @author SunXiaoYONG.Inc
     * @date 2015-10-19 上午11:30:33
     */
    public static String customRandom(int n) {
        if (n < 1 || n > 10) {
            throw new IllegalArgumentException("cannot random " + n + " bit number");
        }
        Random ran = new Random();
        if (n == 1) {
            return String.valueOf(ran.nextInt(10));
        }
        int bitField = 0;
        char[] chs = new char[n];
        for (int i = 0; i < n; i++) {
            while (true) {
                int k = ran.nextInt(10);
                if ((bitField & (1 << k)) == 0) {
                    bitField |= 1 << k;
                    chs[i] = (char) (k + '0');
                    break;
                }
            }
        }
        return new String(chs);
    }

    /**
     * 获取一定长度的随机字符串
     *
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
