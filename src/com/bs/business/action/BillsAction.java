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
			Map<String,Object> m = re.getInParams();
			//����޸�ʱ��
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
	public String delBills(){
		String result ="0";
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//��ȡ�����б�
			Map<String,Object> m = re.getInParams();
			
			String code = (String) m.get("code");
			Object[] params = new Object[]{
					code
			};
			//ɾ��Ʊ����Ϣ
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
	 * �������ƣ�addBills 
	 * ����˵�������Ʊ����Ϣ
	 * ����˵����
	 * @date   ����ʱ�䣺2012-11-5
	 * @author ���ߣ�wwx
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String addBills(){
		String result="0";
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//��ȡ�����б�
			Map<String,Object> m = re.getInParams();
			
			java.util.Date   now=new   java.util.Date();   
			//ʱ���ʽ�뾫ȷ������λ�����s������
		    java.text.SimpleDateFormat formatter=new java.text.SimpleDateFormat("yyMMddHHmmssssss");   
		    String lstDateTime = formatter.format(now);
		    
			//�����б�
			Object[] params = new Object[]{
					m.get("code,"),m.get("name"),m.get("type"),lstDateTime
			};
			
			//���Ʊ����Ϣ
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
	 * �������ƣ�updBills 
	 * ����˵��������Ʊ����Ϣ
	 * ����˵����
	 * @date   ����ʱ�䣺2012-11-5
	 * @author ���ߣ�wwx
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String updBills(){
		String result ="0";
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//��ȡ�����б�
			Map<String,Object> m = re.getInParams();
			
			java.util.Date   now=new   java.util.Date();   
			//ʱ���ʽ�뾫ȷ������λ�����s������
		    java.text.SimpleDateFormat formatter=new java.text.SimpleDateFormat("yyMMddHHmmssssss");   
		    String lstDateTime = formatter.format(now);
		    
			//�����б�
			Object[] params = new Object[]{
					m.get("name"),m.get("type"),lstDateTime,m.get("code")
			};
			
			//����Ʊ����Ϣ
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
	 * �������ƣ�jsonStr 
	 * ����˵����json�ַ���
	 * ����˵����
	 * @param str
	 * @return
	 * @date   ����ʱ�䣺2012-11-9
	 * @author ���ߣ�wwx
	 */
	private String jsonStr(String str){
		JSONObject json = new JSONObject();
		json.put("result", str);
		return json.toString();
	}
}
