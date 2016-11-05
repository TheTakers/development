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
	 * 是否排序
	 */
	private Integer isSort;
	/**
	 * 值
	 */
	private String value;

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

	public Integer getIsSort() {
		return isSort;
	}

	public void setIsSort(Integer isSort) {
		this.isSort = isSort;
	}
}
