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
 * @ClassName   ������UserAction 
 * @Description ����˵����
 * TODO
 ************************************************************************
 * @date        �������ڣ�2012-11-6
 * @author      �����ˣ�wwx
 * @version     �汾�ţ�V1.0
 *<p>
 ***************************�޶���¼*************************************
 * 
 *   2012-11-6   wwx   �������๦�ܡ�
 *
 ************************************************************************
 *</p>
 */
public class UserAction extends BaseAction{

	/**
	 * 
	 * �������ƣ�loginSys 
	 * ����˵������¼
	 * ����˵����
	 * @date   ����ʱ�䣺2012-11-5
	 * @author ���ߣ�wwx
	 */
	public List<Map> loginSys(){
		String res ="";
		List<Map> list=null;
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//��ȡ�����б�
			Map<String,Object> m = re.getInParams();
			MD5 md5 = new MD5();
			
			//�����б�
			Object[] params = new Object[]{
					m.get("uCode"),md5.getMD5ofStr((String) m.get("uPwd")).toUpperCase()
			}; 
			
			//ɾ��Ʊ����Ϣ
			String sql = "select code from and_user where code=? and upper(pwd)=?";
			
			list= db.queryList(Map.class, sql.toString(), params);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
