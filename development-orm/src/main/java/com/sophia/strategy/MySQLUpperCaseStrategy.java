package com.sophia.strategy;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * MySQL table toUpperCase
 * @author zkning
 */
public class MySQLUpperCaseStrategy extends ImprovedNamingStrategy {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String tableName(String tableName) {
		return tableName.toUpperCase();
	}
	
	@Override
	public String propertyToColumnName(String propertyName) {
		return propertyName;
	}
	
	
}
