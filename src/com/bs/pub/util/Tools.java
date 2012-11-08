package com.bs.pub.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.bs.restframework.util.DesUtils;

/**
 * ͨ�÷���<br>
 * 
 * 
 * @author Bill 2008.4.3
 */
public class Tools {

    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String formatJson(String jsonVal){
    	jsonVal=Tools.NullToString(jsonVal);
    	jsonVal=jsonVal.replace("\"", "\\\"");
    	jsonVal=jsonVal.replace("'", "\'");
    	Pattern p = Pattern.compile("\r|\n");
    	jsonVal=p.matcher(jsonVal).replaceAll("");
    	return jsonVal;
    }
    /**
     * ����ֵת�����ַ���
     * 
     * @param str
     *            String
     * @return String
     */
    public static String NullToString(String str) {
	if (str == null || str.equals("null")) {
	    str = "";
	}
	return str;
    }
    public static String NullToZero(String str){
    	if (str == null || str.equals("null") || str.trim().length()==0) {
    	    str = "0";
    	}
    	return str;
    }
    public static String NullToString2(String str) {
	if (str == null || str.equals("null")) {
	    str = "&nbsp;";
	}
	return str;
    }
    public static String NullToString3(Object str) {
    	if (str == null){
    	    return "&nbsp;";
    	}else{
    		return str.toString();
    	}
    }

    public static String trim(String str) {
	if (str == null || str.equals("null")) {
	    str = "";
	}
	return str.trim();
    }
    public static String trim(Object str){
    	if(str!=null && str instanceof String){
    		return trim(str.toString());
    	}else{
    		return "";
    	}
    }

    public static String trimN(String str) {
	if (str == null || str.equals("null") || str.trim().length() == 0) {
	    str = "0";
	}
	return str.trim();
    }

    /**
     * �ַ����Ƿ�Ϊ��
     * 
     * @param str
     * @return
     */
    public static boolean isNullStr(String str) {
	boolean b = true;
	if (str != null && str.trim().length() > 0 && !"null".equals(str))
	    b = false;
	return b;
    }
    /**
     * ������
     * @param str
     * @return
     */
    public static boolean isint(String str){
    	boolean b =false;
    	if(!Tools.isNullStr(str)){
    		b=Tools.check(str, "^[0-9]*[1-9][0-9]*$");
    	}
    	return b;
    }
    /**
     * ���ڵ���0������
     * @param str
     * @return
     */
    public static boolean isZS(String str){
    	boolean b =false;
    	if(!Tools.isNullStr(str)){
    		b=Tools.check(str, "^[0-9]*[0-9][0-9]*$");
    	}
    	return b;
    }
    
    /**
     * ��ʽ��С��,ȥ��С����Ϊ���β��
     * 
     * @param number
     * @return
     */
    public static String delDecimal(String number) {
	if (!Tools.isNullStr(number)) {
	    if (Tools.isNumeric(number)) {
		int begin = number.indexOf(".");
		String end = "";
		if (begin > 0) {// ��С��
		    end = number.substring(begin + 1, number.length());
		    if (end != null && end.length() > 0) {
			while (end.endsWith("0") && end.length() > 0) {
			    end = end.substring(0, end.length() - 1);
			    if (end == null)
				end = "";
			}
		    } else {
			end = "";
		    }
		    if (end.equals("."))
			end = "";
		    number = number.substring(0, begin + 1) + end;
		}
	    }
	} else {
	    number = Tools.NullToString(number);
	}

	return number;
    }

    /**
     * ��ʽ�������ַ�����С���㱣����λ
     */
    public static String formatNum(String numStr) {
	// numStr.split("e")
	if (Tools.isNumeric(numStr)
		|| (!Tools.isNullStr(numStr) && numStr.indexOf("E") > 0)) {
	    // DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
	    // df.applyPattern("#####################################0.00");
	    // return df.format(Float.parseFloat(numStr));
	    BigDecimal bd = new BigDecimal(numStr);
	    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
	    if (bd.intValue() != 0)
		return String.valueOf(bd);
	    else {
		return "";
	    }
	} else {
	    return "";
	}
    }

    /*
     * ###,###.######
     */
    public static String formatNum2(String numStr, int t) {

	if (!Tools.isNullStr(numStr)
		&& (Tools.isNumeric(numStr) || numStr.indexOf("E") > 0)
		&& Double.parseDouble(numStr) != 0) {
	    String format = "";
	    int ii = numStr.trim().indexOf(".");
	    // String tmp[] = numStr.trim().split(".");
	    String f = "";
	    if (t == 1 && ii > 0) {
		String ss = numStr.substring(ii + 1);
		if (ss.length() >= 1) {
		    if (ss.length() < 6) {
			f = ".";
			for (int j = 0; j < ss.length(); j++) {
			    f += "0";
			}
		    } else {
			f = ".000000";
		    }
		}
	    }
	    if (t == 1) {
		format = "##,###,###,###,##0" + f;
	    } else if (t == 2) {
		format = "##,###,###,###,##0.00";
	    }
	    DecimalFormat fmt = new DecimalFormat(format);
	    numStr = fmt.format(Double.valueOf(numStr));
	    if (numStr.endsWith("."))
		numStr = numStr.substring(0, numStr.indexOf("."));
	    if (t == 1) {
		numStr = Tools.delDecimal(numStr);
		if (numStr.endsWith("."))
		    numStr = numStr.substring(0, numStr.indexOf("."));
		return numStr;
	    } else {
		return (numStr);
	    }
	} else {
	    return "0.00";
	}
    }

    public static String nullToZero(String str) {
	if (Tools.isNullStr(str)) {
	    return "0";
	} else {
	    if (Tools.isNumeric(str))
		return str;
	    else
		return "0";
	}
    }

    /**
     * ��ȡ��ǰʱ��,��ʽ��yyyy-MM-dd HH:mm:ss
     * 
     * @return String
     */
    public static String getDayTime() {
    	return getDate("yyyy-MM-dd HH:mm:ss");
    }

    /** 
     * ȡ��ǰ����,���ڸ�ʽ��yyyy-MM-dd
     * * */
    public static String getDate() {
	return getDate("yyyy-MM-dd");
    }
    /**
     * ȡ��ǰ���ڣ����ڸ�ʽ��yyyyMMdd
     * @return
     */
    public static String getDate2() {
    	return getDate("yyyyMMdd");
    }
    /**
     * ��ȡ����
     * <p>�������ƣ�        </p>
     * <p>����˵����
     *
     * </p>
     *<p>����˵����</p>
     * @param fromat
     * @return
     *
     * @date   ����ʱ�䣺2012-6-6
     * @author ���ߣ����п�
     */
    public static String getDate(String fromat){
    	Calendar calender = Calendar.getInstance();
    	SimpleDateFormat format = new SimpleDateFormat(fromat); // HHһ��Ҫ��д��Сд�Ļ������12Сʱ������
    	format.setLenient(false);
    	String datetime = format.format(calender.getTime());
    	return datetime;
    }
    /**
     * �������һ��
     * @param sDate1
     * @return
     */
	public static Date getLastDayOfMonth(Date sDate1) {
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(sDate1);
		final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		return lastDate;
	}
    /** 
     * ��ǰ���ڼ�
     * * */
    public static String getWeek() {
	Date now = new Date();
	DateFormat df = new SimpleDateFormat("EEEEE");
	String s = df.format(now);
	return s;
    }

    /** 
     * ����date�����������ڼ�
     * * */
    public static String getWeek(String date) {
	if (!Tools.isNullStr(date)) {
	    Date now = Tools.stringToDate(date, "");
	    DateFormat df = new SimpleDateFormat("EEEEE");
	    String s = df.format(now);
	    return s;
	} else {
	    return date;
	}
    }

    /**
     * �����ַ�תΪHtml("&","<",">","\t","\r\n","\n"," ","'","\\")
     * 
     * @param s
     * @return
     */
    public static String toHtml(String s) {
	s = Replace(s, "&", "&amp;");
	s = Replace(s, "<", "&lt;");
	s = Replace(s, ">", "&gt;");
	s = Replace(s, "\t", "    ");
	s = Replace(s, "\r\n", "\n");
	s = Replace(s, "\n", "&lt;br&gt;");
	s = Replace(s, " ", "&nbsp;");
	s = Replace(s, "'", "&#39;");
	s = Replace(s, "\\", "&#92;");
	s = Replace(s, "\"", "&#34;");
	return s;
    }

    // ��
    public static String unHtml(String s) {
	s = Replace(s, "<br>", "\n");
	s = Replace(s, "&nbsp;", " ");
	s = Replace(s, "&amp;", "&");
	s = Replace(s, "&lt;", "<");
	s = Replace(s, "&gt;", ">");
	s = Replace(s, "    ", "\t");
	s = Replace(s, "\n", "\r\n");
	s = Replace(s, "&lt;br&gt;", "\n");
	s = Replace(s, "&#39;", "'");
	s = Replace(s, "&#92;", "\\");
	s = Replace(s, "&#34;", "\"");
	return s;
    }

    // Replace
    public static String Replace(String source, String oldString,
	    String newString) {
	if (source == null)
	    return null;
	StringBuffer output = new StringBuffer();
	int lengOfsource = source.length();
	int lengOfold = oldString.length();
	int posStart = 0;
	int pos;
	while ((pos = source.indexOf(oldString, posStart)) >= 0) {
	    output.append(source.substring(posStart, pos));
	    output.append(newString);
	    posStart = pos + lengOfold;
	}
	if (posStart < lengOfsource) {
	    output.append(source.substring(posStart));
	}
	return output.toString();
    }

    /**
     * ȥ��HTMLԪ��
     * 
     * @param input
     * @param length
     * @return
     */
    public static String removeHTML(String input, int length) {
	if (input == null || input.trim().equals("")) {
	    return "";
	}
	// ȥ������htmlԪ��,
	String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
		"<[^>]*>", "");
	str = str.replaceAll("[(/>)<]", "");
	int len = str.length();
	if (len <= length || length <= 0) {
	    return str;
	} else {
	    str = str.substring(0, length);
	    str += "......";
	}
	return str;
    }

    public static boolean check(String str, String ptn) {
	Pattern pattern = Pattern.compile(ptn);
	if (str != null)
	    return pattern.matcher(str).matches();
	else
	    return false;
    }

    /**
     * �Ƿ�Ϊ��ĸ�����֡��»������,�ұ�����ĸ���»��߿�ͷ,����С�ڵ���30
     */
    public static boolean isUserName(String str) {
    	if(str==null || str.length()==0) return false;
    	if(str.getBytes().length>30) return false;
    	return Tools.check(str, "^[a-zA-Z_0-9][a-zA-Z0-9|_][@][\\.]{0,30}$");
    }

    /**
     * �Ƿ�Ϊ����
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {

	boolean b = false;
	// return Tools.check(str, "[0-9]*");
	// ^[-]?\d+[.]?\d*$
	if (str != null) {
	    int e = str.indexOf("E");
	    if (e > 0) {
		String s1 = str.substring(0, e);
		String s2 = str.substring(e + 1);
		if (Tools.check(s1, "^[-]?\\d+[.]?\\d*$")
			&& Tools.check(s2, "^[-]?\\d+[.]?\\d*$")) {
		    b = true;
		}
	    } else {
		b = Tools.check(str, "^[-]?[0-9]+[.]?\\d*$");
	    }
	}
	return b;

    }
    /**
     * ��֤��xxxxxxxxxxxx.xx12λ+С��λ+2λС����
     * @param money
     * @return
     */
    public static boolean checkMoney(String money){
    	return Tools.check(money, "[0-9]{1,12}\\.[0-9]{2}");
    }

    /**
     * email�Ƿ�Ϸ�
     * 
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
	return Tools.check(email,
		"\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    }

    /**
     * URL�Ƿ�Ϸ�
     * 
     * @param url
     * @return
     */
    public static boolean checkURL(String url) {
    	return Tools.check(url,"(http|ftp|https)://[\\w]+(.[\\w]+)([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])");
    }

    /**
     * �ʺźϷ���֤ ��ĸ��ͷ������1-29�ֽڣ�������ĸ�����»���
     * 
     * @param id
     * @return
     */
    public static boolean checkID(String id) {
    	if(id==null || id.length()==0) return false;
    	if(id.getBytes().length>29) return false;
    	return Tools.check(id, "^[a-zA-Z_][a-zA-Z0-9_]{0,29}$");
    }

    /**
     * ���ڵ绰����
     * 
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
	// return Tools.check(phone, "\\d{3}-\\d{8}|\\d{4}-\\d{7}");
	return Tools.check(phone, "(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}");
    }
   /**  
    * �ֻ�������֤,11λ����֪����ϸ���ֻ�����Σ�ֻ����֤��ͷ������1��λ��  
    * */  
   public static boolean checkCellPhone(String cellPhoneNr)   
   {   
//       String reg="^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";   
//       String reg2="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	   String reg = "^\\d{11}$";//"^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\\d{8}$";
	   // || Tools.check(cellPhoneNr,reg2)
       return Tools.check(cellPhoneNr,reg);   
   }  
    /**
     * QQ����֤
     * 
     * @param qq
     * @return
     */
    public static boolean checkQQ(String qq) {
	return Tools.check(qq, "[1-9][0-9]{4,}");
    }

    public static boolean checkIP(String ip) {
	return Tools.check(ip, "\\d+\\.\\d+\\.\\d+\\.\\d+");
    }

    /**
     * ���֤����֤
     * 
     * @param card
     * @return
     */
    public static boolean checkCard(String card) {
	return Tools.check(card, "^\\d{15}|\\d{17}[\\dx]$");
    }

    /**
     * ��֤���ֽ��ַ�
     * 
     * @param byteStr
     * @return
     */
    public static boolean checkSingleByte(String byteStr) {
	return Tools.check(byteStr, "^[\\x00-\\xff]+$");
    }

    /**
     * ƥ������ ��ʽ:yyyy-MM-dd
     * 
     * @param date
     * @return
     */
    public static boolean checkDate(String date) {
	return !Tools.isNullStr(date)
		&& Tools
			.check(date,
				"^\\d{4}-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3([0|1])))$");
    }

    /**
     * ƥ������ ��ʽ:yyyyMMdd
     * 
     * @param date
     * @return
     */
    public static boolean checkDate2(String date) {
	return !Tools.isNullStr(date)
		&& Tools
			.check(date,
				"^\\d{4}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3([0|1])))$");
    }
    /**
     * ƥ������ ��ʽ:yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * @return
     */
    public static boolean checkDate3(String date) {
	return !Tools.isNullStr(date)
		&& Tools
			.check(date,
				"^\\d{4}-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3([0|1]))) ((0[0-9])|([1-2][0-9])):((0[0-9])|([1-5][0-9])):((0[0-9])|([1-5][0-9]))$");
    }
    /**
     * ƥ������ ��ʽ:yyyy/MM/dd
     * @param date
     * @return
     */
    public static boolean checkDate4(String date) {
    	return !Tools.isNullStr(date)
		&& Tools
			.check(date,
				"^\\d{4}/((0[1-9])|(1[0-2]))/((0[1-9])|([1-2][0-9])|(3([0|1])))$");
    }
    /**
     * ��ȡ����
     * 
     * @param url
     * @param paramterName
     * @return
     */
    public static String getParamter(String url, String paramterName) {
	String res = "";
	if (url != null && !url.trim().equals("") && paramterName != null
		&& !paramterName.trim().equals("")) {
	    String regEx = "(^|&)" + paramterName + "=([^&]*)(&|$)";
	    Pattern pat = Pattern.compile(regEx); 
	    Matcher m = pat.matcher(url);
	    if (m.find()) {
		// System.out.println(m.group(2));
		res = m.group(2);
	    }
	}
	return res;
    }

    /**
     * ��ȡ����,���ؼ���
     */
    public static List<String> getParamters(String url, String paramterName) {
	if (url != null) {
	    String tmp[] = url.split("&");
	    ArrayList<String> ary = new ArrayList<String>();
	    if (tmp != null) {
		for (int i = 0; i < tmp.length; i++) {
		    String t[] = tmp[i].split("=");
		    if (t != null && t.length == 2) {
			if (t[0].equals(paramterName)) {
			    ary.add(t[1]);
			}
		    }
		}
	    }

	    return ary;
	}
	return null;
    }

    /**
     * �ַ�����ת�� gbk TO UTF-8
     * 
     * @param para
     *            String
     * @return String
     */
    public static String getGBKtoUTF(String para) {
	if (para == null || "".equals(para)) {
	    return "";
	}
	para = para.trim();
	String strtmp = "";
	try {
	    strtmp = new String(para.getBytes("GBK"), "utf-8");
	} catch (Exception uee) {
	    uee.printStackTrace();
	    return "";
	}
	return strtmp;
    }

    /**
     * ���غ���
     * 
     * @param date
     *            ����
     * @return ���غ���
     */
    public static long getMillis(java.util.Date date) {
	java.util.Calendar c = java.util.Calendar.getInstance();
	c.setTime(date);
	return c.getTimeInMillis();
    }

    /**
     * �������
     * 
     * @param date
     *            ����
     * @param date1
     *            ����
     * @return ��������������
     */
    public static int diffDate(java.util.Date date, java.util.Date date1) {
	return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

//    public static int diffSec(java.util.Date date, java.util.Date date1) {
//	return (int) ((getMillis(date) - getMillis(date1)) / (1000));
//    }
    
    /**
     * �����������������
     * @param date 
     * @param date1
     */
    public static float diffSec(java.util.Date date, java.util.Date date1) {
    	return (float) (((float)(getMillis(date) - getMillis(date1))) / (1000));
    }
    /**
     * ������������ط���
     * <p>�������ƣ�        </p>
     * <p>����˵����
     *
     * </p>
     *<p>����˵����</p>
     * @param date
     * @param date1
     * @return
     *
     * @date   ����ʱ�䣺2011-11-28
     * @author ���ߣ����п�
     */
    public static int diffMinute(java.util.Date date, java.util.Date date1) {
    	return (int) ((getMillis(date) - getMillis(date1)) / (60 * 1000));
    }

    /**
     * �������
     * 
     * @param date
     *            ����
     * @param day
     *            ����
     * @return ������Ӻ������
     */
    public static java.util.Date addDate(java.util.Date date, int day) {
	java.util.Calendar c = java.util.Calendar.getInstance();
	c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
	return c.getTime();
    }

    /*
     * �������
     */
    public static java.util.Date decDate(java.util.Date date, int day) {
	java.util.Calendar c = java.util.Calendar.getInstance();
	c.setTimeInMillis(getMillis(date) - ((long) day) * 24 * 3600 * 1000);
	return c.getTime();
    }

    /**
     * ���ݸ�ʽ�õ���ʽ���������
     * 
     * @param currDate
     *            Ҫ��ʽ��������
     * @param format
     *            ���ڸ�ʽ����yyyy-MM-dd
     * @see java.text.SimpleDateFormat#parse(java.lang.String)
     * @return Date ���ظ�ʽ��������ڣ���ʽ�ɲ���<code>format</code>���壬��yyyy-MM-dd����2006-02-15
     */
    public static java.util.Date getFormatDate(String currDate, String format) {
	SimpleDateFormat dtFormatdB = null;
	try {
	    dtFormatdB = new SimpleDateFormat(format);
	    return dtFormatdB.parse(currDate);
	} catch (Exception e) {
	    dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
	    try {
		return dtFormatdB.parse(currDate);
	    } catch (Exception ex) {
	    }
	}
	return null;
    }

    /*
     * public static String formatDate(String date, String sourceFormat, String
     * aimFormat) { SimpleDateFormat sdf = null; try { sdf = new
     * SimpleDateFormat(aimFormat); return sdf.format(sdf.parse(date)); } catch
     * (Exception e) { e.printStackTrace(); } return null; }
     */
    public static String formatDate(String date, String sourceFormat,
	    String aimFormat) {
		if (Tools.isNullStr(date))
		    return date;
		SimpleDateFormat sdfa = null;
		SimpleDateFormat sdfs = null;
		try {
		    sdfa = new SimpleDateFormat(aimFormat);
		    sdfs = new SimpleDateFormat(sourceFormat);
		    date = sdfa.format(sdfs.parse(date));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return date;
    }

    /**
     * �����ֶ����ֶ��ַ���(�Զ��Ÿ���)�еĶ���λ��(�±��0����)
     * 
     * @param fieldStr
     * @param fieldName
     * @return
     */
    public static int findFieldIndex(String fieldStr, String fieldName) {
	int i = fieldStr.indexOf(fieldName);
	if (i >= 0) {
	    String substring = fieldStr.substring(0, i);
	    if (substring.trim().length() > 0 && substring.indexOf(",") > 0) {
		return substring.split(",").length;
	    } else {
		return 0;
	    }
	} else {
	    return -1;
	}
    }

    /**
     * ����SQL����
     * 
     * @return
     */
    public static String filterSQL(String param) {
	// boolean r = false;
	if (param != null && param.trim().length() > 0) {
	    String filter = "or,and,exec,insert,select,delete,update,count,%,chr,mid,master,truncat,char,declare";
	    String filterAry[] = filter.split(",");
	    // System.out.println(filterAry.length);
	    for (int i = 0; i < filterAry.length; i++) {
		param = param.replaceAll(filterAry[i], "");
	    }
	}
	return param;
    }

    /**
     * �����Ƿ���SQL���ؽ���
     * 
     * @param args
     */
    public static boolean isLawlessChar(String param) {
	boolean b = false;
	if (param != null && param.trim().length() > 0) {
	    String filter = "or,and,exec,insert,select,delete,update,count,%,chr,mid,master,truncat,char,declare";
	    String filterAry[] = filter.split(",");
	    for (int i = 0; i < filterAry.length; i++) {
		if (param.indexOf(filterAry[i]) > 0) {
		    return true;
		}
	    }
	}
	return b;
    }

    public static java.util.Date stringToDate(String date, String format) {
	java.util.Date dd = null;
	if (date == null || date.trim().length() == 0)
	    return null;
	if (format == null || format.trim().length() == 0)
	    format = "yyyy-MM-dd hh:mm:ss";
	SimpleDateFormat formatter = new SimpleDateFormat(format);
	try {
	    dd = formatter.parse(date);
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	return dd;
    }

    public static String formatDate(String a[]) {
	String date = "";
	if (a != null && a.length > 1) {
	    if (a[1].length() == 1)
		a[1] = "0" + a[1];
	    if (a.length > 3 && a[2].length() == 1)
		a[2] = "0" + a[2];
	    if (a.length > 3)
		date = a[0] + a[1] + a[2];
	    else {
		date = a[0] + a[1];
	    }
	}
	return date;
    }

    public static String formatDate(java.util.Date Parameter, String format) {
	java.text.SimpleDateFormat sdFormat = new java.text.SimpleDateFormat(
		format);
	String getDate;
	if (Parameter != null) {
	    getDate = sdFormat.format(Parameter);
	    if (!getDate.equals("0000-00-00"))
		return getDate;
	    else
		return "";
	}// if !=null
	else
	    return "";
    }

    public static String method(String date) {
	if (!Tools.isNullStr(date)) {
	    String a1[] = date.split("-");
	    if (a1 != null && a1.length > 1) {
		String t = "";
		for (int i = 0; i < a1.length; i++) {
		    if (a1[i].length() <= 1)
			a1[i] = "0" + a1[i];
		    t += a1[i];
		}
		date = t;
	    }
	}
	return date;
    }

    /**
     * ����ת��
     * 
     * @param num
     * @return
     */
    public static String formatNumber(String num) {
	if (num != null && num.length() > 0) {
	    String n[] = "һ,��,��,��,��,��,��,��,��".split(",");
	    for (int i = 0; i < n.length; i++) {
		num = num.replaceAll(String.valueOf(i + 1), n[i]);
	    }
	}
	return num;
    }

    /**
     * ��ʽ������ ȥ�գ�ȥ������,nullת""
     * 
     * @param args
     */
    public static String formatParam(String param) {
	param = Tools.trim(param);
	param = param.replaceAll("'", "''");
	return param;
    }
	/**
	 * ��ʽ���ؼ���
	 * @param str
	 * @return
	 */
    public static String formatKey(String str) {
	if (!Tools.isNullStr(str)) {
		Pattern p = Pattern.compile("\r|\n");
		str=str.replace("'", "��").replace("\"", "��");
		str = p.matcher(str).replaceAll("");
	    return str;
	} else {
	    return str;
	}
    }
    /**
     * ���ɼ�ģ����ѯSQL
     * @param str
     * @return
     */
    public static String simpleMohu(String str){
    	if(str==null || str.length()==0) 
    		return "%";
    	else
    		return "%"+str+"%";
    }
    /**
     * ���ɸ��ӵ�ģ����ѯSQL
     * @param str
     * @return
     */
    public static String mohu(String str){
    	String tmp = "";
    	if(str==null || str.length()==0) return "%";
		for(int i=0;i<str.length();i++){
		    String s = str.substring(i,i+1);
		    s = s.replace("*", "%");
		    if(s.equals("?") || s.equals("��")){
			tmp = tmp+"_";
		    }else{
			String ss ="%";
			if(i+1<str.length()){
			    if(str.substring(i+1,i+2).equals("?") || str.substring(i+1,i+2).equals("��")){
				ss="";
			    }
			}
			tmp = tmp+s+ss;
		    }
		}	
		if(tmp.indexOf("_")!=0) tmp = "%"+tmp;
		return tmp;
    }
	/**
	 * ȡ��ǰ�ܿ�ʼ���ںͽ������� ����Ϊһ�ܿ�ʼ
	 * 
	 * @return
	 */
	public static String[] getDateOfThisWeek() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		int index = cal.get(Calendar.DAY_OF_WEEK); // �����Ǳ��ܵĵڼ���
		// ת���й���ϰ��,����ǵ�һ��,��ת��Ϊ������(���������Ϊһ�ܵĵ�һ��,���й�Ϊÿ�ܵ����һ��)
		// if (index == 1)
		// index = 8;
		cal.add(Calendar.DATE, -(index - 1));// index - 2 (������һΪһ�ܿ�ʼ)
		String start = (sdf.format(cal.getTime()));
		cal.add(Calendar.DATE, 6);
		String end = (sdf.format(cal.getTime()));
		String[] result = new String[] { start, end };
		return result;
	}
	/**
	 * ��������ַ�a-zA-Z0-9
	 * <p>�������ƣ�        </p>
	 * <p>����˵����
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @return
	 * lenΪ�ַ�����
	 * @date   ����ʱ�䣺2011-3-18
	 * @author ���ߣ����п�
	 */
	public static String getRndCode(int len){
		if(len<=0) return "";
		StringBuffer code=new StringBuffer();
		String src[]="0,1,2,3,4,5,6,7,8,9,a,b,c,d,f,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");
		int srcsize=src.length;
		for(int i=0;i<len;i++){
			Random ran = new Random();
			int index=(int)(ran.nextFloat()*srcsize);
			code.append(src[index]);
		}
		return code.toString();
	}
	/**
	 * ���ɷ�ҳSQL
	 * @param SQL
	 * @param pagesize
	 * @param page ��1ҳ����
	 * @return
	 */
	public static String formatPagingSQL(String SQL,int pagesize,int page)
	{
		int startRecNo=0,endRecNo=0;
		if(pagesize<=0) pagesize=1;
		if(page<=0) page=1;
		startRecNo=(page-1)*pagesize+1;
		endRecNo = startRecNo+pagesize-1;
		
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * FROM (");
		sql.append("SELECT  ROW_.*, ROWNUM ROWNUM_ FROM (");
		sql.append(SQL + ") ROW_ "); // �Ѹ����Ĳ�ѯ���ƴ�ӵ���ҳ����С�
		sql.append("WHERE ROWNUM <= "+endRecNo+") ");
		sql.append("WHERE ROWNUM_ >= "+startRecNo);

		return sql.toString();
	}
    public static void main(String[] args) throws Exception {
    	//String str="!U";
    	//System.out.println(Tools.isUserName(str));
    	//String str1="23&tag=grid&st=search";
    	//System.out.println(Tools.getParamter(str1, "tag"));
//    	System.out.println(Tools.checkCellPhone("13194457074"));
//    	System.out.println(Tools.isUserName("a123"));
    	System.out.println(Tools.checkURL("http://127.0.0.1:8080/EPay/site/user/loginmgr/loginSubmit.do"));
    	System.out.println(DesUtils.decryptDSE("82df45af95b64fa5"));
    }
    
    /**
     * ��ȡϵͳ·��
     * 
     * @param packageName ����
     * @return
     */
    public static String getResPath(String packageName){
		Resource rs =  new ClassPathResource(packageName);
		try {
			return rs.getFile().getAbsolutePath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		    throw new IllegalAccessError(e.getMessage());
		}
	}
    
    /**
     * BigDecimal ����ת�ַ���
     * <p>�������ƣ�        </p>
     * <p>����˵����
     *
     * </p>
     *<p>����˵����</p>
     * @param obj
     * @return
     *
     * @date   ����ʱ�䣺2011-11-28
     * @author ���ߣ����п�
     */
    public static String bigToStr(Object obj){
		if(obj!=null){
			if (obj instanceof BigDecimal) {
				BigDecimal big = (BigDecimal) obj;
				return big.toString();
			}else{
				return "";
			}
		}
		return "";
	}
    
    /**
     * BigDecimal���Ͷ���תΪ���ͣ�Ϊ�ջ��big����ʱ���򷴻�0
     * <p>�������ƣ�        </p>
     * <p>����˵����
     *
     * </p>
     *<p>����˵����</p>
     * @param obj
     * @return
     *
     * @date   ����ʱ�䣺2012-1-10
     * @author ���ߣ����п�
     */
    public static int bigToInt(Object obj){
		if(obj!=null){
			if (obj instanceof BigDecimal) {
				BigDecimal big = (BigDecimal) obj;
				return big.intValue();
			}else{
				return 0;
			}
		}
		return 0;
	}
}