package com.sophia.vo;

import java.io.Serializable;
import java.util.List;

public class Grid<T> implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer pageSize;
	private Integer pageNo;
	private Integer count;
	private List<T>	data;
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
}
