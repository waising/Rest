package com.bs.basic.action;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bs.pub.util.MD5;
import com.bs.restframework.context.SystemContext;
import com.bs.restframework.context.util.SystemContextUtil;
import com.bs.restframework.db.Database;

/** 
 *
 * @ClassName   类名：SettingAction 
 * @Description 功能说明：
 * TODO
 ************************************************************************
 * @date        创建日期：2012-11-6
 * @author      创建人：wwx
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2012-11-6   wwx   创建该类功能。
 *
 ************************************************************************
 *</p>
 */
public class SettingAction {

	/**
	 * 
	 * 函数名称：getSet 
	 * 功能说明：获取设置信息
	 * 参数说明：
	 * @date   创建时间：2012-11-5
	 * @author 作者：wwx
	 */
	public List<Map> getSet(){
		String res ="";
		List<Map> list=null;
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//获取参数列表
			Map<String,Object> m = re.getInParams();
			
			//参数列表
			Object[] params = new Object[]{
					m.get("obj")
			}; 
			
			//删除票据信息
			String sql = "select code from and_setting where obj=? ";
			
			list= db.queryList(Map.class, sql.toString(), params);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * 函数名称：addSetting 
	 * 功能说明：添加设置
	 * 参数说明：
	 * @date   创建时间：2012-11-5
	 * @author 作者：wwx
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addSetting(){
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//获取参数列表
			Map<String,Object> m = re.getInParams();
			
			//参数列表
			Object[] params = new Object[]{
					m.get("obj"),m.get("value")
					
			};
			//添加设置信息
			String sql = "insert and_setting values(?,?)";
			db.update(sql,params);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 函数名称：delSetting 
	 * 功能说明：
	 * 参数说明：
	 * @date   创建时间：2012-11-6
	 * @author 作者：wwx
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delSetting(){
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//获取参数列表
			Map<String,Object> m = re.getInParams();
			
			//参数列表
			Object[] params = new Object[]{
					m.get("obj")
			};
			//删除设置信息
			String sql = "delete from and_setting obj=?";
			db.update(sql,params);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 函数名称：updSetting 
	 * 功能说明：
	 * 参数说明：
	 * @date   创建时间：2012-11-6
	 * @author 作者：wwx
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updSetting(){
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//获取参数列表
			Map<String,Object> m = re.getInParams();
			
			//参数列表
			Object[] params = new Object[]{
					m.get("obj"),m.get("value")
			};
			
			//更新设置信息
			String sql = "update and_setting value=? where obj=?";
			db.update(sql,params);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
