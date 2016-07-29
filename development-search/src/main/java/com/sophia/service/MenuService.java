package com.sophia.service;

import java.util.List;

import com.sophia.domain.Menu;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.MenuRepository;

public interface MenuService  extends JpaRepository<MenuRepository>{
	
	public String save(Menu menu);
	
	public List<Menu> getTreeData();
}
