package com.sophia.vo;

import java.io.Serializable;

/**
 * 查询参数
 * @author zkning
 */
public class SelectParamDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private Object value;
	
	/**
	 *  参数类型
	 */
	private String type;
	
	/**
	 * 参数索引
	 */
	private Integer index;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
