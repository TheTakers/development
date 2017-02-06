package com.sophia.service;

import java.util.Map;

import com.sophia.domain.SQLGroup;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLGroupRepository;
import com.sophia.request.QueryRequest;
import com.sophia.response.GridResponse;

public interface SQLGroupService extends JpaRepository<SQLGroupRepository>{

	String save(SQLGroup sqlGroup);

	Map<String,Object> findById(String id);

	GridResponse<Map<String,Object>> list(QueryRequest queryRequest);
}
