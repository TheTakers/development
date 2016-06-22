package com.sophia.vo;

import java.util.Map;

import org.springframework.jdbc.core.CallableStatementCallback;

public class QueryFilter{
	
	public String sqlId;
	public Object[] args;
	
	/*
	 * 是否分页
	 */
	public Boolean paging = false;
	
	public Integer pageSize = 20;
	
	public Integer pageNo = 1;
	
	public CallableStatementCallback<Map<String,Object>> callableStatementCallback;
	
	public static QueryFilter getInstance(){
		return new QueryFilter();
	}
	
	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	public Boolean getPaging() {
		return paging;
	}

	public void setPaging(Boolean paging) {
		this.paging = paging;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public CallableStatementCallback<Map<String,Object>> getCallableStatementCallback() {
		return callableStatementCallback;
	}

	public void setCallableStatementCallback(CallableStatementCallback<Map<String,Object>> callableStatementCallback) {
		this.callableStatementCallback = callableStatementCallback;
	}
}
