package com.bs.pub.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/** 
 * ��ȡ�����ļ�
 * @ClassName   ������ReadConfig 
 * @Description ����˵����
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        �������ڣ�2011-3-24
 * @author      �����ˣ����п�
 * @version     �汾�ţ�V1.0
 *<p>
 ***************************�޶���¼*************************************
 * 
 *   2011-3-24   ���п�   �������๦�ܡ�
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
	 * <p>�������ƣ�getProperties        </p>
	 * <p>����˵������ȡ�����ļ���Ϣ
	 *
	 * </p>
	 *<p>����˵����</p>
	 *
	 * @date   ����ʱ�䣺Jan 10, 2012
	 * @author ���ߣ�������
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
	 * <p>�������ƣ�getConfig        </p>
	 * <p>����˵������ȡ�����ļ���Ϣ
	 *
	 * </p>
	 *<p>����˵����</p>
	 *
	 * @date   ����ʱ�䣺2011-3-24
	 * @author ���ߣ����п�
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
	 * <p>�������ƣ�setConfig        </p>
	 * <p>����˵����д������޸������ļ���Ϣ
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @param key ��
	 * @param value ֵ
	 *
	 * @date   ����ʱ�䣺Jan 10, 2012
	 * @author ���ߣ�������
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
	
	//����
	public static void main(String[] args) {
		Map map=ReadConfig.getConfig();
		System.out.println(map.get("appName"));
		//String a[][][]={{{"a","b"},{"a","b"}},{{"a","b"},{"a","b"}}};
	}
}
