package com.sophia.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 查询grid
 * @author zkning
 */
public class QueryGridParam {
	
	
	/**
	 * SQLID
	 */
	private String sqlId;
	
	/**
	 * 分页数据
	 */
	@NotBlank
	private Integer pageSize = 20;

	/**
	 * 当前页
	 */
	@NotBlank
	private Integer pageNo = 1;
	
	/**
	 * 查询参数
	 */
	private String params;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}
	
}
