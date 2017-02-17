package com.sophia.service;

import java.util.List;
import java.util.Map;

import com.sophia.domain.Res;
import com.sophia.domain.Pager;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.ResRepository;
import com.sophia.vo.QueryParam;

public interface ResService  extends JpaRepository<ResRepository>{
	
	/**
	 * 保存菜单
	 * @param res
	 * @return   返回保存id
	 */
	 String save(Res res);
	/**
	 * 获取菜单名称
	 * @param name
	 * @return
	 */
	 List<Res> findByNameLike(String name);
	
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
