package com.sophia.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sophia.domain.Res;
import com.sophia.domain.Pager;
import com.sophia.repository.ResRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.ResService;
import com.sophia.service.SqlIdJdbcService;
import com.sophia.utils.SqlFilter;
import com.sophia.vo.QueryParam;

@Service
public class ResServiceImpl extends JpaRepositoryImpl<ResRepository> implements ResService {
	Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	@Autowired SqlIdJdbcService sqlIdJdbcService;
	
	private static final String sql ="select t.*,c.name as pText from tb_basic_res t left join tb_basic_res c on t.pid = c.id ";
	public String save(Res res){
		return getRepository().save(res).getId();
	}
	
	/**
	 * 获取树数据
	 * @return
	 */
	private List<Res> findAllOrderByIdx() {
		List<Res> data = getRepository().findAll(new Sort("idx"));
		List<Res> menuData = new ArrayList<>();
		for(Res res : data){
			if(res.getPid() .equals( "0" )){
				menuData.add(res);
			}
		}
		formatTreeData(menuData, data);
		return menuData;
	}
	
	@Override
	public List<Res> findByNameLike(String name) {
		if(StringUtils.isEmpty(name)){
			return this.findAllOrderByIdx();
		}else{
			return this.getRepository().findByNameLike(name);
		}
	}
	
	private void formatTreeData(List<Res> tree,List<Res> data){
		if(!CollectionUtils.isEmpty(tree)){
			for(Res item : tree){
				for(Res res : data){
					if(item.getId().equals(res.getPid())){
						item.getChild().add(res);
					}
				}
				formatTreeData(item.getChild(), data);
			}
		}
	}
	
	@Override
	public Map<String,Object> findById(String id) {
		SqlFilter sqlFilter = SqlFilter.getInstance();
		sqlFilter.setMainSql(sql);
		sqlFilter.EQ("id", id);
		return sqlIdJdbcService.queryForMap(sqlFilter);
	}
	
	@Override
	public void delete(String id) {
		this.getRepository().delete(id);
	}
	
	@Override
	public Pager<Map<String,Object>> list(QueryParam queryRequest) {
		SqlFilter sqlFilter = new SqlFilter(queryRequest.getCondition());
		sqlFilter.setMainSql(sql);
		if(queryRequest.getTreeNode()!=null){
			sqlFilter.EQ("pid", queryRequest.getTreeNode().getString("id"));
		}
		return sqlIdJdbcService.filter(sqlFilter,queryRequest.getPageSize(),queryRequest.getPageNo());
	}
}
