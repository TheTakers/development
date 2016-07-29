package com.sophia.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * SQL分组参数
 * @author zkning
 *
 */
public class SQLGroupRequest {
	
	@NotBlank
	private String code;
	
	@NotBlank
	private String name;
	private String remark;
	private String parentId;
	
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
