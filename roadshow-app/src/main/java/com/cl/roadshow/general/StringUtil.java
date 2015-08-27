package com.cl.roadshow.general;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符串工具
 * 
 */
public class StringUtil {
    
    private static final Logger log = LoggerFactory.getLogger("console");
    
    public static void main(String[] args) {
        String strOld = "jin" + "\r"
                + "sdf" + "\n"
                 + "dd" + "\r\n"
                + "df";
        log.info(strOld);
        log.info(replaceEnter(strOld));
        
        String s = "o check\"],\"baseInfo\":[\"http://baidu.com?tt=aa&limit=20&sort=smart&";
        
        log.info(urlDecode(urlEncode(s)));
    }
    
    /**
     * 替换回车换行符
     * @param str
     * @return
     */
    public static String replaceEnter(String str) {
        if(str == null || "".equals(str.trim())) {
            return str;
        }
        Pattern p = Pattern.compile("\r|\n");
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
    
    /**
     * url encode
     * @param str
     * @return
     */
    public static String urlEncode(String str) {       
        try {
            return URLEncoder.encode(str,"utf-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
    
    /**
     * url decode
     * @param str
     * @return
     */
    public static String urlDecode(String str) {       
        try {
            return URLDecoder.decode(str,"utf-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
    
    
    /**
     * 判断空字符串
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
}
