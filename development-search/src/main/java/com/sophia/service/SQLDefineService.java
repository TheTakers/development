package com.sophia.service;

import java.util.List;
import java.util.Map;

import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLDefineRepository;

public interface SQLDefineService extends JpaRepository<SQLDefineRepository>{
	
	
	public List<Map<String,Object>> findBySQLId(String SQLId,Object...args);
}
