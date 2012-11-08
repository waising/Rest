package com.bs.business.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bs.restframework.context.SystemContext;
import com.bs.restframework.context.util.SystemContextUtil;
import com.bs.restframework.db.Database;
import com.bs.restframework.web.action.BaseAction;
import com.bs.restframework.web.result.ResultModle;
import com.bs.restframework.web.result.ResultType;

/** 
 *
 * @ClassName   类名：DemoAction 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2012-10-24
 * @author      创建人：李中俊
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2012-10-24   李中俊   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class DemoAction extends BaseAction{
	/**
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *	返回json
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2012-10-24
	 * @author 作者：李中俊
	 * @throws InterruptedException 
	 * @throws UnsupportedEncodingException 
	 */
	public String getJson() throws InterruptedException{
//		Database db = SystemContextUtil.getDatabase(this);
//		
//		StringBuilder sql = new StringBuilder();
//		Object[] params = new Object[]{};
//
//		sql.append("select name from a_user");
//		
//		List<Map> rList = db.queryList(Map.class, sql.toString(), params);
//		
//		Map<String,Object> resultMap = new HashMap<String,Object>(); 
//		
//		//需要考虑多条信息
//		if (!(rList.isEmpty()&&rList.size()<0)){
//			for (Map map : rList){
//				resultMap.put("id",map.get("id"));
//				resultMap.put("name",map.get("name"));
//				resultMap.put("pwd",map.get("pwd"));
//				resultMap.put("sex",map.get("sex"));
//			}
//		}
//		
		Database db = SystemContextUtil.getDatabase(this);
		
	StringBuilder sql = new StringBuilder();
		Object[] params = new Object[]{};
		List<Map> list;
		Map m = new HashMap<String, String>();
		m.put("result", "1");

		sql.append("select * from acc_user where rownum<10");
		list = db.queryList(Map.class, sql.toString(), params);

		if (list.size()==0){
			m.put("result", "0");
		}
		list.add(m);
		return "xxxxxxxxxxxxxxx";
//		SystemContext re = SystemContextUtil.getSystemContext();
//		
//		Map<String,Object> m = re.getInParams();
//		System.out.println((String)m.get("userName"));
//		System.out.println(re.getRequestIP());
////		Thread.sleep(5000);
//		resultMap.put("success", "true");
//		resultMap.put("name", );
////		resultMap.put("sex", "不详xxx1");
////		resultMap.put("sex", "不详xxx2");
////		resultMap.put("sex", "不详xxx3");
////		resultMap.put("sex", "不详xxx4");
//		return resultMap;
	}
	
	public ResultModle getRandom(){

		return new ResultModle(ResultType.OUTPUT_STRING_STREAM,"sss");
	}
	
	public void sendMsg(){
		SystemContext re = SystemContextUtil.getSystemContext();
		
		Map<String,Object> m = re.getInParams();
		System.out.println(m.get("userName"));
		
	}
	
	
	/**
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *	页面跳转
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2012-10-24
	 * @author 作者：李中俊
	 */
	public ResultModle turn(){
		return new ResultModle(ResultType.OUTPUT_JSP_VIEW,"/demo/form","该参数值能传回页面");
	}
}
