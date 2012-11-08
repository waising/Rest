package com.bs.pub.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;

import com.bs.httpclient.HttpClientUtil;

/** 
 *
 * @ClassName   ������HttpBackReq 
 * @Description ����˵����Http�ص�����
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        �������ڣ�2011-6-15
 * @author      �����ˣ�Josen.Lin
 * @version     �汾�ţ�V1.0
 *<p>
 ***************************�޶���¼*************************************
 * 
 *   2011-6-15   Josen.Lin   �������๦�ܡ�
 *
 ***********************************************************************
 *</p>
 */
public class HttpBackReq {

	/*//ע�ṫ�÷���
	public static HashMap<String, String> regMethod = new HashMap<String, String>();
	
	static {
		regMethod.put("getExecPost", "url");
		regMethod.put("execPost", "url");
	}*/
	
	/**
	 * 
	 * <p>�������ƣ�execPost        </p>
	 * <p>����˵����ִ��Post����
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @param params ��ѯ����
	 *
	 * @date   ����ʱ�䣺2011-6-9
	 * @author ���ߣ�Josen.Lin
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
	 * <p>�������ƣ�execPost        </p>
	 * <p>����˵����ִ��Post����
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @param params ��ѯ����
	 *
	 * @date   ����ʱ�䣺2011-6-9
	 * @author ���ߣ�Josen.Lin
	 */
	public static void execPost(String url){
		
		if (url==null || "".equals(url.trim()))
			return;
		
		DefaultHttpClient httpClient = HttpClientUtil.getHttpClient();
    	HttpClientUtil.excutePostUrl(httpClient, url);
	}
}
