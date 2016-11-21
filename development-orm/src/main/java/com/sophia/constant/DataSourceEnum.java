package com.sophia.constant;

/**
 *
 * @author zkning
 */
public enum DataSourceEnum {
	
	
	master("masterJdbcTemplate"),slaver("slaverJdbcTemplate");
	
	private String jdbcTemplate;
	
	private DataSourceEnum(String jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public String getJdbcTemplate() {
		return jdbcTemplate;
	}



	public void setJdbcTemplate(String jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}



	public static DataSourceEnum getDataSource(String dataSource){
		
		 if(master.name().equals(dataSource)){
			 return master;
		 }
		 if(slaver.name().equals(dataSource)){
			 return slaver;
		 }
		 return null;
	}
	
	
	
//	public static final String DATASOURCE_MASTER = "master";
//	public static final String DATASOURCE_SLAVER = "slaver";
}
