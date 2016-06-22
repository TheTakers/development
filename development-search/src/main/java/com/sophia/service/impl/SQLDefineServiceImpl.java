package com.sophia.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sophia.repository.SQLDefineRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.JdbcQueryService;
import com.sophia.service.QueryResultSetService;
import com.sophia.service.SQLDefineService;
import com.sophia.vo.QueryFilter;

/**
 * SQL定义服务
 * @author zkning
 */
@Service
public class SQLDefineServiceImpl extends JpaRepositoryImpl<SQLDefineRepository> implements SQLDefineService {

	Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired QueryResultSetService queryResultSetService;
	@Autowired JdbcQueryService jdbcQueryService;
	
	@Override
	public List<Map<String, Object>> findBySQLId(String SQLId,Object...args) {
		QueryFilter queryFilter = QueryFilter.getInstance();
		queryFilter.setSqlId(SQLId);
		queryFilter.setArgs(args);
		return jdbcQueryService.queryResultSet(queryFilter).getResultSet();
	}
	
}
