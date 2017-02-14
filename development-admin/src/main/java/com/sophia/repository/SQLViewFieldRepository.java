package com.sophia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sophia.domain.SqlViewField;

@Repository
public interface SQLViewFieldRepository extends JpaRepository<SqlViewField, String>{
	
	/**
	 * 根据viewId查询视图
	 * @param viewId
	 * @return
	 */
	public List<SqlViewField> getByViewId(String viewId);
	
	/**
	 * 根据viewId查询视图排序
	 * @param viewId
	 * @return
	 */
	public List<SqlViewField> getByViewIdOrderByIdxAsc(String viewId); 
}
