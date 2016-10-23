package com.sophia.service;

import java.util.List;
import java.util.Map;

import com.sophia.domain.SQLView;
import com.sophia.domain.SQLViewField;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLViewRepository;
import com.sophia.request.QueryRequest;
import com.sophia.request.SQLViewRequest;
import com.sophia.response.GridResponse;

public interface SQLViewService extends JpaRepository<SQLViewRepository>{
	
	public String save(SQLViewRequest sqlViewRequest);
	
	public SQLView findById(String id);
	
	public GridResponse<Map<String,Object>> list(QueryRequest queryRequest);
	
	public List<SQLViewField> showFullColumnsBySql(String sql) throws Exception;
}
