package com.sophia.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sophia.domain.SQLDefine;
import com.sophia.repository.SQLDefineRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.request.QueryRequest;
import com.sophia.response.GridResponse;
import com.sophia.service.JdbcTemplateService;
import com.sophia.service.SQLDefineService;
import com.sophia.utils.SQLFilter;

/**
 * SQL定义服务
 * @author zkning
 */
@Service
public class SQLDefineServiceImpl extends JpaRepositoryImpl<SQLDefineRepository> implements SQLDefineService {

	Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	
	@Autowired JdbcTemplateService npJdbcTemplateService;
	
	private static final String sql ="select t.*,c.name as pText from tb_sm_sqldefine t left join tb_sm_sqlgroup c on t.groupid = c.id ";

	public String save(SQLDefine sqlDefine){
		
		//生成GROUP PATH
		return getRepository().save(sqlDefine).getId();
	}
	@Override
	public GridResponse list(QueryRequest queryRequest) {
		SQLFilter sqlFilter = SQLFilter.getInstance();
		sqlFilter.addCondition(queryRequest.getCondition());
		sqlFilter.setMainSql(sql);
		if(queryRequest.getTreeNode()!=null){
			sqlFilter.EQ("groupid", queryRequest.getTreeNode().getString("id"));
		}
		return npJdbcTemplateService.grid(sqlFilter,queryRequest.getPageSize(),queryRequest.getPageNo());
	}
	@Override
	public Map<String,Object> findById(String id) {
		SQLFilter sqlFilter = SQLFilter.getInstance();
		sqlFilter.setMainSql(sql);
		sqlFilter.EQ("id", id);
		return namedParameterJdbcTemplate.queryForMap(sqlFilter.getSql(), sqlFilter.getParams());
	}
	@Override
	public SQLDefine findBySqlId(String sqlId) {
		return getRepository().findBySqlId(sqlId);
	}
}
