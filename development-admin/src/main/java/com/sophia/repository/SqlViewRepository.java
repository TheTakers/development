package com.sophia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sophia.domain.SqlView;

@Repository
public interface SqlViewRepository  extends JpaRepository<SqlView, String>{
	
	public Page<SqlView> findAll(Pageable pageable);
	
	public SqlView getByCode(String code);
}
