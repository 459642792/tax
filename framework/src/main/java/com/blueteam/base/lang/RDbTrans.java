package com.blueteam.base.lang;

import com.blueteam.base.exception.BusinessException;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * 数字处理工具类
 * Created by  NastyNas on 2017/11/8.
 */
public class RDbTrans {
    /**
     * 将数据库中价格单位为分的 number型、string型数据转化成 单位为元并保留两位小数的字符串
     *
     * @param price
     * @return
     */
    public static String asShowPrice(Object price) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String showPrice;
        try {
            if (price == null) {
                return null;
            }
            if (!String.class.isInstance(price) && !Number.class.isInstance(price)) {
                throw new BusinessException();
            }
            showPrice = df.format(Double.parseDouble(price.toString()) / 100);
        } catch (Exception e) {
            throw new BusinessException("价格转化异常");
        }
        return showPrice;
    }

    public static void main(String[] args) {
        String s = "0.01";
        asDbPrice(s);
    }


    /**
     * 将页面保留两位小数的单位为元的double型或字符串型价格转换成long型数据库价格
     *
     * @param price
     * @return
     */
    public static Long asDbPrice(Object price) {
        Long dbPrice;
        try {
            if (price == null) {
                return null;
            }
            if (!String.class.isInstance(price) && !Number.class.isInstance(price)) {
                throw new BusinessException();
            }
            Double yuanPrice = Double.parseDouble(price.toString());
            Double fenPrice = yuanPrice * 100;
            dbPrice = fenPrice.longValue();
        } catch (Exception e) {
            throw new BusinessException("价格转化异常");
        }
        return dbPrice;
    }


    /**
     * 将数据库中的时间转化成格式为：yyyy-MM-dd HH:mm:ss的页面展示时间
     *
     * @param date
     * @return
     */
    public static String asShowDate(Object date) {
        String showDate;
        try {
            if (date == null) {
                return null;
            }
            if (String.class.isInstance(date)) {
                showDate = Dates.format(Dates.parse(date.toString()));
            } else if (Date.class.isInstance(date)) {
                showDate = Dates.format((Date) date);
            } else if (Long.class.isInstance(date)) {
                showDate = Dates.format((long) date);
            } else {
                throw new BusinessException();
            }
        } catch (Exception e) {
            throw new BusinessException("时间转化异常");
        }
        return showDate;
    }
}
