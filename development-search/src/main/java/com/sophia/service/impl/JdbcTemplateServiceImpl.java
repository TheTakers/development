package com.sophia.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sophia.response.GridResponse;
import com.sophia.service.JdbcTemplateService;
import com.sophia.utils.SQLFilter;

@Service
public class JdbcTemplateServiceImpl implements JdbcTemplateService {
	
	@Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	 
	@Override
	public GridResponse<Map<String,Object>> grid(SQLFilter sqlFilter, Integer pageSize, Integer pageNo) {
		GridResponse<Map<String,Object>> gridResponse = new GridResponse<Map<String,Object>>();
		gridResponse.setContent(namedParameterJdbcTemplate.queryForList( sqlFilter.getLimitSql(pageNo, pageSize) , sqlFilter.getParams()));
		gridResponse.setTotalElements(namedParameterJdbcTemplate.queryForObject(sqlFilter.getCountSql(), sqlFilter.getParams(), Integer.class));
		gridResponse.setPageSize(pageSize);
		gridResponse.setPageNo(pageNo);
		return gridResponse;
	}
	
	@Override
	public Boolean execute(final String sql){
		return namedParameterJdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement arg) throws SQLException, DataAccessException {
				return arg.execute(sql);
			}
		});
	}

	@Override
	public Map<String, Object> queryForMap(String sql, Map<String, Object> paramMap) {
		List<Map<String,Object>> listData = namedParameterJdbcTemplate.queryForList(sql, paramMap);
		if(CollectionUtils.isEmpty(listData)){
			return new HashMap<String, Object>();
		}
		return listData.get(0);
	}
}
