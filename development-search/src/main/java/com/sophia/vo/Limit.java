package com.sophia.vo;

public class Limit {
	
	private Integer pageSize;
	private Integer pageNo;
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
	public Limit(Integer pageSize,Integer pageNo){
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}
	
	public static Limit create(Integer pageSize,Integer pageNo){
		return new Limit(pageSize, pageNo);
	}
}
