package com.sophia.request;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sophia.constant.SQLViewConstant;
import com.sophia.domain.SQLViewField;

public class SQLViewRequest extends CrudRequest{

	@NotBlank
	private String code;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String sqlId;
	
	
	private String remark;
	/**
	 * 显示列名
	 */
	@NotEmpty
	private	List<SQLViewField> columnList  = new ArrayList();
	
	private Integer showRowNum = SQLViewConstant.NO;
	/**
	 * 条件过滤
	 */
	private JSONArray filterList = new JSONArray();
	
	/**
	 * 按钮列表
	 */
	private JSONArray buttonList = new JSONArray();
	
	private Integer multiple = SQLViewConstant.NO;
	/**
	 * 树设置
	 */
	private JSONObject treeData = new JSONObject();
	
	
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

	public List<SQLViewField> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<SQLViewField> columnList) {
		this.columnList = columnList;
	}

	public JSONArray getFilterList() {
		return filterList;
	}

	public void setFilterList(JSONArray filterList) {
		this.filterList = filterList;
	}

	public JSONArray getButtonList() {
		return buttonList;
	}

	public void setButtonList(JSONArray buttonList) {
		this.buttonList = buttonList;
	}

	public JSONObject getTreeData() {
		return treeData;
	}

	public void setTreeData(JSONObject treeData) {
		this.treeData = treeData;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
