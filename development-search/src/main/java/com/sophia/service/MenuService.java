package com.sophia.service;

import com.sophia.domain.Menu;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.MenuRepository;

public interface MenuService  extends JpaRepository<MenuRepository>{
	
	public String insert(Menu menu);
}
