package com.bs.pub.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
/** 
*
* @ClassName   ������SqlUtil 
* @Description ����˵����
* <p>
*  SQL��ҳ������
*</p>
************************************************************************
* @date        �������ڣ�2011-6-24
* @author      �����ˣ�����
* @version     �汾�ţ�V1.0
*<p>
***************************�޶���¼*************************************
* 
*   2011-6-24   ����   �������๦�ܡ�
*
***********************************************************************
*</p>
*/
public class SqlUtil {
	public final static String BLOB_KEY = "BLOB";
	/**
	 * 
	 * <p>�������ƣ�getMaxElements        </p>
	 * <p>����˵������ȡ������
	 *
	 * </p>
	 *<p>����˵����sql �����õ�SQL���</p>
	 * @return
	 *
	 * @date   ����ʱ�䣺2011-6-24
	 * @author ���ߣ�����
	 */
	public static String getMaxElements(String sql) {
		String regex = "select((.)+)from";
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		String[] s = p.split(sql);
		StringBuffer newSql  = new StringBuffer("select count(1) as total from ").append(s[1]);
		return newSql.toString();
	}


	/**
	 * 
	 * <p>�������ƣ�getQuerySql        </p>
	 * <p>����˵������ȡ��ѯSQL���
	 *
	 * </p>
	 *<p>����˵����sql �����õ�SQL���
	 *   pageNumber ��ǰҳ
	 *   pageSize ÿҳ����
	 *</p>
	 * @return
	 *
	 * @date   ����ʱ�䣺2011-6-24
	 * @author ���ߣ�����
	 */
	public static String getQuerySql(String sql,int pageNumber,int pageSize)
	{
		
		StringBuffer target = new StringBuffer(100);
		target.append("select * from ( select a.*, rownum r from ( ");
		target.append(sql);
		target.append(" ) a where rownum <= ");
		target.append(pageSize*pageNumber);
		target.append(") where r>");
		target.append((pageNumber - 1) * pageSize);
		return target.toString();
	}
	/**
	 * <p>�������ƣ�        </p>
	 * <p>����˵����
	 *	blob�����ֶα���²���
	 * </p>
	 *<p>����˵����</p>
	 * @param sql
	 * @param setParams blob�ֶθ���ֵ�����б�
	 * @param wherePrams where����ֵ�����б�
	 * @return
	 *
	 * @date   ����ʱ�䣺2012-9-5
	 * @author ���ߣ����п�
	 */
	public static boolean blobUpdate(DataSource ds,LobHandler lobHandler,String sql,final List<byte[]> setParams,final List wherePrams){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute(sql,
				new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					
				protected void setValues(PreparedStatement ps,
						LobCreator lobCreator) throws SQLException {
					
					int index =1;
					for(int i=0;i<setParams.size();i++){
						int length = 0;
						InputStream in = null;
						byte[] data = setParams.get(i);
						if(data!=null) {
							length = data.length;
							in = new ByteArrayInputStream(data);
						}
						lobCreator.setBlobAsBinaryStream(ps, index, in, length);
						index++;
					}
					
					if(wherePrams!=null){
						for(int i=0;i<wherePrams.size();i++){
							ps.setObject(index, wherePrams.get(i));
							index++;
						}
					}
				}
			
		});
		jdbcTemplate = null;
		return true;
	}
	
	/**
	 * <p>�������ƣ�        </p>
	 * <p>����˵����
	 *	�����blob�����ֶ�ֵ��sql�����ע��ֻ�ܲ�һ��blob�ֶ�
	 * </p>
	 *<p>����˵����</p>
	 * @param ds
	 * @param lobHandler
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 *
	 * @date   ����ʱ�䣺2012-9-5
	 * @author ���ߣ����п�
	 */
	public static List<Map<String,byte[]>> getBlob(DataSource ds,final LobHandler lobHandler,String sql,Object []params) throws Exception {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		List<Map<String,byte[]>> list = jdbcTemplate.query(sql,
				params, new RowMapper() {
					public Object mapRow(ResultSet rs, int i)
							throws SQLException {
						Map<String,byte[]> results = new HashMap<String, byte[]>();
						byte[] blobByte = lobHandler.getBlobAsBytes(rs, 1);
						results.put(BLOB_KEY, blobByte);
						return results;
					}
		});
		
		return list;
	}
}

