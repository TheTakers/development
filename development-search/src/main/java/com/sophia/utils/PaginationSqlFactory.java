package com.sophia.utils;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 分页语句工厂
 * @author zkning
 */
public class PaginationSqlFactory extends JdbcTemplate{
	
	
	public static String buildPaginationSQL(String sql,Integer pageSize,Integer pageNo,String database){
		
//		if(database.equals(""))
		if(true)
			return mySql(sql, pageSize, pageNo);
		
		return "";
	}
	
	private static String mySql(String sql,Integer pageSize,Integer pageNo){
		StringBuilder sqlbuilder = new StringBuilder(sql);
		sqlbuilder.append(" LIMIT ")
				  .append((pageNo -1) * pageSize)
				  .append(",")
				  .append(pageSize);
		return sqlbuilder.toString();
	}
	
	public static String buildCountSQL(String sql){
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("select count(1) from (")
		.append(sql)
		.append(") t");
		return sqlbuilder.toString();
	}
}
