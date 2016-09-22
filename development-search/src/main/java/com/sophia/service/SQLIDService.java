package com.sophia.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCallback;

import com.sophia.request.GridResponse;


public interface SQLIDService {
	
	public <T> List<T> queryForList(String sqlID ,Map<String,Object> args,Class<T> elementType);
	
	public List<Map<String,Object>> queryForList(String sqlID ,Map<String,Object> args);
	
	public <T> T queryForObject(String sql, Class<T> requiredType,Map<String,Object> args);

	public Map<String, Object> queryForMap(String sql, Map<String,Object> args);
	
	public <T> T execute(String SQLID, PreparedStatementCallback<T> action);
	
	public <T> GridResponse<T> findAll(String SQLID ,Map<String,Object> args,Class<T> elementType,Integer pageSize,Integer pageNo);
}
