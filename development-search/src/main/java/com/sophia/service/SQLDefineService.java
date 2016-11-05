package com.sophia.service;

import java.util.List;
import java.util.Map;

import com.sophia.domain.SQLDefine;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLDefineRepository;
import com.sophia.request.QueryRequest;
import com.sophia.response.GridResponse;

public interface SQLDefineService extends JpaRepository<SQLDefineRepository>{
	
	public String save(SQLDefine sqlDefine);
	
	public GridResponse list(QueryRequest queryRequest);
	
	/**
	 * 根据SQLCODE
	 * @param queryRequest
	 * @return
	 */
	public GridResponse list(String code,QueryRequest queryRequest);
	
	public Map<String,Object> findById(String id);
	
	public SQLDefine findBySqlId(String sqlId);
	
	/**
	 * 根据SQLID获取SQL对应的所有数据
	 * @param sqlId
	 * @return
	 */
	public List<Map<String,Object>> findAllBySqlId(String sqlId);
}
