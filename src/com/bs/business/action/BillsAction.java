package com.bs.business.action;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bs.restframework.context.SystemContext;
import com.bs.restframework.context.util.SystemContextUtil;
import com.bs.restframework.db.Database;
import com.bs.restframework.web.action.BaseAction;

/** 
 *
 * @ClassName   类名：Bills 
 * @Description 功能说明：
 * TODO
 ************************************************************************
 * @date        创建日期：2012-11-5
 * @author      创建人：wwx
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2012-11-5   wwx   创建该类功能。
 *
 ************************************************************************
 *</p>
 */
public class BillsAction extends BaseAction {
	
	/**
	 * 
	 * 函数名称：getBills 
	 * 功能说明：获取票据信息
	 * 参数说明：
	 * @date   创建时间：2012-11-5
	 * @author 作者：wwx
	 */
	public List<Map> getBills(){
		List<Map> list=null;
		
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			
			Database db = SystemContextUtil.getDatabase(this);
			
			StringBuilder sql = new StringBuilder();
			
			//获取参数列表
			Map<String,Object> m = re.getInParams();
			//最后修改时间
			String lstDate = (String) m.get("lstDate");
			Object[] params = new Object[]{
					lstDate
			};
			
			sql.append("select * from and_bill where 1=1 ");
			
			if (lstDate!=null && lstDate.equals("")){
				sql.append("and LSTDATETIME <= ?");
			}
			
			list= db.queryList(Map.class, sql.toString(), null);
			java.text.SimpleDateFormat formatter=new java.text.SimpleDateFormat("yyMMddHHmmssssss");   
			Map map = new HashMap<String, String>();
			map.put("RESULTLSTTIME", formatter.format(new Date()));
			list.add(map);
			
		}catch (Exception e){
			e.printStackTrace();
		}
		//返回数据格式
		return list;
	}
	
	/**
	 * 
	 * 函数名称：delBills 
	 * 功能说明：
	 * 参数说明：
	 * @date   创建时间：2012-11-5
	 * @author 作者：wwx
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String delBills(){
		String result ="0";
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//获取参数列表
			Map<String,Object> m = re.getInParams();
			
			String code = (String) m.get("code");
			Object[] params = new Object[]{
					code
			};
			//删除票据信息
			String sql = "delete from and_bills where code=?";
			db.update(sql,new Object[]{code});
			result = "1";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr(result);
	}
	
	/**
	 * 
	 * 函数名称：addBills 
	 * 功能说明：添加票据信息
	 * 参数说明：
	 * @date   创建时间：2012-11-5
	 * @author 作者：wwx
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String addBills(){
		String result="0";
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//获取参数列表
			Map<String,Object> m = re.getInParams();
			
			java.util.Date   now=new   java.util.Date();   
			//时间格式想精确到多少位后面加s就行了
		    java.text.SimpleDateFormat formatter=new java.text.SimpleDateFormat("yyMMddHHmmssssss");   
		    String lstDateTime = formatter.format(now);
		    
			//参数列表
			Object[] params = new Object[]{
					m.get("code,"),m.get("name"),m.get("type"),lstDateTime
			};
			
			//添加票据信息
			String sql = "insert bsc_bill values(sys_guid(),?,?,?,?)";
			db.update(sql,params);
			result = "1";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr(result);
	}
	
	/**
	 * 
	 * 函数名称：updBills 
	 * 功能说明：更新票据信息
	 * 参数说明：
	 * @date   创建时间：2012-11-5
	 * @author 作者：wwx
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String updBills(){
		String result ="0";
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//获取参数列表
			Map<String,Object> m = re.getInParams();
			
			java.util.Date   now=new   java.util.Date();   
			//时间格式想精确到多少位后面加s就行了
		    java.text.SimpleDateFormat formatter=new java.text.SimpleDateFormat("yyMMddHHmmssssss");   
		    String lstDateTime = formatter.format(now);
		    
			//参数列表
			Object[] params = new Object[]{
					m.get("name"),m.get("type"),lstDateTime,m.get("code")
			};
			
			//更新票据信息
			String sql = "update bsc_bill set name=?,type=?,lstDateTime=? where code=?";
			db.update(sql,params);
			result="1";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr(result);
	}
	
	/**
	 * 
	 * 函数名称：jsonStr 
	 * 功能说明：json字符串
	 * 参数说明：
	 * @param str
	 * @return
	 * @date   创建时间：2012-11-9
	 * @author 作者：wwx
	 */
	private String jsonStr(String str){
		JSONObject json = new JSONObject();
		json.put("result", str);
		return json.toString();
	}
}
