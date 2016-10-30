package com.sophia.service;

import java.util.Map;

import com.sophia.response.GridResponse;
import com.sophia.utils.SQLFilter;

public interface JdbcTemplateService {
	
	public Boolean execute(String sql);
	
	public GridResponse<Map<String,Object>> grid(SQLFilter sqlFilter,Integer pageSize,Integer pageNo);
	
	/**
	 * 根据SQL获取一个MAP对象
	 * @param sql
	 * @param param
	 * @return
	 */
	public Map<String,Object> queryForMap(String sql,Map<String,Object> paramMap);
}
