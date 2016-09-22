package com.sophia.service;

import java.util.Map;

import com.sophia.domain.SQLDefine;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLDefineRepository;
import com.sophia.request.GridResponse;
import com.sophia.request.QueryRequest;

public interface SQLDefineService extends JpaRepository<SQLDefineRepository>{
	
	public String save(SQLDefine sqlDefine);
	
	public GridResponse list(QueryRequest queryRequest);
	
	public Map<String,Object> findById(String id);
}
