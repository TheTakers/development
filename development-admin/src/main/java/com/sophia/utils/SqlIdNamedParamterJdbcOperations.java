package com.sophia.utils;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.sophia.domain.Pager;
import com.sophia.domain.SQLDefine;
import com.sophia.service.SqlDefineService;

/**
 * sqlId数据操作
 * @author zkning
 */
@Component
public class SqlIdNamedParamterJdbcOperations extends ApplicationObjectSupport{
	private SqlDefineService sqlDefineService;
	private SQLDefine sqlDefine;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public SqlIdNamedParamterJdbcHandler get(String sqlId){
		SqlIdNamedParamterJdbcHandler SqlIdNamedParamterJdbcHandler = new SqlIdNamedParamterJdbcHandler();
		this.sqlDefineService = (SqlDefineService) getApplicationContext().getBean(SqlDefineService.class);
		
		//根据sqlDefine
		this.sqlDefine = sqlDefineService.findBySqlId(sqlId);
		
		//获取数据源
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.getDataSource(sqlDefine.getDatasource()));
		return SqlIdNamedParamterJdbcHandler;
	}
	
	private DataSource getDataSource(String jdbcTemplate){
		return (DataSource) getApplicationContext().getBean(jdbcTemplate);
	}
	
	  public class SqlIdNamedParamterJdbcHandler{
		  
		public  <T> T queryForObject(Map<String, ?> paramMap,Class<T> requiredType) {
			// TODO Auto-generated method stub
			return namedParameterJdbcTemplate.queryForObject(sqlDefine.getSelectSql(), paramMap, requiredType);
		}

		public Map<String, ?> queryForMap(Map<String, ?> paramMap) {
			// TODO Auto-generated method stub
			return namedParameterJdbcTemplate.queryForMap(sqlDefine.getSelectSql(), paramMap);
		}

		public <T> List<T> queryForList(Map<String, ?> paramMap, Class<T> elementType) {
			// TODO Auto-generated method stub
			return namedParameterJdbcTemplate.queryForList(sqlDefine.getSelectSql(), paramMap, elementType);
		}

		public List<Map<String, Object>> queryForList(Map<String, ?> paramMap) {
			// TODO Auto-generated method stub
			return namedParameterJdbcTemplate.queryForList(sqlDefine.getSelectSql(), paramMap);
		}

		public <T> Pager<T> queryForPager(String sqlId, Map<String, ?> paramMap, Class<T> elementType, Integer pageSize,
				Integer pageNo) {
			Pager<T> pager = new Pager<T>();
			//TODO getJdbcTemplate().getDataSource().getConnection().getMetaData().getDatabaseProductName())
			pager.setContent(namedParameterJdbcTemplate.queryForList(SqlPagerBuilder.createPager(sqlDefine.getSelectSql(),pageSize,pageNo,SqlPagerBuilder.DATABASE_MYSQL), paramMap, elementType));
			pager.setTotalElements(namedParameterJdbcTemplate.queryForObject(SqlPagerBuilder.countWrap(sqlDefine.getSelectSql()),paramMap,Integer.class));
			pager.setPageSize(pageSize); 
			pager.setPageNo(pageNo);
			return pager;
		}
	}
}
