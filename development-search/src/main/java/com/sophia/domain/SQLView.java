package com.sophia.domain;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sophia.constant.SQLViewConstant;

@Entity
@Table(name="TB_SM_VIEW")
public class SQLView extends Auditable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String name;
	private Integer showRowNum = SQLViewConstant.NO;
	/**
	 * 条件过滤
	 */
	private String conditions = "[]";
	
	/**
	 * 按钮列表
	 */
	private String buttons="[]";
	private String sqlId;
	private Integer multiple = SQLViewConstant.NO;
	
	/**
	 * 显示列名
	 */
	@Transient
	private ArrayList<SQLViewField> columnList  = new ArrayList<SQLViewField>();
	
	/**
	 * 树设置
	 */
	@Transient
	private String treeData = "{}";
	
	public ArrayList<SQLViewField> getColumnList() {
		return columnList;
	}
	public void setColumnList(ArrayList<SQLViewField> columnList) {
		this.columnList = columnList;
	}
	public String getTreeData() {
		return treeData;
	}
	public void setTreeData(String treeData) {
		this.treeData = treeData;
	}
	
	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getShowRowNum() {
		return showRowNum;
	}

	public void setShowRowNum(Integer showRowNum) {
		this.showRowNum = showRowNum;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getButtons() {
		return buttons;
	}

	public void setButtons(String buttons) {
		this.buttons = buttons;
	}
}
