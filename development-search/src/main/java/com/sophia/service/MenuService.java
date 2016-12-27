package com.sophia.service;

import java.util.List;
import java.util.Map;

import com.sophia.domain.Menu;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.MenuRepository;
import com.sophia.request.QueryRequest;
import com.sophia.response.GridResponse;

public interface MenuService  extends JpaRepository<MenuRepository>{
	
	/**
	 * 保存菜单
	 * @param menu
	 * @return   返回保存id
	 */
	public String save(Menu menu);
	/**
	 * 获取菜单名称
	 * @param name
	 * @return
	 */
	public List<Menu> findByNameLike(String name);
	
	/**
	 * 根据id获取菜单
	 * @param id
	 * @return
	 */
	public Map<String,Object> findById(String id);
	
	/**
	 * 根据ID删除
	 * @param id
	 */
	public void delete(String id);
	
	/**
	 * 获取菜单列表
	 * @param queryRequest
	 * @return
	 */
	public GridResponse<Map<String,Object>> list(QueryRequest queryRequest);
}
