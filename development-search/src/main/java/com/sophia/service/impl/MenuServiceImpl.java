package com.sophia.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sophia.domain.Menu;
import com.sophia.repository.MenuRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.request.GridResponse;
import com.sophia.request.QueryRequest;
import com.sophia.service.MenuService;
import com.sophia.service.JdbcTemplateService;
import com.sophia.utils.SQLFilter;

@Service
public class MenuServiceImpl extends JpaRepositoryImpl<MenuRepository> implements MenuService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired JdbcTemplateService npJdbcTemplateService;
	@Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	private static final String sql ="select t.*,c.name as pText from tb_basic_menu t left join tb_basic_menu c on t.pid = c.id ";
	
	public String save(Menu menu){
		return getRepository().save(menu).getId();
	}
	
	public List<String> getMenuPath(String id){
		String paths = getRepository().getPath(id);
		if(StringUtils.isNoneBlank(paths)){
			return  Arrays.asList(paths.split(","));
		}
		return null;
	}
	
	@Override
	public List<Menu> getTreeData() {
		List<Menu> data = getRepository().findAll();
		List<Menu> menuData = new ArrayList<>();
		for(Menu menu : data){
			if(menu.getPid() .equals( "0" )){
				menuData.add(menu);
			}
		}
		formatTreeData(menuData, data);
		return menuData;
	}
	
	@Override
	public List getMenuByName(String name) {
		
		if(StringUtils.isEmpty(name)){
			return getTreeData();
		}else{
			String sql  = "SELECT * FROM TB_BASIC_MENU T WHERE NAME LIKE :NAME ";
			Map<String,Object> paramMap = new HashMap<>();
			paramMap.put("NAME", "%"+name+"%");
			List<Map<String,Object>> menuList = namedParameterJdbcTemplate.queryForList(sql, paramMap);
			return menuList;
		}
	}
	
	
	private void formatTreeData(List<Menu> tree,List<Menu> data){
		
		if(!CollectionUtils.isEmpty(tree)){
			 
			for(Menu item : tree){
			 
				for(Menu menu : data){
					if(item.getId().equals(menu.getPid())){
						item.getChild().add(menu);
					}
				}
				formatTreeData(item.getChild(), data);
			}
		}
	}
	
	@Override
	public Map<String,Object> findById(String id) {
		SQLFilter sqlFilter = SQLFilter.getInstance();
		sqlFilter.setMainSql(sql);
		sqlFilter.EQ("id", id);
		return namedParameterJdbcTemplate.queryForMap(sqlFilter.getSql(), sqlFilter.getParams());
	}
	
	@Override
	public void delete(String id) {
		getRepository().delete(id);
	}
	
	@Override
	public GridResponse<Map<String,Object>> list(QueryRequest queryRequest) {

		SQLFilter sqlFilter = SQLFilter.getInstance();
		sqlFilter.addCondition(queryRequest.getCondition());
		sqlFilter.setMainSql(sql);
		
		if(queryRequest.getTreeNode()!=null){
			sqlFilter.EQ("pid", queryRequest.getTreeNode().getString("id"));
		}
		
		return npJdbcTemplateService.grid(sqlFilter,queryRequest.getPageSize(),queryRequest.getPageNo());
	}
}
