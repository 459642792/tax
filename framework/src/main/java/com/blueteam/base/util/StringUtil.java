package com.blueteam.base.util;

import com.blueteam.base.exception.BusinessException;

import java.math.BigDecimal;

/**
 * String帮助类
 *
 * @author clam
 */
public class StringUtil {
    /**
     * 是否为空或者空字符串
     *
     * @param str 字符串
     * @return
     */
    public static boolean IsNullOrEmpty(String str) {
        if (str == null)
            return true;
        if (str.isEmpty())
            return true;
        return false;
    }

    public static String join(String[] arr, String symbol) {
        StringBuffer buff = new StringBuffer();
        for (String str : arr) {
            buff.append(str).append(symbol);
        }
        buff.deleteCharAt(buff.length() - 1);
        return buff.toString();
    }

    /**
     * 将分为单位的转换为元 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";
    public static String changeF2Y(String amount){
        if(!amount.matches(CURRENCY_FEN_REGEX)) {
            amount="0";
        }
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).toString();
    }
    public static Long getLongByString(String msg,String amount){
        if(amount.matches(CURRENCY_FEN_REGEX)) {
            return Long.valueOf(amount);
        }
        throw new BusinessException("对不起该"+msg+"必须全部为数字类型");
    }
}
