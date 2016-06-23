package com.sophia.utils;

import org.springframework.jdbc.core.JdbcTemplate;

import com.sophia.vo.Pagination;

/**
 * 分页语句工厂
 * @author zkning
 */
public class PaginationSqlFactory extends JdbcTemplate{
	
	
	public static String buildPaginationSQL(String sql,Pagination pagination,String database){
		
//		if(database.equals(""))
		if(true)
			return mySql(sql, pagination);
		
		return "";
	}
	
	private static String mySql(String sql,Pagination pagination){
		StringBuilder sqlbuilder = new StringBuilder(sql);
		sqlbuilder.append(" LIMIT ")
				  .append((pagination.getPageNo() -1) * pagination.getPageSize())
				  .append(",")
				  .append(pagination.getPageSize());
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
