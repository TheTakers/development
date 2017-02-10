package com.sophia.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * master slaver
 * @author zkning
 */
@Configuration
public class MultipleDatasourceConfiguration {
	
	public static final String jdbcTemplate_master = "jdbcTemplateMaster";
	public static final String jdbcTemplate_slaver = "jdbcTemplateSlaver";
	

	@Bean(name = jdbcTemplate_master) 
	@Primary 
	@ConfigurationProperties(prefix="spring.master") 
	public DataSource masterDataSource() { 
		return DataSourceBuilder.create().build(); 
	} 

	@Bean(name = jdbcTemplate_slaver) 
	@ConfigurationProperties(prefix="spring.slaver") 
	public DataSource slaverDataSource() { 
		return DataSourceBuilder.create().build(); 
	}  
}
