package com.sophia.vo;

import java.io.Serializable;

/**
 * 树VO
 * @author zkning
 *
 */
public class TreeVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url; 
	private String idKey;
	private String pIdKey;
	private String rootPId;
	private Integer isShow;
	
	/**
	 * 关联字段
	 */
	private String  relationField;
	/**
	 * 节点操作类型 
	 * ALLNODE 全部子节点
	 * SUBNODE 直接子节点
	 * CHECKEDNODE 当前节点
	 */
	private String nodeOpts;
	private String width;
	private String sqlId;
	
	public String getRelationField() {
		return relationField;
	}
	public void setRelationField(String relationField) {
		this.relationField = relationField;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIdKey() {
		return idKey;
	}
	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}
	public String getpIdKey() {
		return pIdKey;
	}
	public void setpIdKey(String pIdKey) {
		this.pIdKey = pIdKey;
	}
	public String getRootPId() {
		return rootPId;
	}
	public void setRootPId(String rootPId) {
		this.rootPId = rootPId;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public String getNodeOpts() {
		return nodeOpts;
	}
	public void setNodeOpts(String nodeOpts) {
		this.nodeOpts = nodeOpts;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getSqlId() {
		return sqlId;
	}
	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}
}
