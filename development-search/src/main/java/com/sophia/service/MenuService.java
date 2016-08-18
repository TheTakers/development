package com.sophia.service;

import java.util.List;

import com.sophia.domain.Menu;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.MenuRepository;
import com.sophia.vo.Grid;
import com.sophia.vo.QueryRequest;

public interface MenuService  extends JpaRepository<MenuRepository>{
	
	public String save(Menu menu);
	
	public List<Menu> getTreeData();
	
	public List getMenuByName(String name);
	
	public Menu findById(String id);
	
	public void delete(String id);
	
	/**
	 * 获取菜单导航路径
	 * @param id
	 * @return
	 */
	public List<String>  getMenuPath(String id);
	
	public Grid list(QueryRequest queryRequest);
}
