package com.sophia.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sophia.domain.Pager;
import com.sophia.domain.SQLDefine;
import com.sophia.exception.ServiceException;
import com.sophia.repository.SQLDefineRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.SqlDefineService;
import com.sophia.utils.SqlFilter;
import com.sophia.utils.SqlNamedParamterJdbcOperations;
import com.sophia.vo.QueryParam;

/**
 * SQL定义服务
 * @author zkning
 */
@Service
public class SQLDefineServiceImpl extends JpaRepositoryImpl<SQLDefineRepository> implements SqlDefineService {

	Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	@Autowired SqlNamedParamterJdbcOperations sqlNamedParamterJdbcOperations;
	private static final String sql ="select t.*,c.name as pText from tb_sm_sqldefine t left join tb_sm_sqlgroup c on t.groupid = c.id ";

	public String save(SQLDefine sqlDefine){
		
		//生成GROUP PATH
		return getRepository().save(sqlDefine).getId();
	}
	@Override
	public Pager<Map<String, Object>> list(QueryParam queryRequest) {
		SqlFilter sqlFilter = SqlFilter.getInstance();
		sqlFilter.addCondition(queryRequest.getCondition());
		sqlFilter.setMainSql(sql);
		if(queryRequest.getTreeNode()!=null){
			sqlFilter.EQ("groupid", queryRequest.getTreeNode().getString("id"));
		}
		return sqlNamedParamterJdbcOperations.filter(sqlFilter,queryRequest.getPageSize(),queryRequest.getPageNo());
	}
	@Override
	public Map<String,Object> findById(String id) {
		SqlFilter sqlFilter = SqlFilter.getInstance();
		sqlFilter.setMainSql(sql);
		sqlFilter.EQ("id", id);
		return sqlNamedParamterJdbcOperations.queryForMap(sqlFilter);
	}
	@Override
	public SQLDefine findBySqlId(String sqlId) {
		return getRepository().findBySqlId(sqlId);
	}
	@Override
	public Pager<Map<String, Object>> list(String sqlId, QueryParam queryRequest) {
		SQLDefine sqlDefine = getRepository().findBySqlId(sqlId);
		if(null == sqlDefine){
			return new Pager<Map<String, Object>>();
		}
		SqlFilter sqlFilter = SqlFilter.getInstance();
		sqlFilter.addCondition(queryRequest.getCondition());
		sqlFilter.setMainSql(sqlDefine.getSelectSql());
		return sqlNamedParamterJdbcOperations.filter(sqlFilter,queryRequest.getPageSize(),queryRequest.getPageNo());
	}
	@Override
	public List<Map<String, Object>> findAllBySqlId(String sqlId) {
		SQLDefine sqlDefine = getRepository().findBySqlId(sqlId);
		if(null == sqlDefine){
			throw new ServiceException("SQLID:"+sqlId+"未定义");
		}
		return namedParameterJdbcTemplate.queryForList(sqlDefine.getSelectSql(), new HashMap<String, Object>());
	}
	@Override
	public List<Map<String, Object>> findAllTable() {
		List<String> result = namedParameterJdbcTemplate.queryForList("show tables ", new HashMap<String, Object>(),String.class);
		List<Map<String, Object>> resultMap = new ArrayList<Map<String,Object>>();
		Map<String,Object> tableMap;
		for(String table : result){
			tableMap = new HashMap<String, Object>();
			tableMap.put("table", table);
			resultMap.add(tableMap);
		}
		return resultMap;
	}
}
