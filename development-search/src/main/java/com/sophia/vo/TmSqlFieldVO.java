package com.sophia.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sophia.domain.SQLViewField;

/**
 * 对象功能:自定义SQL字段定义 VO对象
 * 开发公司:向刚制作
 * 开发人员:xianggang
 * 创建时间:2016-01-29 15:33:15
 */
public class TmSqlFieldVO extends SQLViewField{
	private static final long serialVersionUID = 1L;
	// 主键
	private String id;
	// SQLID
	private String sqlId;
	// sqlAlias
	private String sqlAlias;
	// SN
	private String sn;
	// 字段名
	private String name;
	// 实际字段名
	private String fieldName;
	// 字段备注
	private String fieldDesc;
	// 是否可见
	private Integer isShow;
	// 是否搜索
	private Integer isSearch;
	// 控件类型
	private Integer controlType;
	// 数据类型
	private String dataType;
	// DATE_FORMAT
	private String dateFormat;
	// control_content
	private String controlContent;
	
	
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 返回 主键
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	public void setSqlId(String sqlId){
		this.sqlId = sqlId;
	}
	/**
	 * 返回 SQLID
	 * @return
	 */
	public String getSqlId() {
		return this.sqlId;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 返回 字段名
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	public void setFieldName(String fieldName){
		this.fieldName = fieldName;
	}
	/**
	 * 返回 实际字段名
	 * @return
	 */
	public String getFieldName() {
		return this.fieldName;
	}
	public void setFieldDesc(String fieldDesc){
		this.fieldDesc = fieldDesc;
	}
	/**
	 * 返回 字段备注
	 * @return
	 */
	public String getFieldDesc() {
		return this.fieldDesc;
	}
	public void setIsShow(Integer isShow){
		this.isShow = isShow;
	}
	/**
	 * 返回 是否可见
	 * @return
	 */
	public Integer getIsShow() {
		return this.isShow;
	}
	public void setIsSearch(Integer isSearch){
		this.isSearch = isSearch;
	}
	/**
	 * 返回 是否搜索
	 * @return
	 */
	public Integer getIsSearch() {
		return this.isSearch;
	}
	public void setControlType(Integer controlType){
		this.controlType = controlType;
	}
	/**
	 * 返回 控件类型
	 * @return
	 */
	public Integer getControlType() {
		return this.controlType;
	}
	public void setDataType(String dataType){
		this.dataType = dataType;
	}
	/**
	 * 返回 数据类型
	 * @return
	 */
	public String getDataType() {
		return this.dataType;
	}
	public void setDateFormat(String dateFormat){
		this.dateFormat = dateFormat;
	}
	/**
	 * 返回 DATE_FORMAT
	 * @return
	 */
	public String getDateFormat() {
		return this.dateFormat;
	}
	public void setControlContent(String controlContent){
		this.controlContent = controlContent;
	}
	/**
	 * 返回 control_content
	 * @return
	 */
	public String getControlContent() {
		return this.controlContent;
	}

   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof TmSqlFieldVO)) 
		{
			return false;
		}
		TmSqlFieldVO rhs = (TmSqlFieldVO) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.sqlId, rhs.sqlId)
		.append(this.name, rhs.name)
		.append(this.fieldName, rhs.fieldName)
		.append(this.fieldDesc, rhs.fieldDesc)
		.append(this.isShow, rhs.isShow)
		.append(this.isSearch, rhs.isSearch)
		.append(this.controlType, rhs.controlType)
		.append(this.dataType, rhs.dataType)
		.append(this.dateFormat, rhs.dateFormat)
		.append(this.controlContent, rhs.controlContent)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.sqlId) 
		.append(this.name) 
		.append(this.fieldName) 
		.append(this.fieldDesc) 
		.append(this.isShow) 
		.append(this.isSearch) 
		.append(this.controlType) 
		.append(this.dataType) 
		.append(this.dateFormat) 
		.append(this.controlContent) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("sqlId", this.sqlId) 
		.append("name", this.name) 
		.append("fieldName", this.fieldName) 
		.append("fieldDesc", this.fieldDesc) 
		.append("isShow", this.isShow) 
		.append("isSearch", this.isSearch) 
		.append("controlType", this.controlType) 
		.append("dataType", this.dataType) 
		.append("dateFormat", this.dateFormat) 
		.append("controlContent", this.controlContent) 
		.toString();
	}
	public String getSqlAlias() {
		return sqlAlias;
	}
	public void setSqlAlias(String sqlAlias) {
		this.sqlAlias = sqlAlias;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
   
  

}