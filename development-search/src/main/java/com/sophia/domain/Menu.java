package com.sophia.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

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
	
	private String icon;
	
	private String url;
	
	private String pid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
