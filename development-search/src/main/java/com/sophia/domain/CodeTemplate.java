package com.sophia.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * tb_basic_code_template
 * @author zkning
 */
@Entity
@Table(name="TB_BASIC_CODE_TEMPLATE")
public class CodeTemplate extends Auditable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String template;
	private String remark;
	private String filepath;
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
