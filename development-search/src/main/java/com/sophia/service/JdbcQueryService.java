package com.sophia.service;

import com.sophia.vo.QueryFilter;
import com.sophia.vo.QueryResultSet;

public interface JdbcQueryService {
	
	
	public QueryResultSet queryResultSet(QueryFilter queryFilter);
	
}
