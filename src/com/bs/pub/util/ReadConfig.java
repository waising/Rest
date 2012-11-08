package com.bs.pub.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/** 
 * 读取配置文件
 * @ClassName   类名：ReadConfig 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2011-3-24
 * @author      创建人：李中俊
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2011-3-24   李中俊   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class ReadConfig {
	
	private static Map<String,String> hm = null;
	private static Properties pro = null;
	private static String path = "config/settings.properties";
	
	/**
	 * 
	 * <p>函数名称：getProperties        </p>
	 * <p>功能说明：读取配置文件信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 *
	 * @date   创建时间：Jan 10, 2012
	 * @author 作者：余威威
	 */
	public static void getProperties(){
		if(pro == null){
			PathMatchingResourcePatternResolver resolover = new PathMatchingResourcePatternResolver();
			Resource r = resolover.getResource(path);
			pro = new Properties();
			try {
				pro.load(r.getInputStream());
			}catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}
	
	/**
	 * 
	 * <p>函数名称：getConfig        </p>
	 * <p>功能说明：获取配置文件信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 *
	 * @date   创建时间：2011-3-24
	 * @author 作者：李中俊
	 */
	public static Map<String,String> getConfig(){
		if(hm == null){
			hm = new HashMap<String,String>();
			ReadConfig.getProperties();
			for(Object key :pro.keySet()){
				String keys = key.toString();
				hm.put(keys, pro.getProperty(keys));
			}
		}
		return hm;
	}
	
	/**
	 * 
	 * <p>函数名称：setConfig        </p>
	 * <p>功能说明：写入或者修改配置文件信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param key 键
	 * @param value 值
	 *
	 * @date   创建时间：Jan 10, 2012
	 * @author 作者：余威威
	 */
	public static void setConfig(String key, String value){
		try {
			ReadConfig.getProperties();
			OutputStream fos = new FileOutputStream(new PathMatchingResourcePatternResolver().getResource(path).getFile().getAbsolutePath());
			pro.setProperty(key, value);
			pro.store(fos, "update '" + key + "' value"); 
			if(hm == null){
				hm = getConfig();
			}
			hm.put(key, value);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		} 
	}
	
	//测试
	public static void main(String[] args) {
		Map map=ReadConfig.getConfig();
		System.out.println(map.get("appName"));
		//String a[][][]={{{"a","b"},{"a","b"}},{{"a","b"},{"a","b"}}};
	}
}
