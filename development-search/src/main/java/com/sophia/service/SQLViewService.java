package com.sophia.service;

import java.util.List;
import java.util.Map;

import com.sophia.domain.SQLView;
import com.sophia.domain.SQLViewField;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLViewRepository;
import com.sophia.request.GridResponse;
import com.sophia.request.QueryRequest;

public interface SQLViewService extends JpaRepository<SQLViewRepository>{
	
	public String save(SQLView sqlView);
	
	public Map<String,Object> findById(String id);
	
	public GridResponse<Map<String,Object>> list(QueryRequest queryRequest);
	
	public List<SQLViewField> showFullColumnsBySql(String sql) throws Exception;
}
