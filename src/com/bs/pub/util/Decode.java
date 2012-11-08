package com.bs.pub.util;

import java.util.Map;

import com.bs.restframework.context.SystemContext;

/**
 * 参数解密
 * @author 李中俊
 *
 */
public class Decode {
	
	public static Map<String,Object> decode(){
		Map<String,Object> params=SystemContext.getInParams();
		if(params!=null){
			for(Object key : params.keySet()){
				Object val= params.get(key);
				if(val!=null && val instanceof String){
					params.put((String)key, EscapeUnescape.decode((String) val));
				}
			}
		}
		return params;
	}
}
