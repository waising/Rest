package com.bs.basic.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 *
 * @ClassName   ������sss 
 * @Description ����˵����
 * TODO
 ************************************************************************
 * @date        �������ڣ�2012-11-11
 * @author      �����ˣ�wwx
 * @version     �汾�ţ�V1.0
 *<p>
 ***************************�޶���¼*************************************
 * 
 *   2012-11-11   wwx   �������๦�ܡ�
 *
 ************************************************************************
 *</p>
 */
public class sss {

	/**
	 * �������ƣ�main 
	 * ����˵����
	 * ����˵����
	 * @param args
	 * @date   ����ʱ�䣺2012-11-11
	 * @author ���ߣ�wwx
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		String str = "[{\"result\":\"120121111134000046\"}]";
		json.put("result", str);
		System.out.println(str.toString());
		System.out.println(str.replace("'\"'", ""));
		JSONArray j = JSONArray.fromObject(str);
		
		  JSONArray jsonArray = new JSONArray();
		  
//		  org.json.JSONArray;
	}

}
