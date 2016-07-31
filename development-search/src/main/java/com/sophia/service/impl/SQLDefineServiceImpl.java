package com.sophia.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sophia.domain.SQLDefine;
import com.sophia.repository.SQLDefineRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.SQLDefineService;

/**
 * SQL定义服务
 * @author zkning
 */
@Service
public class SQLDefineServiceImpl extends JpaRepositoryImpl<SQLDefineRepository> implements SQLDefineService {

	Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public String save(SQLDefine sqlDefine){
		
		//生成GROUP PATH
		return getRepository().save(sqlDefine).getId();
	}
}
