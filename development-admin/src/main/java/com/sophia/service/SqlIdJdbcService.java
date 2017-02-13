package com.sophia.service;

import java.util.Map;

import com.sophia.domain.Pager;
import com.sophia.service.impl.SqlIdJdbcServiceImpl.SqlIdNamedParamterJdbcHandler;
import com.sophia.utils.SqlFilter;

public interface SqlIdJdbcService {
	
	SqlIdNamedParamterJdbcHandler get(String sqlId);
	
	Pager<Map<String,Object>> filter(SqlFilter sqlFilter, Integer pageSize, Integer pageNo);
	
	Map<String,Object> queryForMap(String sql, Map<String, ?> paramMap);
	
	Map<String,Object> queryForMap(SqlFilter sqlFilter);
}
