package com.sophia.vo;

import java.io.Serializable;

/**
 * 条件Vo
 * @author zkning
 *
 */
public class ConditionVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 字段
	 */
	private String field;
	
	/**
	 * 数据类型
	 */
	private String dataType;
	
	/**
	 * 组件类型 
	 */
	private String componentType;
	
	/**
	 * 表达式
	 */
	private String expr;
	
	/**
	 * 排序类型 (ASC,DESC)
	 */
	private String idx;
	
	/**
	 * 排序规则
	 */
	private String sort;
	/**
	 * 值
	 */
	private String value;
	
	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}
}
