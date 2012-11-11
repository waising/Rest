package com.bs.basic.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 *
 * @ClassName   类名：sss 
 * @Description 功能说明：
 * TODO
 ************************************************************************
 * @date        创建日期：2012-11-11
 * @author      创建人：wwx
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2012-11-11   wwx   创建该类功能。
 *
 ************************************************************************
 *</p>
 */
public class sss {

	/**
	 * 函数名称：main 
	 * 功能说明：
	 * 参数说明：
	 * @param args
	 * @date   创建时间：2012-11-11
	 * @author 作者：wwx
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
