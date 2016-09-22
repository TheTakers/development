package com.sophia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sophia.domain.SQLView;

@Repository
public interface SQLViewRepository  extends JpaRepository<SQLView, String>{
	
	public Page<SQLView> findAll(Pageable pageable);
}
