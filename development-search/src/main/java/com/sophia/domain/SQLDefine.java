package com.sophia.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TB_SM_SQLDEFINE")
public class SQLDefine extends Auditable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sqlId;
	private String sqlName;
	private String defSelectSql;
	private String datasource;
	private Integer type;
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
	private String remark;
	
	/**
	 * 所属功能组
	 */
	private String groupId;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getDefSelectSql() {
		return defSelectSql;
	}
	public void setDefSelectSql(String defSelectSql) {
		this.defSelectSql = defSelectSql;
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
