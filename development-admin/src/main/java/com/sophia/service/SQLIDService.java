package com.sophia.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCallback;

import com.sophia.domain.Pager;

public interface SQLIDService {
	
	/**
	 * queryForList
	 * @param sqlID
	 * @param args
	 * @param elementType
	 * @return
	 */
	 <T> List<T> queryForList(String sqlId ,Map<String,Object> args,Class<T> elementType);
	
	/**
	 * 根据sqlid查询结果集
	 * @param sqlID
	 * @param args
	 * @return
	 */
	 List<Map<String,Object>> queryForList(String sqlId ,Map<String,Object> args);
	
	/**
	 * queryForObject T
	 * @param sql
	 * @param requiredType
	 * @param args
	 * @return
	 */
	 <T> T queryForObject(String sqlId, Class<T> requiredType,Map<String,Object> args);
	
	/**
	 * queryForMap
	 * @param SQLID
	 * @param args
	 * @return
	 */
	 Map<String, Object> queryForMap(String sqlId, Map<String,Object> args);
	
	/**
	 * execute
	 * @param SQLID
	 * @param action
	 * @return
	 */
	 <T> T execute(String sqlId, PreparedStatementCallback<T> action);
	
	/**
	 * 根据sqlid获取分页数据
	 * @param SQLID
	 * @param args
	 * @param elementType
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	 <T> Pager<T> findAll(String sqlId ,Map<String,Object> args,Class<T> elementType,Integer pageSize,Integer pageNo);
}
