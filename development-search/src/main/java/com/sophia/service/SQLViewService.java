package com.sophia.service;

import java.util.Map;

import com.sophia.domain.SQLView;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLViewRepository;
import com.sophia.vo.GridResponse;
import com.sophia.vo.QueryRequest;

public interface SQLViewService extends JpaRepository<SQLViewRepository>{
	
	public String save(SQLView sqlView);
	
	public Map<String,Object> findById(String id);
	
	public GridResponse<Map<String,Object>> list(QueryRequest queryRequest);
}
