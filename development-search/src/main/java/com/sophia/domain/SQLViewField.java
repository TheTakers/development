package com.sophia.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sophia.constant.ComponentType;
import com.sophia.constant.SQLViewConstant;

@Entity
@Table(name="TB_SM_VIEW_FIELD")
public class SQLViewField extends Auditable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String viewId;
	private String title;
	private String field;
	private String expand;
	private Integer isDisplay = SQLViewConstant.NO;
	private Integer isSort = SQLViewConstant.NO;
	private Integer isUpdate = SQLViewConstant.YES;
	private Integer isSearch = SQLViewConstant.NO;
	private String dataType;
	private String componentType = ComponentType.TEXT.getValue();
	private Integer idx;
	private String length;
	private String rule = "[]";
	
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public Integer getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
	}
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	public Integer getIsSearch() {
		return isSearch;
	}
	public void setIsSearch(Integer isSearch) {
		this.isSearch = isSearch;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
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
	 
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	public String getExpand() {
		return expand;
	}
	public void setExpand(String expand) {
		this.expand = expand;
	}
	public Integer getIsSort() {
		return isSort;
	}
	public void setIsSort(Integer isSort) {
		this.isSort = isSort;
	}
}
