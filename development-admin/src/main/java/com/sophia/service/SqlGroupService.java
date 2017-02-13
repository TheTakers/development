package com.sophia.service;

import java.util.Map;

import com.sophia.domain.Pager;
import com.sophia.domain.SQLGroup;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLGroupRepository;
import com.sophia.vo.QueryParam;

public interface SqlGroupService extends JpaRepository<SQLGroupRepository>{

	String save(SQLGroup sqlGroup);

	Map<String,Object> findById(String id);

	Pager<Map<String,Object>> list(QueryParam queryRequest);
}
