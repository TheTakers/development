package com.sophia.service;

import java.util.Map;

import com.sophia.domain.Pager;
import com.sophia.domain.SqlGroup;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SqlGroupRepository;
import com.sophia.vo.QueryParam;

public interface SqlGroupService extends JpaRepository<SqlGroupRepository>{

	String save(SqlGroup sqlGroup);

	Map<String,Object> findById(String id);

	Pager<Map<String,Object>> list(QueryParam queryRequest);
}
