package com.sophia.service.impl;

import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sophia.constant.DataSourceEnum;
import com.sophia.constant.SQLDefineConstant;
import com.sophia.domain.SQLDefine;
import com.sophia.exception.ServiceException;
import com.sophia.service.JdbcQueryService;
import com.sophia.service.MultipleDataSouceService;
import com.sophia.service.QueryResultSetService;
import com.sophia.service.SQLDefineService;
import com.sophia.utils.PaginationSqlFactory;
import com.sophia.vo.QueryFilter;
import com.sophia.vo.QueryResultSet;

@Service
public class JdbcQueryServiceImpl implements JdbcQueryService{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired SQLDefineService sqlDefineService;
	@Autowired MultipleDataSouceService multipleDataSouceService;
	@Autowired QueryResultSetService queryResultSetService;
	
	
	private SQLDefine checkSQLDefine(String SQLId){
		
		if(StringUtils.isBlank(SQLId)){
			throw new ServiceException("SQLId不能为空");
		}

		SQLDefine sqlDefine = sqlDefineService.getRepository().findBySqlId(SQLId);

		if(sqlDefine == null){
			throw new ServiceException("SQLId未定义");
		}
		
		if(SQLDefineConstant.SQLDEFINE_STATUS_ACTIVE != sqlDefine.getStatus()){
			throw new ServiceException("SQLId状态未发布");
		}
		return sqlDefine;
	}
	
	public QueryResultSet queryResultSet(QueryFilter queryFilter) {

		SQLDefine sqlDefine = checkSQLDefine(queryFilter.getSqlId());
		try {
			
			QueryResultSet queryResultSet = QueryResultSet.getInstance(); 
			
			JdbcTemplate jdbcTemplate = getTemplate(sqlDefine.getDatasource());
			
			if(SQLDefineConstant.SQLDEFINE_TYPE_SQL == sqlDefine.getType()){
			 
				if(queryFilter.paging){
					
					queryResultSet.setResultSet(jdbcTemplate.queryForList(PaginationSqlFactory.buildPaginationSQL(sqlDefine.getDefSelectSql(), queryFilter.getPageSize(), queryFilter.getPageNo(), jdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName()), 
							queryFilter.getArgs()));
					
					queryResultSet.setMaxCount(jdbcTemplate.queryForObject(sqlDefine.getDefSelectSql(), queryFilter.getArgs(), Integer.class));
					
				}else{
					queryResultSet.setResultSet(jdbcTemplate.queryForList(sqlDefine.getDefSelectSql(), queryFilter.getArgs()));
				}
			}
			
			if(SQLDefineConstant.SQLDEFINE_TYPE_CURSOR == sqlDefine.getType()){
				jdbcTemplate.execute(sqlDefine.getDefSelectSql(), queryFilter.getCallableStatementCallback());
			}
			
			return queryResultSet;
		} catch (SQLException e) {
			logger.error("SQLId:{}查询异常:{}",queryFilter.getSqlId(),e);
			throw new ServiceException("SQLId:"+queryFilter.getSqlId()+"查询异常");
		}
	}
	
	private JdbcTemplate getTemplate(String dataSource){
		return multipleDataSouceService.getDataSourceByBean(DataSourceEnum.getDataSource(dataSource));
	}
	
}
