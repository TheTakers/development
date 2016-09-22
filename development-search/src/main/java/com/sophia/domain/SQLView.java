package com.sophia.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TB_SM_VIEW")
public class SQLView extends Auditable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String name;
	private String showRowNum;
	private Integer multiple;
	private String conditions;
	private String buttons;
	private String treedata;
	private String mainsql;
	private String sqlexpand;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShowRowNum() {
		return showRowNum;
	}

	public void setShowRowNum(String showRowNum) {
		this.showRowNum = showRowNum;
	}
	
	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getButtons() {
		return buttons;
	}

	public void setButtons(String buttons) {
		this.buttons = buttons;
	}

	public String getTreedata() {
		return treedata;
	}

	public void setTreedata(String treedata) {
		this.treedata = treedata;
	}

	public String getMainsql() {
		return mainsql;
	}

	public void setMainsql(String mainsql) {
		this.mainsql = mainsql;
	}

	public String getSqlexpand() {
		return sqlexpand;
	}

	public void setSqlexpand(String sqlexpand) {
		this.sqlexpand = sqlexpand;
	}
}
