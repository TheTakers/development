package com.sophia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sophia.domain.SQLDefine;

@Repository
public interface SQLDefineRepository  extends JpaRepository<SQLDefine, String>{
	
	public Page<SQLDefine> findAll(Pageable pageable);
	
	public SQLDefine findBySqlId(String sqlId);
}
