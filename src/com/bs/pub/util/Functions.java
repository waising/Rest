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
 * ��2011-03-16�����п��޸�
 * @author Pierre Delisle
 */

public class Functions {
	/**
	 * ȡconfig/settings.properites�����ļ�
	 * @param key
	 * @return
	 */
	public static String getConfig(String key){
		Map<String,String> map=ReadConfig.getConfig();
		return map.get(key);
	}
	/**
	 * javascriptת��
	 * @param key
	 * @return
	 */
	public static String escapeByJs(String key){
		return JavaScriptUtils.javaScriptEscape(key);
	}
	/** 
	 * htmlת��
	 * @param html
	 * @return
	 */
	public static String escapeByHtml(String html){
		return HtmlUtils.htmlEscapeHex(html);
	}
	
	/**
	 * 1�ͣ�ת���ɡ��ǡ���'��'
	 */
	public static String toYesNo(String state){
		String res="";
		if("0".equals(state)){
			res="��";
		}else if(state==null || state.length()==0 || "1".equals(state)){
			res="��";
		}
		return res;
	}
	/**
	 * <p>�������ƣ�        </p>
	 * <p>����˵����
	 *	�û�����,��1���ˡ�2��λ��3��վ��4Ӧ����
	 * </p>
	 *<p>����˵����</p>
	 * @param state
	 * @return
	 *
	 * @date   ����ʱ�䣺2011-3-21
	 * @author ���ߣ����п�
	 */
	public static String toUserType(BigDecimal type){
		if(type==null){
			return "δ֪�û�����";
		}else if(type.intValue()==1){
			return "����";
		}else if(type.intValue()==2){
			return "��λ";
		}else if(type.intValue()==3){
			return "��վ";
		}else if(type.intValue()==4){
			return "Ӧ����";
		}else{
			return "δ֪�û�����";
		}
	}
	/**
	 * ����״̬ת��
	 * <p>�������ƣ�        </p>
	 * <p>����˵����
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @param type
	 * @return
	 *
	 * @date   ����ʱ�䣺2011-3-22
	 * @author ���ߣ����п�
	 */
	public static String toTranStatus(BigDecimal type){
		if(type==null){
			return "δ֪״̬";
		}else if(type.intValue()==1){
			return "<span style='color:green'>�ɹ�</span>";
		}else if(type.intValue()==2){
			return "<span style='color:red'>ʧ��</span>";
		}else if(type.intValue()==3){
			return "��ȷ��";
		}else{
			return "δ֪״̬";
		}
	}
	/**
	 * ����״̬ת��2
	 * <p>�������ƣ�        </p>
	 * <p>����˵����
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @param type
	 * @return
	 *
	 * @date   ����ʱ�䣺2011-7-20
	 * @author ���ߣ�����
	 */
	public static String toTranStatusTwo(BigDecimal type,String isresp){

		if(type==null){
			return "δ֪״̬";
		}else if(type.intValue()==1){
			return "<span style='color:green'>�ɹ�</span>";
		}else if(type.intValue()==2){
			return "<span style='color:red'>ʧ��</span>";
		}else if(type.intValue()==3){
			if(isresp.equals("0"))
				return "δ�ɿ�";
			else
				return "��ȷ��";
		}else{
			return "δ֪״̬";
		}
	}
	/**
	 * ִ�յ�λ����״̬
	 * <p>�������ƣ�        </p>
	 * <p>����˵����
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @param type
	 * @return
	 *
	 * @date   ����ʱ�䣺2011-3-23
	 * @author ���ߣ����п�
	 */
	public static String toResp(BigDecimal type){
		if(type==null){
			return "δ����";
		}else if(type.intValue()==1){
			return "�Ѵ���";
		}else{
			return "δ����";
		}
	}
	/**
	 * תΪ����
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
	 * ��ʽ������
	 * @param date �����ַ���
	 * @param srcFormat Դ���ڸ�ʽ
	 * @param format Ŀ�����ڸ�ʽ
	 * @return
	 */
	public static String fmtDate(String date,String srcFormat,String aimFormat){
		return Tools.formatDate(date, srcFormat, aimFormat);
	}
	
	/**
	 * ��ʽ�����
	 * @param money
	 * @return
	 */
	public static String fmtMoney(String money){
		return Tools.formatNum2(money,2);
	}
	/**
	 * ����תJson�ַ���
	 * @param obj
	 * @return
	 */
	public static String objToJson(Object obj){
		return JSONObject.fromObject(obj).toString();
	}
	/**
	 * md5����
	 * <p>�������ƣ�        </p>
	 * <p>����˵����
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @param str �����ܵ��ַ���
	 * @return
	 *
	 * @date   ����ʱ�䣺2011-10-19
	 * @author ���ߣ����п�
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
	 * ��ʽ������
	 * @param arg1 option��ֵ
	 * @param arg2 Ŀ��ֵ
	 * @return html select�Ƿ�ѡ���seleced
	 * @author ����
	 */
    public static String isSelected(String arg1,String arg2){
    	if(arg1.equals(arg2))
    		return "selected";
    	else
    		return null;
    }
    
    /**
     * �������
     * <p>�������ƣ�        </p>
     * <p>����˵����
     *
     * </p>
     *<p>����˵����</p>
     * @param arg1
     * @return
     *
     * @date   ����ʱ�䣺2012-1-6
     * @author ���ߣ����п�
     */
    public static String escape(String arg1){
    	return EscapeUnescape.escape(arg1);
    }
    /**
     * �������
     * <p>�������ƣ�        </p>
     * <p>����˵����
     * ���jsScript����encodeURI()���ܺ���ַ������н���
     * </p>
     *<p>����˵����</p>
     * @param arg1
     * @return
     *
     * @date   ����ʱ�䣺2012-1-6
     * @author ���ߣ����п�
     */
    public static String decode(String arg1){
    	return EscapeUnescape.decode(arg1);
    }
    
    /**
     * �������
     * <p>�������ƣ�        </p>
     * <p>����˵����
     *
     * </p>
     *<p>����˵����</p>
     * @param arg1
     * @return
     *
     * @date   ����ʱ�䣺2012-1-6
     * @author ���ߣ����п�
     */
    public static String unescape(String arg1){
    	return EscapeUnescape.unescape(arg1);
    }
    /**
     * ��ֹ������Դ���棬��css���ű�
     * <p>�������ƣ�        </p>
     * <p>����˵����
     *
     * </p>
     *<p>����˵����</p>
     * @return
     *
     * @date   ����ʱ�䣺2012-2-16
     * @author ���ߣ����п�
     */
    public static String nocahe(){
    	Random rnd = new Random();
    	return "?nocahe="+String.valueOf(rnd.nextInt());
    }
}
