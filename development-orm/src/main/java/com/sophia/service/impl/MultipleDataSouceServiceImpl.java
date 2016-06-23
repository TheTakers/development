package com.sophia.service.impl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sophia.constant.DataSourceEnum;
import com.sophia.service.MultipleDataSouceService;

/**
 * 多数据源服务
 * @author zkning
 */
@Service
public class MultipleDataSouceServiceImpl extends ApplicationObjectSupport implements MultipleDataSouceService {
		
	@Override
	public JdbcTemplate getDataSourceByBean(DataSourceEnum me) {
		
		if(null==me)
			throw new ServiceException("数据源不能为空");
		
		Object bean  = getApplicationContext().getBean(me.getJdbcTemplate());
		if(bean == null)
			throw new ServiceException("未定义的数据源");
		
		return (JdbcTemplate)bean;
	}

}
