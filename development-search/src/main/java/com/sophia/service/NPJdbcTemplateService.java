package com.sophia.service;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.sophia.utils.SQLFilter;
import com.sophia.vo.Grid;

public interface NPJdbcTemplateService {
	
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();
	
	public Grid grid(SQLFilter sqlFilter,Integer pageSize,Integer pageNo);
}
