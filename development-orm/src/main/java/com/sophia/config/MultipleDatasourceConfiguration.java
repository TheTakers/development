package com.sophia.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * master slaver
 * @author zkning
 */
@Configuration
public class MultipleDatasourceConfiguration {

	@Bean(name = "master") 
	@Primary 
	@ConfigurationProperties(prefix="spring.master") 
	public DataSource masterDataSource() { 
		return DataSourceBuilder.create().build(); 
	} 
	
	@Bean(name = "masterJdbcTemplate") 
  	public JdbcTemplate masterJdbcTemplate(@Qualifier("master")DataSource master) { 
  	      return new JdbcTemplate(master); 
	} 


	@Bean(name = "slaver") 
	@ConfigurationProperties(prefix="spring.slaver") 
	public DataSource slaverDataSource() { 
		return DataSourceBuilder.create().build(); 
	}  
	
	@Bean(name = "slaverJdbcTemplate") 
  	public JdbcTemplate slaverJdbcTemplate(@Qualifier("slaver")DataSource slaver) { 
  	      return new JdbcTemplate(slaver); 
	} 
}
