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
	private Integer option;
	private String conditions;
	private String buttons;
	private String treedata;
	private String sqlId;

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

	public Integer getOption() {
		return option;
	}

	public void setOption(Integer option) {
		this.option = option;
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

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}
}
