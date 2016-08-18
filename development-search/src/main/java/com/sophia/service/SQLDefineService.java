package com.sophia.service;

import java.util.Map;

import com.sophia.domain.SQLDefine;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLDefineRepository;
import com.sophia.vo.Grid;
import com.sophia.vo.QueryRequest;

public interface SQLDefineService extends JpaRepository<SQLDefineRepository>{
	
	public String save(SQLDefine sqlDefine);
	
	public Grid list(QueryRequest queryRequest);
	
	public Map<String,Object> findById(String id);
}
