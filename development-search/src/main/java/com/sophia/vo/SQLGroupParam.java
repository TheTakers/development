package com.sophia.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * SQL分组参数
 * @author zkning
 *
 */
public class SQLGroupParam {
	
	@NotBlank
	private String groupCode;
	
	@NotBlank
	private String groupName;
	private String groupDesc;
	
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupDesc() {
		return groupDesc;
	}
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}


}
