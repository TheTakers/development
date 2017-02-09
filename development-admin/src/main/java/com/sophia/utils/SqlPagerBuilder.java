package com.sophia.utils;

/**
 * 分页语句工厂
 * @author zkning
 */
public class SqlPagerBuilder{
	public static final String DATABASE_MYSQL = "";
	public static final String DATABASE_ORACLE = "";

	public static String createPager(String sql,Integer pageSize,Integer pageNo,String database){
		StringBuilder sqlbuilder = new StringBuilder(sql);
		//		if(database.equals(""))
		if(true){
			sqlbuilder.append(" limit ").append((pageNo -1) * pageSize).append(",").append(pageSize);
			return sqlbuilder.toString();
		}
		return null;
	}

	public static String countWarp(String sql){
		StringBuilder countBuilder = new StringBuilder();
		countBuilder.append("select count(1) from (").append(sql).append(") t");
		return countBuilder.toString();
	}
}
