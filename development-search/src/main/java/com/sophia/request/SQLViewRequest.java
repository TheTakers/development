package com.sophia.request;

import org.hibernate.validator.constraints.NotBlank;

public class SQLViewRequest extends CrudRequest{
	
	@NotBlank
	private String code;
	
	@NotBlank
	private String name;
	
	private String showRowNum;
	
	/**
	 * 1 单选,2多选
	 */
	private Integer multiple;
	private String conditions;
	private String buttons;
	private String treedata;
	
	@NotBlank
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

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}
}
