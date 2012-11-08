package com.bs.business.action;
import java.util.List;
import java.util.Map;

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
//			Map<String,Object> m = re.getInParams();
//			
//			Object[] params = new Object[]{
//					
//			};
			sql.append("select * from and_bill ");
			list= db.queryList(Map.class, sql.toString(), null);
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
	public void delBills(){
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//获取参数列表
			Map<String,Object> m = re.getInParams();
			
			String code = (String) m.get("code");
			//删除票据信息
			String sql = "delete from EBILL_BUS_PAIRED where ebookno=?";
			db.update(sql,new Object[]{code});
		}catch (Exception e) {
			e.printStackTrace();
		}
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
	public void addBills(){
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//获取参数列表
			Map<String,Object> m = re.getInParams();
			
			//参数列表
			Object[] params = new Object[]{
					m.get(""),m.get("")
					
			};
			String code = (String) m.get("code");
			//添加票据信息
			String sql = "insert bsc_bill values(?,?)";
			db.update(sql,params);
		}catch (Exception e) {
			e.printStackTrace();
		}
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
	public void updBills(){
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//获取参数列表
			Map<String,Object> m = re.getInParams();
			
			//参数列表
			Object[] params = new Object[]{
					m.get(""),m.get("")
					
			};
			String code = (String) m.get("code");
			//更新票据信息
			String sql = "update bsc_bill set  where code=?";
			db.update(sql,params);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
