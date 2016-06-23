package com.sophia.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.CallableStatementCallback;

import com.sophia.vo.Grid;
import com.sophia.vo.Pagination;


public interface SQLIDService {
	
	public <T> List<T> queryForList(String sqlID ,Object[] args,Class<T> elementType);
	
	public <T> T queryForObject(String sql, Class<T> requiredType,Object... args);

	public Map<String, Object> queryForMap(String sql, Object... args);
	
	public <T> T execute(String SQLID, CallableStatementCallback<T> action);
	
	public <T> Grid<T> findAll(String SQLID ,Object[] args,Class<T> elementType,Pagination pagination);
}
