package com.sophia.service;

import java.util.List;
import java.util.Map;

import com.sophia.domain.Menu;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.MenuRepository;
import com.sophia.request.QueryRequest;
import com.sophia.response.GridResponse;

public interface MenuService  extends JpaRepository<MenuRepository>{
	
	public String save(Menu menu);
	
	public List<Menu> getTreeData();
	
	public List getMenuByName(String name);
	
	public Map<String,Object> findById(String id);
	
	public void delete(String id);
	public GridResponse<Map<String,Object>> list(QueryRequest queryRequest);
}
