package com.sophia.service;

import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.sophia.utils.SQLFilter;
import com.sophia.vo.GridResponse;

public interface NPJdbcTemplateService {
	
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();
	
	public GridResponse<Map<String,Object>> grid(SQLFilter sqlFilter,Integer pageSize,Integer pageNo);
}
