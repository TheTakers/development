package com.sophia.service;

import java.util.List;
import java.util.Map;

import com.sophia.domain.SQLView;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLViewRepository;
import com.sophia.vo.GridResponse;
import com.sophia.vo.QueryRequest;
import com.sophia.vo.search.TmSqlFieldVO;

public interface SQLViewService extends JpaRepository<SQLViewRepository>{
	
	public String save(SQLView sqlView);
	
	public Map<String,Object> findById(String id);
	
	public GridResponse<Map<String,Object>> list(QueryRequest queryRequest);
	
	public List<TmSqlFieldVO> getObtainFieldListBySql(String sql, int sqlIndex) throws Exception;
}
