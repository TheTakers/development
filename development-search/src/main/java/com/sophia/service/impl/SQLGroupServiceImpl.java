package com.sophia.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sophia.domain.SQLGroup;
import com.sophia.repository.SQLGroupRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.SQLGroupService;

@Service
public class SQLGroupServiceImpl extends JpaRepositoryImpl<SQLGroupRepository> implements SQLGroupService  {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String save(SQLGroup sqlGroup){
		
		//生成GROUP PATH
		return getRepository().save(sqlGroup).getId();
	}

	 
}
