package com.sophia.service;

import java.util.Map;

import com.sophia.request.GridResponse;
import com.sophia.utils.SQLFilter;

public interface JdbcTemplateService {
	
	public Boolean execute(String sql);
	
	public GridResponse<Map<String,Object>> grid(SQLFilter sqlFilter,Integer pageSize,Integer pageNo);
}
