package com.sophia.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

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
	private Integer diaplay;
	private String expand;
	private Integer isSort;
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
	public Integer getDiaplay() {
		return diaplay;
	}
	public void setDiaplay(Integer diaplay) {
		this.diaplay = diaplay;
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