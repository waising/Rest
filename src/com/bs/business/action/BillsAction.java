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
 * @ClassName   ������Bills 
 * @Description ����˵����
 * TODO
 ************************************************************************
 * @date        �������ڣ�2012-11-5
 * @author      �����ˣ�wwx
 * @version     �汾�ţ�V1.0
 *<p>
 ***************************�޶���¼*************************************
 * 
 *   2012-11-5   wwx   �������๦�ܡ�
 *
 ************************************************************************
 *</p>
 */
public class BillsAction extends BaseAction {
	
	/**
	 * 
	 * �������ƣ�getBills 
	 * ����˵������ȡƱ����Ϣ
	 * ����˵����
	 * @date   ����ʱ�䣺2012-11-5
	 * @author ���ߣ�wwx
	 */
	public List<Map> getBills(){
		List<Map> list=null;
		
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			
			Database db = SystemContextUtil.getDatabase(this);
			
			StringBuilder sql = new StringBuilder();
			
			//��ȡ�����б�
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
		//�������ݸ�ʽ
		return list;
	}
	
	/**
	 * 
	 * �������ƣ�delBills 
	 * ����˵����
	 * ����˵����
	 * @date   ����ʱ�䣺2012-11-5
	 * @author ���ߣ�wwx
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delBills(){
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//��ȡ�����б�
			Map<String,Object> m = re.getInParams();
			
			String code = (String) m.get("code");
			//ɾ��Ʊ����Ϣ
			String sql = "delete from EBILL_BUS_PAIRED where ebookno=?";
			db.update(sql,new Object[]{code});
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * �������ƣ�addBills 
	 * ����˵�������Ʊ����Ϣ
	 * ����˵����
	 * @date   ����ʱ�䣺2012-11-5
	 * @author ���ߣ�wwx
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addBills(){
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//��ȡ�����б�
			Map<String,Object> m = re.getInParams();
			
			//�����б�
			Object[] params = new Object[]{
					m.get(""),m.get("")
					
			};
			String code = (String) m.get("code");
			//���Ʊ����Ϣ
			String sql = "insert bsc_bill values(?,?)";
			db.update(sql,params);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * �������ƣ�updBills 
	 * ����˵��������Ʊ����Ϣ
	 * ����˵����
	 * @date   ����ʱ�䣺2012-11-5
	 * @author ���ߣ�wwx
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updBills(){
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//��ȡ�����б�
			Map<String,Object> m = re.getInParams();
			
			//�����б�
			Object[] params = new Object[]{
					m.get(""),m.get("")
					
			};
			String code = (String) m.get("code");
			//����Ʊ����Ϣ
			String sql = "update bsc_bill set  where code=?";
			db.update(sql,params);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
