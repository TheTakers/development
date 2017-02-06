package com.sophia.utils;

/**
 * 分页语句工厂
 * @author zkning
 */
public class SqlPagerBuilder{
	public static final String DATABASE_MYSQL = "";
	public static final String DATABASE_ORACLE = "";

	public static String createLimit(String sql,Integer pageSize,Integer pageNo,String database){

		//		if(database.equals(""))
		if(true)
			return mySql(sql,pageSize,pageNo);

		return "";
	}

	private static String mySql(String sql,Integer pageSize,Integer pageNo){
		StringBuilder sqlbuilder = new StringBuilder(sql);
		sqlbuilder.append(" limit ")
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
