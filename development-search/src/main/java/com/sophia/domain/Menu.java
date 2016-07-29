package com.sophia.domain;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 菜单
 * @author zkning
 */

@Entity
@Table(name="TB_BASIC_MENU")
public class Menu extends Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String ico;
	
	private String link;
	
	private String pid;
	
	private String remark;
	
	private String path;
	
	@Transient
	private ArrayList<Menu> child = new ArrayList<>();
	
	public ArrayList<Menu> getChild() {
		return child;
	}

	public void setChild(ArrayList<Menu> child) {
		this.child = child;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
