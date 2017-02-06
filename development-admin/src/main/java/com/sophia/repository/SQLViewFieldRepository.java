package com.sophia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sophia.domain.SQLViewField;

@Repository
public interface SQLViewFieldRepository extends JpaRepository<SQLViewField, String>{
	
	public List<SQLViewField> getByViewId(String viewId);
	
	public List<SQLViewField> getByViewIdOrderByIdxAsc(String viewId); 
}
