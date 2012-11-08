package com.bs.pub.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;

import com.bs.httpclient.HttpClientUtil;

/** 
 *
 * @ClassName   类名：HttpBackReq 
 * @Description 功能说明：Http回调请求
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2011-6-15
 * @author      创建人：Josen.Lin
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2011-6-15   Josen.Lin   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class HttpBackReq {

	/*//注册公用方法
	public static HashMap<String, String> regMethod = new HashMap<String, String>();
	
	static {
		regMethod.put("getExecPost", "url");
		regMethod.put("execPost", "url");
	}*/
	
	/**
	 * 
	 * <p>函数名称：execPost        </p>
	 * <p>功能说明：执行Post请求
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param params 查询参数
	 *
	 * @date   创建时间：2011-6-9
	 * @author 作者：Josen.Lin
	 */
	public static  Map<String, Object> getExecPost(String url){
		
		if (url==null || "".equals(url.trim()))
			return null;
		
		try {
			DefaultHttpClient httpClient = HttpClientUtil.getHttpClient();
	    	String respStr = HttpClientUtil.excutePostUrl(httpClient, url);
	    	Map<String, Object> rslMap = new HashMap<String, Object>();
	    	rslMap.put("RESPSRC", respStr);
	    	return rslMap;
    	}
		catch (Exception e){
			return null;
		}
	}
	
	/**
	 * 
	 * <p>函数名称：execPost        </p>
	 * <p>功能说明：执行Post请求
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param params 查询参数
	 *
	 * @date   创建时间：2011-6-9
	 * @author 作者：Josen.Lin
	 */
	public static void execPost(String url){
		
		if (url==null || "".equals(url.trim()))
			return;
		
		DefaultHttpClient httpClient = HttpClientUtil.getHttpClient();
    	HttpClientUtil.excutePostUrl(httpClient, url);
	}
}
