package com.sophia.domain;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * SQLGROUP
 * @author zkning
 */
@Entity
@Table(name="TB_SM_SQLGROUP")
public class SQLGroup extends Auditable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String groupCode;
	private String groupName;
	private String groupId;
	private String groupPath;
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
	 
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupPath() {
		return groupPath;
	}
	public void setGroupPath(String groupPath) {
		this.groupPath = groupPath;
	}
	public String getGroupDesc() {
		return groupDesc;
	}
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
}
