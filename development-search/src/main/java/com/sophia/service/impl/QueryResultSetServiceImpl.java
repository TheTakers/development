package com.sophia.service.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sophia.exception.ServiceException;
import com.sophia.service.QueryResultSetService;
import com.sophia.vo.QueryResultSet;

/**
 * 结果集适配器
 * @author zkning
 */
@Service
public class QueryResultSetServiceImpl implements QueryResultSetService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public List<Map<String, Object>> toHashMap(QueryResultSet queryResultSet){
		return null;
	}
}
