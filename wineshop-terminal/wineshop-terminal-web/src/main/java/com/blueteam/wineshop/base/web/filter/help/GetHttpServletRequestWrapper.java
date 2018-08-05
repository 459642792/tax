package com.blueteam.wineshop.base.web.filter.help;

import jodd.util.URLDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private String CHARSET = "UTF-8";
    // 定义了一个成员变量，用来保存构造函数传入的requset对象
    private HttpServletRequest NEW_REQUEST = null;

    // 定义一个标记，用来标注：当前requset中，请求参数，是否已经编码过了
    private boolean flag = false;
    private static Logger logger = LoggerFactory.getLogger(GetHttpServletRequestWrapper.class);

    public GetHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public GetHttpServletRequestWrapper(HttpServletRequest request,
                                        String charset) {
        super(request);
        NEW_REQUEST = request;
        this.CHARSET = CHARSET;
    }

    /**
     * 利用反射获取所有请求参数
     * * @author xiaojiang 2017年4月24日
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        String method = this.NEW_REQUEST.getMethod();
        // post请求
        if ("post".equalsIgnoreCase(method)) {
            try {
                NEW_REQUEST.setCharacterEncoding(CHARSET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Map<String, String[]> map = this.NEW_REQUEST.getParameterMap();
            return map;

        } else if ("get".equalsIgnoreCase(method)) {
            Map<String, String[]> map = this.NEW_REQUEST.getParameterMap();
            if (flag) {
                return map;
            }
            if (map == null) {
                return super.getParameterMap();
            } else {
                Set<String> key = map.keySet();
                for (String string : key) {
                    String[] value = map.get(string);
                    for (int i = 0; i < value.length; i++) {
                        try {
                            if (null != value[i] && !value[i].isEmpty()) {
                                //转码
                                boolean flag = isMessyCode(value[i]);

                                logger.info("===============flag=====" + flag);
                                if (flag) {
                                    String encoding = getEncoding(value[i]);
                                    String string2 = new String(value[i].getBytes(encoding), CHARSET);
                                    value[i] = URLDecoder.decode(string2);
                                } else {
                                    String string2 = new String(value[i].getBytes(CHARSET), CHARSET);
                                    value[i] = URLDecoder.decode(string2);
                                }
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                flag = true;
                return map;
            }
        } else {
            return super.getParameterMap();
        }
    }

    @Override
    public String getParameter(String name) {
        String[] values = this.getParameterValues(name);
        if (values == null) {
            return super.getParameter(name);
        } else {
            return values[0];
        }
    }

    @Override
    public String[] getParameterValues(String name) {
        // 通过map集合获取参数
        Map<String, String[]> map = this.getParameterMap();
        if (map == null) {
            return super.getParameterValues(name);
        } else {
            String[] strings = map.get(name);
            return strings;
        }
    }

    /**
     * 判断字符类型
     *
     * @param str
     * @return * @author xiaojiang 2017年4月24日
     */
//	public String getEncoding(String str){
//		String	encode = "UTF-8";
//		try {
//			switch (str.substring(0).getBytes().length) {
//			case 0:
//				encode =  "GB2312";
//				break;
//			case 1:
//				encode =  "GBK";
//				break;
//			case 3:
//				encode =  "UTF8";
//				break;
//			case 4:
//				encode =  "UNICODE";
//				break;
//			case 9:
//				encode =  "ISO-8859-1";
//				break;
//			default:
//				break;
//			}
//		} catch (Exception exception) {
//		}
//		return encode;
//	}
    public static String getEncoding(String str) {
        logger.info("===============getEncoding(=====" + str);
        System.out.println("===============getEncoding(=====" + str);
        try {
            String encode = "ISO-8859-1";
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
            encode = "UTF-8";
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
            encode = "GBK";
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
            encode = "GB2312";
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception exception) {
        }
        return "";
    }

    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4 || Float.compare(result, Float.NaN) == 0) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//	        System.out.println(getEncoding("??????VS??????")+ new String("??????VS???".getBytes("gbk"),  "u-8"));
        System.out.println(URLDecoder.decode("%E9%AB%98%E6%96%B0%E5%8C%BA%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%EF%BC%9F%EF%BC%9Fwww"));

    }
}  