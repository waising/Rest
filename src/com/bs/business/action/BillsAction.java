package com.bs.business.action;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bs.pub.util.GUID;
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
	public List<Map> delBills(){
		String result ="0";
		Map map = new HashMap<String, String>();
		map.put("result", "0");
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//��ȡ�����б�
			Map<String,Object> m = re.getInParams();
			
			String id = (String) m.get("id");
			Object[] params = new Object[]{id};
			//ɾ��Ʊ����Ϣ
			String sql = "delete from and_bill where id=?";
			db.update(sql, params);
			result = "1";
			map.put("result",result);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr(map);
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
	public List<Map> addBills(){
		String result="0";
		Map map = new HashMap<String, String>();
		map.put("result", "0");
		try{
			SystemContext re = SystemContextUtil.getSystemContext();
			Database db = SystemContextUtil.getDatabase(this);
			//��ȡ�����б�
			Map<String,Object> m = re.getInParams();
			
			String code = (String) m.get("code");
			String type = (String) m.get("type");
			
			Object[] selPar = new Object[]{
					code,type
			};
			
			String sqlSel="select * from and_bill where code=? and type=? ";
			List<String> billCount = db.queryList(String.class, sqlSel, selPar);
			if (billCount.size()>0){
				result=result+"|��Ʊ�ݱ����Ѵ���!";
			}
			
			map.put("result", result);
			if (result.equals("0")){
				java.util.Date  now=new   java.util.Date();   
				//ʱ���ʽ�뾫ȷ������λ�����s������
			    java.text.SimpleDateFormat formatter=new java.text.SimpleDateFormat("yyyyMMddHHmmsssss");   
			    String lstDateTime = formatter.format(now);
			    String guid = GUID.newGUID();
				//�����б�
				Object[] params = new Object[]{
						guid,code,m.get("name"),type,lstDateTime
				};
				
				//���Ʊ����Ϣ
				String sql = "insert into And_Bill values(?,?,?,?,?)"; 
				
				db.update(sql,params);
				result = "1";
				map.put("result", result);
				map.put("guid", guid);
				map.put("lstDateTime", lstDateTime);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr(map);
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
	public List<Map> updBills(){
		String result ="0";
		Map map = new HashMap<String, String>();
		map.put("result", "0");
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
			map.put("result", result);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr(map);
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
	private List<Map> jsonStr(Map map){

		List<Map> list = new ArrayList<Map>();
		list.add(map);
		return list;
	}
}
