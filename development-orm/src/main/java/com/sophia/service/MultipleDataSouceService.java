package com.sophia.service;

import org.springframework.jdbc.core.JdbcTemplate;

import com.sophia.constant.DataSourceEnum;

public interface MultipleDataSouceService {
	
	
	public JdbcTemplate getDataSourceByBean(DataSourceEnum me);
}
