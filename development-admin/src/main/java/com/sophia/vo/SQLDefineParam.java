package com.sophia.vo;

import org.hibernate.validator.constraints.NotBlank;

public class SQLDefineParam extends CrudParam{
	
	@NotBlank
	private String sqlId;
	private String sqlName;
	private String selectSql;
	private String datasource;
	/**
	 * 是否缓存
	 */
	private Integer cache;
	
	/**
	 * 1-编辑,2-发布
	 */
	private Integer status;
	
	/**
	 * 功能描述
	 */
	private String sqldesc;
	
	/**
	 * 所属功能组
	 */
	@NotBlank
	private String groupId;
	
	@NotBlank
	private String masterTableId;
	
	@NotBlank
	private String masterTable;
	
	public String getMasterTableId() {
		return masterTableId;
	}
	public void setMasterTableId(String masterTableId) {
		this.masterTableId = masterTableId;
	}
	public String getMasterTable() {
		return masterTable;
	}
	public void setMasterTable(String masterTable) {
		this.masterTable = masterTable;
	}
	public String getSqldesc() {
		return sqldesc;
	}
	public void setSqldesc(String sqldesc) {
		this.sqldesc = sqldesc;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getSqlId() {
		return sqlId;
	}
	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}
	public String getSqlName() {
		return sqlName;
	}
	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}
	 
	public String getSelectSql() {
		return selectSql;
	}
	public void setSelectSql(String selectSql) {
		this.selectSql = selectSql;
	}
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	public Integer getCache() {
		return cache;
	}
	public void setCache(Integer cache) {
		this.cache = cache;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
