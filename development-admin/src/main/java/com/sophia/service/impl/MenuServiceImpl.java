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

import com.sophia.domain.Menu;
import com.sophia.domain.Pager;
import com.sophia.repository.MenuRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.MenuService;
import com.sophia.service.SqlIdJdbcService;
import com.sophia.utils.SqlFilter;
import com.sophia.vo.QueryParam;

@Service
public class MenuServiceImpl extends JpaRepositoryImpl<MenuRepository> implements MenuService {
	Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	@Autowired SqlIdJdbcService sqlIdJdbcService;
	
	private static final String sql ="select t.*,c.name as pText from tb_basic_menu t left join tb_basic_menu c on t.pid = c.id ";
	public String save(Menu menu){
		return getRepository().save(menu).getId();
	}
	
	/**
	 * 获取树数据
	 * @return
	 */
	private List<Menu> findAllOrderByIdx() {
		List<Menu> data = getRepository().findAll(new Sort("idx"));
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
	public List<Menu> findByNameLike(String name) {
		if(StringUtils.isEmpty(name)){
			return this.findAllOrderByIdx();
		}else{
			return this.getRepository().findByNameLike(name);
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
