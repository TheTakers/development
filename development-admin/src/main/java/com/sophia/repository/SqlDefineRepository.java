package com.sophia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sophia.domain.SqlDefine;

@Repository
public interface SqlDefineRepository  extends JpaRepository<SqlDefine, String>{
	
	public Page<SqlDefine> findAll(Pageable pageable);
	
	public SqlDefine findBySqlId(String sqlId);
	
}
