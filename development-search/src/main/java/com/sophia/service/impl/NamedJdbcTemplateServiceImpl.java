package com.sophia.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.sophia.service.NPJdbcTemplateService;
import com.sophia.utils.SQLFilter;
import com.sophia.vo.GridResponse;

@Service
public class NamedJdbcTemplateServiceImpl implements NPJdbcTemplateService {
	
	@Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Override
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}
	@Override
	public GridResponse<Map<String,Object>> grid(SQLFilter sqlFilter, Integer pageSize, Integer pageNo) {
		GridResponse<Map<String,Object>> gridResponse = new GridResponse<Map<String,Object>>();
		gridResponse.setContent(namedParameterJdbcTemplate.queryForList( sqlFilter.getLimitSql(pageNo, pageSize) , sqlFilter.getParams()));
		gridResponse.setTotalElements(namedParameterJdbcTemplate.queryForObject(sqlFilter.getCountSql(), sqlFilter.getParams(), Integer.class));
		gridResponse.setPageSize(pageSize);
		gridResponse.setPageNo(pageNo);
		return gridResponse;
	}
}
