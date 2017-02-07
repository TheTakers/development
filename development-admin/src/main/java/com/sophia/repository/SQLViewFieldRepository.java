package com.sophia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sophia.domain.SQLViewField;

@Repository
public interface SQLViewFieldRepository extends JpaRepository<SQLViewField, String>{
	
	/**
	 * 根据viewId查询视图
	 * @param viewId
	 * @return
	 */
	public List<SQLViewField> getByViewId(String viewId);
	
	/**
	 * 根据viewId查询视图排序
	 * @param viewId
	 * @return
	 */
	public List<SQLViewField> getByViewIdOrderByIdxAsc(String viewId); 
}
