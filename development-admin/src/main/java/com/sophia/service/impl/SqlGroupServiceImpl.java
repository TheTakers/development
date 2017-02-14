package com.sophia.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sophia.domain.Pager;
import com.sophia.domain.SqlGroup;
import com.sophia.repository.SQLGroupRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.SqlIdJdbcService;
import com.sophia.service.SqlGroupService;
import com.sophia.utils.SqlFilter;
import com.sophia.vo.QueryParam;

@Service
public class SqlGroupServiceImpl extends JpaRepositoryImpl<SQLGroupRepository> implements SqlGroupService  {
	Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired SqlIdJdbcService sqlNamedParamterJdbcOperations;

	private String sql="select t.*,c.name as ptext from tb_sm_sqlgroup t left join tb_sm_sqlgroup c on t.parentid =  c.id ";

	public String save(SqlGroup sqlGroup){

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
