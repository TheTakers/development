package com.sophia.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.sophia.constant.DataSourceEnum;
import com.sophia.constant.SQLDefineConstant;
import com.sophia.domain.SQLDefine;
import com.sophia.exception.ServiceException;
import com.sophia.response.GridResponse;
import com.sophia.service.MultipleDataSouceService;
import com.sophia.service.SQLDefineService;
import com.sophia.service.SQLIDService;
import com.sophia.utils.SQLLimitFactory;

/**
 *  
 * @author zkning
 */
@Service
public class SQLIDServiceImpl implements SQLIDService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired SQLDefineService sqlDefineService;
	@Autowired MultipleDataSouceService multipleDataSouceService;
	
	public <T> List<T> queryForList(String SQLID ,Map<String,Object> args,Class<T> elementType)  {
		SQLDefine selDefine = get(SQLID);
		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedParameterJdbcTemplate(selDefine.getDatasource());
		return namedJdbcTemplate.queryForList(selDefine.getSelectSql(), args, elementType);
	}
	
	public Map<String, Object> queryForMap(String SQLID, Map<String,Object> args){
		SQLDefine selDefine = get(SQLID);
		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedParameterJdbcTemplate(selDefine.getDatasource());
		return namedJdbcTemplate.queryForMap(selDefine.getSelectSql(),args);
	}
	
	public List<Map<String, Object>> queryForList(String SQLID, Map<String,Object> args){
		SQLDefine selDefine = get(SQLID);
		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedParameterJdbcTemplate(selDefine.getDatasource());
		return namedJdbcTemplate.queryForList(selDefine.getSelectSql(),args);
	}
	
	public <T> T queryForObject(String SQLID, Class<T> requiredType,Map<String,Object> args){
		SQLDefine selDefine = get(SQLID);
		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedParameterJdbcTemplate(selDefine.getDatasource());
		return namedJdbcTemplate.queryForObject(selDefine.getSelectSql(),args,requiredType);
	}
	
	public Integer count(String SQLID,Map<String,Object> args){
		SQLDefine selDefine = get(SQLID);
		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedParameterJdbcTemplate(selDefine.getDatasource());
		return namedJdbcTemplate.queryForObject(SQLLimitFactory.buildCountSQL(selDefine.getSelectSql()),args,Integer.class);
	}
	
	public <T> T execute(String SQLID, PreparedStatementCallback<T> action){
		SQLDefine selDefine = get(SQLID);
		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedParameterJdbcTemplate(selDefine.getDatasource());
		return namedJdbcTemplate.execute(selDefine.getSelectSql(),action);
	}
	
	public <T> GridResponse<T> findAll(String SQLID ,Map<String,Object> args,Class<T> elementType,Integer pageSize,Integer pageNo){
		SQLDefine selDefine = get(SQLID);
		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedParameterJdbcTemplate(selDefine.getDatasource());

		GridResponse<T> grid = new GridResponse<T>();
		//TODO getJdbcTemplate().getDataSource().getConnection().getMetaData().getDatabaseProductName())
		grid.setContent(namedJdbcTemplate.queryForList(SQLLimitFactory.createLimit(selDefine.getSelectSql(),pageSize,pageNo,SQLLimitFactory.DATABASE_MYSQL), args, elementType));
		grid.setTotalElements(namedJdbcTemplate.queryForObject(SQLLimitFactory.buildCountSQL(selDefine.getSelectSql()),args,Integer.class));
		grid.setPageSize(pageSize); 
		grid.setPageNo(pageNo);
		return grid;
	}
	
	private SQLDefine get(String SQLId){
		
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
	
	private NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(String dataSource){
		return new NamedParameterJdbcTemplate(getJdbcTemplate(dataSource));
	}
	
	private JdbcTemplate getJdbcTemplate(String dataSource){
		return multipleDataSouceService.getDataSourceByBean(DataSourceEnum.getDataSource(dataSource));
	}
}
