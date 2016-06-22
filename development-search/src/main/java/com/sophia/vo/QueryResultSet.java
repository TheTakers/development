package com.sophia.vo;

import java.util.List;
import java.util.Map;

/**
 * 查询结果集
 * @author zkning
 */
public class QueryResultSet {
	
	private List<Map<String,Object>> resultSet;
 
	private Integer maxCount;
	
	public Integer getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}
	
	public static QueryResultSet getInstance(){
		return new QueryResultSet();
	}
	public List<Map<String,Object>> getResultSet() {
		return resultSet;
	}
	public void setResultSet(List<Map<String,Object>> resultSet) {
		this.resultSet = resultSet;
	}
 }
