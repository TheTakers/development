package com.sophia.service;

import java.util.List;
import java.util.Map;

import com.sophia.domain.Menu;
import com.sophia.domain.Pager;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.MenuRepository;
import com.sophia.vo.QueryParam;

public interface MenuService  extends JpaRepository<MenuRepository>{
	
	/**
	 * 保存菜单
	 * @param menu
	 * @return   返回保存id
	 */
	 String save(Menu menu);
	/**
	 * 获取菜单名称
	 * @param name
	 * @return
	 */
	 List<Menu> findByNameLike(String name);
	
	/**
	 * 根据id获取菜单
	 * @param id
	 * @return
	 */
	 Map<String,Object> findById(String id);
	
	/**
	 * 根据ID删除
	 * @param id
	 */
	 void delete(String id);
	
	/**
	 * 获取菜单列表
	 * @param queryRequest
	 * @return
	 */
	 Pager<Map<String,Object>> list(QueryParam queryRequest);
}
