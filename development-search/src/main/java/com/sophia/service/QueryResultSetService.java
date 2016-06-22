package com.sophia.service;

import java.util.List;
import java.util.Map;

import com.sophia.vo.QueryResultSet;

public interface QueryResultSetService {
	
	
	public List<Map<String,Object>>	toHashMap(QueryResultSet queryResultSet);
}
