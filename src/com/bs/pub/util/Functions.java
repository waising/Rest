/*
 * Copyright 1999-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package com.bs.pub.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspTagException;

import net.sf.json.JSONObject;

import org.apache.taglibs.standard.resources.Resources;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

import com.bs.restframework.context.util.SystemContextUtil;
import com.bs.restframework.db.Database;
import com.bs.security.epay.server.BsSignWithMac;

/**
 * <p>JSTL Functions</p>
 * 　2011-03-16　李中俊修改
 * @author Pierre Delisle
 */

public class Functions {
	/**
	 * 取config/settings.properites配置文件
	 * @param key
	 * @return
	 */
	public static String getConfig(String key){
		Map<String,String> map=ReadConfig.getConfig();
		return map.get(key);
	}
	/**
	 * javascript转义
	 * @param key
	 * @return
	 */
	public static String escapeByJs(String key){
		return JavaScriptUtils.javaScriptEscape(key);
	}
	/** 
	 * html转义
	 * @param html
	 * @return
	 */
	public static String escapeByHtml(String html){
		return HtmlUtils.htmlEscapeHex(html);
	}
	
	/**
	 * 1和０转化成‘是’和'否'
	 */
	public static String toYesNo(String state){
		String res="";
		if("0".equals(state)){
			res="否";
		}else if(state==null || state.length()==0 || "1".equals(state)){
			res="是";
		}
		return res;
	}
	/**
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *	用户类型,【1个人、2单位、3网站、4应用域】
	 * </p>
	 *<p>参数说明：</p>
	 * @param state
	 * @return
	 *
	 * @date   创建时间：2011-3-21
	 * @author 作者：李中俊
	 */
	public static String toUserType(BigDecimal type){
		if(type==null){
			return "未知用户类型";
		}else if(type.intValue()==1){
			return "个人";
		}else if(type.intValue()==2){
			return "单位";
		}else if(type.intValue()==3){
			return "网站";
		}else if(type.intValue()==4){
			return "应用域";
		}else{
			return "未知用户类型";
		}
	}
	/**
	 * 交易状态转换
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param type
	 * @return
	 *
	 * @date   创建时间：2011-3-22
	 * @author 作者：李中俊
	 */
	public static String toTranStatus(BigDecimal type){
		if(type==null){
			return "未知状态";
		}else if(type.intValue()==1){
			return "<span style='color:green'>成功</span>";
		}else if(type.intValue()==2){
			return "<span style='color:red'>失败</span>";
		}else if(type.intValue()==3){
			return "不确定";
		}else{
			return "未知状态";
		}
	}
	/**
	 * 交易状态转换2
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param type
	 * @return
	 *
	 * @date   创建时间：2011-7-20
	 * @author 作者：张文
	 */
	public static String toTranStatusTwo(BigDecimal type,String isresp){

		if(type==null){
			return "未知状态";
		}else if(type.intValue()==1){
			return "<span style='color:green'>成功</span>";
		}else if(type.intValue()==2){
			return "<span style='color:red'>失败</span>";
		}else if(type.intValue()==3){
			if(isresp.equals("0"))
				return "未缴款";
			else
				return "待确认";
		}else{
			return "未知状态";
		}
	}
	/**
	 * 执收单位处理状态
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param type
	 * @return
	 *
	 * @date   创建时间：2011-3-23
	 * @author 作者：李中俊
	 */
	public static String toResp(BigDecimal type){
		if(type==null){
			return "未处理";
		}else if(type.intValue()==1){
			return "已处理";
		}else{
			return "未处理";
		}
	}
	/**
	 * 转为整型
	 * @param number
	 * @return
	 */
	public static String toInt(String number){
		if(number==null || number.length()==0 || !Tools.isNumeric(number)){
			number="0";
		}else{
			int i=(int)Float.parseFloat(number);
			number = String.valueOf(i);
		}
		return number;
	}
	
	/**
	 * 格式化日期
	 * @param date 日期字符串
	 * @param srcFormat 源日期格式
	 * @param format 目标日期格式
	 * @return
	 */
	public static String fmtDate(String date,String srcFormat,String aimFormat){
		return Tools.formatDate(date, srcFormat, aimFormat);
	}
	
	/**
	 * 格式化金额
	 * @param money
	 * @return
	 */
	public static String fmtMoney(String money){
		return Tools.formatNum2(money,2);
	}
	/**
	 * 对象转Json字符串
	 * @param obj
	 * @return
	 */
	public static String objToJson(Object obj){
		return JSONObject.fromObject(obj).toString();
	}
	/**
	 * md5加密
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param str 被加密的字符串
	 * @return
	 *
	 * @date   创建时间：2011-10-19
	 * @author 作者：李中俊
	 */
	public static String md5(String str){
		String res=BsSignWithMac.crtSign(str);
		return res;
	}
    //*********************************************************************
    // String capitalization

    /**
     * Converts all of the characters of the input string to upper case.
     */
    public static String toUpperCase(String input) {
        return input.toUpperCase();
    }

    /**
     * Converts all of the characters of the input string to lower case.
     */
    public static String toLowerCase(String input) {
        return input.toLowerCase();
    }
    
    //*********************************************************************
    // Substring processing
    
    public static int indexOf(String input, String substring) {
        if (input == null) input = "";
        if (substring == null) substring = "";
        return input.indexOf(substring);
    }    

    public static boolean contains(String input, String substring) {
        return indexOf(input, substring) != -1;
    }    

    public static boolean containsIgnoreCase(String input, String substring) {
        if (input == null) input = "";
        if (substring == null) substring = "";        
        String inputUC = input.toUpperCase();
        String substringUC = substring.toUpperCase();
        return indexOf(inputUC, substringUC) != -1;
    }    

    public static boolean startsWith(String input, String substring) {
        if (input == null) input = "";
        if (substring == null) substring = "";
        return input.startsWith(substring);
    }    
        
    public static boolean endsWith(String input, String substring) {
        if (input == null) input = "";
        if (substring == null) substring = "";
        int index = input.indexOf(substring);
        if (index == -1) return false;
        if (index == 0 && substring.length() == 0) return true;
        return (index == input.length() - substring.length());
    }  
    
    public static String substring(String input, int beginIndex, int endIndex) {
        if (input == null) input = "";
        if (beginIndex >= input.length()) return "";
        if (beginIndex < 0) beginIndex = 0;
        if (endIndex < 0 || endIndex > input.length()) endIndex = input.length();
        if (endIndex < beginIndex) return "";
        return input.substring(beginIndex, endIndex);
    }    
    
    public static String substringAfter(String input, String substring) {
        if (input == null) input = "";
        if (input.length() == 0) return "";
        if (substring == null) substring = "";
        if (substring.length() == 0) return input;
        
        int index = input.indexOf(substring);
        if (index == -1) {
            return "";
        } else {
            return input.substring(index+substring.length());
        }
    }    
        
    public static String substringBefore(String input, String substring) {
        if (input == null) input = "";
        if (input.length() == 0) return "";
        if (substring == null) substring = "";
        if (substring.length() == 0) return "";

        int index = input.indexOf(substring);
        if (index == -1) {
            return "";
        } else {
            return input.substring(0, index);
        }
    }    

    //*********************************************************************
    // Character replacement
    
    public static String escapeXml(String input) {
        if (input == null) return "";
        return HtmlUtils.htmlEscapeHex(input);
    }
    
    public static String trim(String input) {
        if (input == null) return "";
        return input.trim();
    }    

    public static String replace(
    String input, 
    String substringBefore,
    String substringAfter) 
    {
        if (input == null) input = "";
        if (input.length() == 0) return "";
        if (substringBefore == null) substringBefore = "";
        if (substringBefore.length() == 0) return input;
                
        StringBuffer buf = new StringBuffer(input.length());
        int startIndex = 0;
        int index;
        while ((index = input.indexOf(substringBefore, startIndex)) != -1) {
            buf.append(input.substring(startIndex, index)).append(substringAfter);
            startIndex = index + substringBefore.length();
        }
        return buf.append(input.substring(startIndex)).toString();
    }
    
    public static String[] split(
    String input, 
    String delimiters) 
    {
        String[] array;
        if (input == null) input = "";
        if (input.length() == 0) {
            array = new String[1];
            array[0] = "";
            return array;
        }
        
        if (delimiters == null) delimiters = "";

        StringTokenizer tok = new StringTokenizer(input, delimiters);
        int count = tok.countTokens();
        array = new String[count];
        int i = 0;
        while (tok.hasMoreTokens()) {
            array[i++] = tok.nextToken();
        }
        return array;
    }        
        
    //*********************************************************************
    // Collections processing
    
    public static int length(Object obj) throws JspTagException {
        if (obj == null) return 0;  
        
        if (obj instanceof String) return ((String)obj).length();
        if (obj instanceof Collection) return ((Collection)obj).size();
        if (obj instanceof Map) return ((Map)obj).size();
        
        int count = 0;
        if (obj instanceof Iterator) {
            Iterator iter = (Iterator)obj;
            count = 0;
            while (iter.hasNext()) {
                count++;
                iter.next();
            }
            return count;
        }            
        if (obj instanceof Enumeration) {
            Enumeration enum_ = (Enumeration)obj;
            count = 0;
            while (enum_.hasMoreElements()) {
                count++;
                enum_.nextElement();
            }
            return count;
        }
        try {
            count = Array.getLength(obj);
            return count;
        } catch (IllegalArgumentException ex) {}
        throw new JspTagException(Resources.getMessage("FOREACH_BAD_ITEMS"));        
    }      

    public static String join(String[] array, String separator) {
        if (array == null) return "";         
        if (separator == null) separator = "";
        
        StringBuffer buf = new StringBuffer();
        for (int i=0; i<array.length; i++) {
            buf.append(array[i]);
            if (i < array.length-1) buf.append(separator);
        }
        
        return buf.toString();
    }
    
    /**
	 * 格式化日期
	 * @param arg1 option的值
	 * @param arg2 目标值
	 * @return html select是否被选择的seleced
	 * @author 张文
	 */
    public static String isSelected(String arg1,String arg2){
    	if(arg1.equals(arg2))
    		return "selected";
    	else
    		return null;
    }
    
    /**
     * 编码加密
     * <p>函数名称：        </p>
     * <p>功能说明：
     *
     * </p>
     *<p>参数说明：</p>
     * @param arg1
     * @return
     *
     * @date   创建时间：2012-1-6
     * @author 作者：李中俊
     */
    public static String escape(String arg1){
    	return EscapeUnescape.escape(arg1);
    }
    /**
     * 编码解密
     * <p>函数名称：        </p>
     * <p>功能说明：
     * 针对jsScript方法encodeURI()加密后的字符串进行解密
     * </p>
     *<p>参数说明：</p>
     * @param arg1
     * @return
     *
     * @date   创建时间：2012-1-6
     * @author 作者：李中俊
     */
    public static String decode(String arg1){
    	return EscapeUnescape.decode(arg1);
    }
    
    /**
     * 编码解密
     * <p>函数名称：        </p>
     * <p>功能说明：
     *
     * </p>
     *<p>参数说明：</p>
     * @param arg1
     * @return
     *
     * @date   创建时间：2012-1-6
     * @author 作者：李中俊
     */
    public static String unescape(String arg1){
    	return EscapeUnescape.unescape(arg1);
    }
    /**
     * 防止请求资源缓存，如css，脚本
     * <p>函数名称：        </p>
     * <p>功能说明：
     *
     * </p>
     *<p>参数说明：</p>
     * @return
     *
     * @date   创建时间：2012-2-16
     * @author 作者：李中俊
     */
    public static String nocahe(){
    	Random rnd = new Random();
    	return "?nocahe="+String.valueOf(rnd.nextInt());
    }
}
