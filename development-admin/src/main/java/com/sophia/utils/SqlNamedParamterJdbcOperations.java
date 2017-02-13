package com.sophia.utils;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
public class SqlNamedParamterJdbcOperations extends ApplicationObjectSupport{
	@Autowired SqlDefineService sqlDefineService;
	@Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public SqlIdNamedParamterJdbcHandler get(String sqlId){
		SQLDefine sqlDefine = sqlDefineService.findBySqlId(sqlId);
		SqlIdNamedParamterJdbcHandler SqlIdNamedParamterJdbcHandler = new SqlIdNamedParamterJdbcHandler(new NamedParameterJdbcTemplate(this.getDataSource(sqlDefine.getDatasource()))
				,sqlDefine);
		this.sqlDefineService = (SqlDefineService) getApplicationContext().getBean(SqlDefineService.class);
		return SqlIdNamedParamterJdbcHandler;
	}
	
	private DataSource getDataSource(String jdbcTemplate){
		return (DataSource) getApplicationContext().getBean(jdbcTemplate);
	}
	
	public Pager<Map<String,Object>> filter(SqlFilter sqlFilter, Integer pageSize, Integer pageNo) {
		Pager<Map<String,Object>> pager = new Pager<Map<String,Object>>();
		pager.setContent(namedParameterJdbcTemplate.queryForList( sqlFilter.createPager(pageNo, pageSize) , sqlFilter.getParams()));
		pager.setTotalElements(namedParameterJdbcTemplate.queryForObject(sqlFilter.countSql(), sqlFilter.getParams(), Integer.class));
		pager.setPageSize(pageSize);
		pager.setPageNo(pageNo);
		return pager;
	}

	public class SqlIdNamedParamterJdbcHandler{
		private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
		private SQLDefine sqlDefine;
		public SqlIdNamedParamterJdbcHandler(NamedParameterJdbcTemplate namedParameterJdbcTemplate,SQLDefine sqlDefine){
			this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
			this.sqlDefine = sqlDefine;
		}
		
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
		
		public Pager<Map<String,Object>> filter(SqlFilter sqlFilter, Integer pageSize, Integer pageNo) {
			Pager<Map<String,Object>> pager = new Pager<Map<String,Object>>();
			pager.setContent(namedParameterJdbcTemplate.queryForList(sqlFilter.createPager(pageNo, pageSize) , sqlFilter.getParams()));
			pager.setTotalElements(namedParameterJdbcTemplate.queryForObject(sqlFilter.countSql(), sqlFilter.getParams(), Integer.class));
			pager.setPageSize(pageSize);
			pager.setPageNo(pageNo);
			return pager;
		}
	}
}
