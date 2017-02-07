package com.sophia.service;

import java.util.Map;

import com.sophia.domain.Pager;
import com.sophia.utils.SqlFilter;

public interface JdbcTemplateService {
	
	 Boolean execute(String sql);
	
	 Pager<Map<String,Object>> filter(SqlFilter sqlFilter,Integer pageSize,Integer pageNo);
	
	/**
	 * 根据SQL获取一个MAP对象
	 * @param sql
	 * @param param
	 * @return
	 */
	 Map<String,Object> queryForMap(String sql,Map<String,Object> paramMap);
}
