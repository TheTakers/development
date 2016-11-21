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
	
	/**
	 * 是否显示列表
	 */
	private Integer isDisplay = SQLViewConstant.YES;
	
	/**
	 * 排序规则  asc ,desc
	 */
	private String sort;
	
	/**
	 * 操作类型
	 * 0 不显示,1,可操作,2 disable
	 */
	private Integer modiftyType = SQLViewConstant.MODIFTY_HIDE;
	
	/**
	 * 是否察看详情
	 */
	private Integer isView = SQLViewConstant.YES;
	/**
	 * 是否添加
	 */
	private Integer isInsert = SQLViewConstant.NO;
	private String dataType;
	private String componentType = ComponentType.TEXT.getValue();
	private Integer idx;
	private String length;
	private String rule = "[]";
	
	public Integer getIsInsert() {
		return isInsert;
	}
	public void setIsInsert(Integer isInsert) {
		this.isInsert = isInsert;
	}
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
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	 
	public Integer getModiftyType() {
		return modiftyType;
	}
	public void setModiftyType(Integer modiftyType) {
		this.modiftyType = modiftyType;
	}
	public Integer getIsView() {
		return isView;
	}
	public void setIsView(Integer isView) {
		this.isView = isView;
	}
}
