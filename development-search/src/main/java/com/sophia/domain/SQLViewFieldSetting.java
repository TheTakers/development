package com.sophia.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_SM_VIEW_FIELDSET")
public class SQLViewFieldSetting extends Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String viewId;
	private String title;
	private String field;
	private String expand;
	private Integer sort;
	private Integer required;
	private String componentType;

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getRequired() {
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
}
