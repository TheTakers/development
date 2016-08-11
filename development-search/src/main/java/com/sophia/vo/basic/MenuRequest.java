package com.sophia.vo.basic;

import org.hibernate.validator.constraints.NotBlank;

import com.sophia.vo.CrudRequest;

public class MenuRequest extends CrudRequest{

	@NotBlank
	private String name;
	private String remark;
	private String pid;
	private String ico;
	private String link;
	private String id;
	private Long idx;
	
	public Long getIdx() {
		return idx;
	}
	public void setIdx(Long index) {
		this.idx = index;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String icon) {
		this.ico = icon;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String url) {
		this.link = url;
	}
}
