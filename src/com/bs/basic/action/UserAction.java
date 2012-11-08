package com.bs.basic.action;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bs.pub.util.MD5;
import com.bs.restframework.context.SystemContext;
import com.bs.restframework.context.util.SystemContextUtil;
import com.bs.restframework.db.Database;
import com.bs.restframework.web.action.BaseAction;

/** 
 *
 * @ClassName   类名：UserAction 
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
public class UserAction extends BaseAction{

	/**
	 * 
	 * 函数名称：loginSys 
	 * 功能说明：登录
	 * 参数说明：
	 * @date   创建时间：2012-11-5
	 * @author 作者：wwx
	 */
	public List<Map> loginSys(){
		String res ="";
		List<Map> list=null;
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//获取参数列表
			Map<String,Object> m = re.getInParams();
			MD5 md5 = new MD5();
			
			//参数列表
			Object[] params = new Object[]{
					m.get("uCode"),md5.getMD5ofStr((String) m.get("uPwd")).toUpperCase()
			}; 
			
			//删除票据信息
			String sql = "select code from and_user where code=? and upper(pwd)=?";
			
			list= db.queryList(Map.class, sql.toString(), params);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
