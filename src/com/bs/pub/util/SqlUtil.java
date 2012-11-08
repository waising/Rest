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
* @ClassName   类名：SqlUtil 
* @Description 功能说明：
* <p>
*  SQL分页工具类
*</p>
************************************************************************
* @date        创建日期：2011-6-24
* @author      创建人：张文
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2011-6-24   张文   创建该类功能。
*
***********************************************************************
*</p>
*/
public class SqlUtil {
	public final static String BLOB_KEY = "BLOB";
	/**
	 * 
	 * <p>函数名称：getMaxElements        </p>
	 * <p>功能说明：获取总条数
	 *
	 * </p>
	 *<p>参数说明：sql 构建好的SQL语句</p>
	 * @return
	 *
	 * @date   创建时间：2011-6-24
	 * @author 作者：张文
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
	 * <p>函数名称：getQuerySql        </p>
	 * <p>功能说明：获取查询SQL语句
	 *
	 * </p>
	 *<p>参数说明：sql 构建好的SQL语句
	 *   pageNumber 当前页
	 *   pageSize 每页条数
	 *</p>
	 * @return
	 *
	 * @date   创建时间：2011-6-24
	 * @author 作者：张文
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
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *	blob类型字段表更新操作
	 * </p>
	 *<p>参数说明：</p>
	 * @param sql
	 * @param setParams blob字段更新值参数列表
	 * @param wherePrams where条件值参数列表
	 * @return
	 *
	 * @date   创建时间：2012-9-5
	 * @author 作者：李中俊
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
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *	查表中blob类型字段值，sql语句中注意只能查一个blob字段
	 * </p>
	 *<p>参数说明：</p>
	 * @param ds
	 * @param lobHandler
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 *
	 * @date   创建时间：2012-9-5
	 * @author 作者：李中俊
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

