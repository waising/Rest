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
 * @ClassName   ������SettingAction 
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
public class SettingAction {

	/**
	 * 
	 * �������ƣ�getSet 
	 * ����˵������ȡ������Ϣ
	 * ����˵����
	 * @date   ����ʱ�䣺2012-11-5
	 * @author ���ߣ�wwx
	 */
	public List<Map> getSet(){
		String res ="";
		List<Map> list=null;
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//��ȡ�����б�
			Map<String,Object> m = re.getInParams();
			
			//�����б�
			Object[] params = new Object[]{
					m.get("obj")
			}; 
			
			//ɾ��Ʊ����Ϣ
			String sql = "select code from and_setting where obj=? ";
			
			list= db.queryList(Map.class, sql.toString(), params);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * �������ƣ�addSetting 
	 * ����˵�����������
	 * ����˵����
	 * @date   ����ʱ�䣺2012-11-5
	 * @author ���ߣ�wwx
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addSetting(){
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//��ȡ�����б�
			Map<String,Object> m = re.getInParams();
			
			//�����б�
			Object[] params = new Object[]{
					m.get("obj"),m.get("value")
					
			};
			//���������Ϣ
			String sql = "insert and_setting values(?,?)";
			db.update(sql,params);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * �������ƣ�delSetting 
	 * ����˵����
	 * ����˵����
	 * @date   ����ʱ�䣺2012-11-6
	 * @author ���ߣ�wwx
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delSetting(){
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//��ȡ�����б�
			Map<String,Object> m = re.getInParams();
			
			//�����б�
			Object[] params = new Object[]{
					m.get("obj")
			};
			//ɾ��������Ϣ
			String sql = "delete from and_setting obj=?";
			db.update(sql,params);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * �������ƣ�updSetting 
	 * ����˵����
	 * ����˵����
	 * @date   ����ʱ�䣺2012-11-6
	 * @author ���ߣ�wwx
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updSetting(){
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//��ȡ�����б�
			Map<String,Object> m = re.getInParams();
			
			//�����б�
			Object[] params = new Object[]{
					m.get("obj"),m.get("value")
			};
			
			//����������Ϣ
			String sql = "update and_setting value=? where obj=?";
			db.update(sql,params);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
