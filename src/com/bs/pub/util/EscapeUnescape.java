package com.bs.pub.util;

import java.io.UnsupportedEncodingException;

/**
 * 编码加密与解密
 * 
 * @author Trail Blazers
 * @company bosssoft
 * @2008-1-7
 */
public class EscapeUnescape {

    /**
     * 加密
     * 
     * @param src
     * @return
     */
    public static String escape(String src) {
	int i;
	char j;
	StringBuffer tmp = new StringBuffer();
	if (src != null && src.length() > 0) {
	    tmp.ensureCapacity(src.length() * 6);
	    for (i = 0; i < src.length(); i++) {
		j = src.charAt(i);
		if (Character.isDigit(j) || Character.isLowerCase(j)
			|| Character.isUpperCase(j))
		    tmp.append(j);
		else if (j < 256) {
		    tmp.append("%");
		    if (j < 16)
			tmp.append("0");
		    tmp.append(Integer.toString(j, 16));
		} else {
		    tmp.append("%u");
		    tmp.append(Integer.toString(j, 16));
		}
	    }
	}
	return tmp.toString();
    }

    /**
     * 解密
     * 
     * @param src
     * @return
     */
    public static String unescape(String src) {
	if (src != null && src.length() > 0) {
	    StringBuffer tmp = new StringBuffer();
	    tmp.ensureCapacity(src.length());
	    int lastPos = 0, pos = 0;
	    char ch;
	    while (lastPos < src.length()) {
		pos = src.indexOf("%", lastPos);
		if (pos == lastPos) {
		    if (src.charAt(pos + 1) == 'u') {
			ch = (char) Integer.parseInt(src.substring(pos + 2,
				pos + 6), 16);
			tmp.append(ch);
			lastPos = pos + 6;
		    } else {
			ch = (char) Integer.parseInt(src.substring(pos + 1,
				pos + 3), 16);
			tmp.append(ch);
			lastPos = pos + 3;
		    }
		} else {
		    if (pos == -1) {
			tmp.append(src.substring(lastPos));
			lastPos = src.length();
		    } else {
			tmp.append(src.substring(lastPos, pos));
			lastPos = pos;
		    }
		}
	    }
	    return tmp.toString();
	} else {
	    return "";
	}
    }

    /**
     * 针对jsScript方法encodeURI()加密后的字符串进行解密
     * 
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decode(String str){
	if (str != null && str.length() > 0) {
	    try {
			return java.net.URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	} else {
	    return "";
	}
    }
    public static String escapeUTF8(String str){
    	if (str != null && str.length() > 0) {
    	    try {
    			return java.net.URLEncoder.encode(str, "utf-8");
    		} catch (UnsupportedEncodingException e) {
    			e.printStackTrace();
    			return str;
    		}
    	} else {
    	    return "";
    	}
    }
    public static void main(String[] args) {
//    	System.out.println(EscapeUnescape.decode("%E9%BB%91%E9%BE%99%E6%B1%9F%E7%9C%81%E9%A1%B6%E7%BA%A7%E8%B4%A2%E6%94%BF"));
	    System.out.println(EscapeUnescape.unescape("%u6821%u9a8c%u7801%u9a8c%u8bc1%u6709%u8bef%21"));
    }
}