package com.sophia.service;

import java.util.List;
import java.util.Map;

import com.sophia.domain.Pager;
import com.sophia.domain.SQLDefine;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLDefineRepository;
import com.sophia.vo.QueryParam;

public interface SqlDefineService extends JpaRepository<SQLDefineRepository>{
	
	 String save(SQLDefine sqlDefine);
	
	 Pager<Map<String,Object>> list(QueryParam queryRequest);
	
	/**
	 * 根据SQLCODE
	 * @param queryRequest
	 * @return
	 */
	 Pager<Map<String, Object>> list(String code,QueryParam queryRequest);
	
	 Map<String,Object> findById(String id);
	
	 SQLDefine findBySqlId(String sqlId);
	
	/**
	 * 根据SQLID获取SQL对应的所有数据
	 * @param sqlId
	 * @return
	 */
	 List<Map<String,Object>> findAllBySqlId(String sqlId);
	
	/**
	 * 查询全部表
	 * @return
	 */
	 List<Map<String,Object>> findAllTable();
}
