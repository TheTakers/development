package com.sophia.service;

import java.util.List;
import java.util.Map;

import com.sophia.domain.Pager;
import com.sophia.domain.SQLDefine;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLDefineRepository;
import com.sophia.vo.QueryParam;

public interface SQLDefineService extends JpaRepository<SQLDefineRepository>{
	
	public String save(SQLDefine sqlDefine);
	
	public Pager<Map<String,Object>> list(QueryParam queryRequest);
	
	/**
	 * 根据SQLCODE
	 * @param queryRequest
	 * @return
	 */
	public Pager<Map<String, Object>> list(String code,QueryParam queryRequest);
	
	public Map<String,Object> findById(String id);
	
	public SQLDefine findBySqlId(String sqlId);
	
	/**
	 * 根据SQLID获取SQL对应的所有数据
	 * @param sqlId
	 * @return
	 */
	public List<Map<String,Object>> findAllBySqlId(String sqlId);
	
	/**
	 * 查询全部表
	 * @return
	 */
	public List<Map<String,Object>> findAllTable();
}
