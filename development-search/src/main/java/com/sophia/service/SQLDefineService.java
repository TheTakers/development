package com.sophia.service;

import com.sophia.domain.SQLDefine;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLDefineRepository;

public interface SQLDefineService extends JpaRepository<SQLDefineRepository>{
	
	public String save(SQLDefine sqlDefine);
}
