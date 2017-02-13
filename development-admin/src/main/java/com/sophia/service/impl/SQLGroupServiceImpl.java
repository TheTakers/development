package com.sophia.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sophia.domain.Pager;
import com.sophia.domain.SQLGroup;
import com.sophia.repository.SQLGroupRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.SqlIdJdbcService;
import com.sophia.service.SQLGroupService;
import com.sophia.utils.SqlFilter;
import com.sophia.vo.QueryParam;

@Service
public class SQLGroupServiceImpl extends JpaRepositoryImpl<SQLGroupRepository> implements SQLGroupService  {
	Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired SqlIdJdbcService sqlNamedParamterJdbcOperations;

	private String sql="select t.*,c.name as pText from TB_SM_SQLGROUP t left join TB_SM_SQLGROUP c on t.parentid =  c.id ";

	public String save(SQLGroup sqlGroup){

		//生成GROUP PATH
		return getRepository().save(sqlGroup).getId();
	}

	@Override
	public Map<String,Object> findById(String id) {
		SqlFilter sqlFilter = SqlFilter.getInstance();
		sqlFilter.setMainSql(sql);
		sqlFilter.EQ("id", id);
		return sqlNamedParamterJdbcOperations.queryForMap(sqlFilter);
	}

	public Pager<Map<String,Object>> list(QueryParam queryRequest){
		SqlFilter sqlFilter = SqlFilter.getInstance();
		sqlFilter.addCondition(queryRequest.getCondition());
		sqlFilter.setMainSql(sql);
		if(queryRequest.getTreeNode()!=null){
			sqlFilter.EQ("parentid", queryRequest.getTreeNode().getString("id"));
		}
		return sqlNamedParamterJdbcOperations.filter(sqlFilter,queryRequest.getPageSize(),queryRequest.getPageNo());
	}
}
