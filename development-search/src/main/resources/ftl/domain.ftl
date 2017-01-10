<#assign text="${param}" />
<#assign vars=text?eval />
package com.${vars.packageName}.domain;

<#list vars.columnList as column>
	<#if column.attr != "id" &&
		 column.attr != "version"&&
		 column.attr != "createUser"&&
		 column.attr != "createTime"&&
		 column.attr != "lastUpdateUser"&&
		 column.attr != "lastUpdateTime" &&
		 (column.package)??
		 > 
${column.package}
	</#if> 
</#list>
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.${vars.packageName}.domain.Auditable;

/**
 * ${vars.comment}实体类
 * @author ${vars.author}
 */  
@Entity
@Table(name="${vars.tableName}")
public class ${vars.beanName} extends Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	<#list vars.columnList as column>
	<#if column.attr != "id" &&
		 column.attr != "version"&&
		 column.attr != "createUser"&&
		 column.attr != "createTime"&&
		 column.attr != "lastUpdateUser"&&
		 column.attr != "lastUpdateTime"
		 > 
	@Column(name="${column.name}")
	private ${column.dtype} ${column.attr};
	
	</#if> 
	</#list>

	<#list vars.columnList as column>
	<#if column.attr != "id" &&
		 column.attr != "version"&&
		 column.attr != "createUser"&&
		 column.attr != "createTime"&&
		 column.attr != "lastUpdateUser"&&
		 column.attr != "lastUpdateTime"
		 > 
	/**
	 * ${column.name}
	 */
	public ${column.dtype} get${column.methodName}() {
		return ${column.attr};
	}
	public void set${column.methodName}(${column.dtype} ${column.attr}) {
		this.${column.attr} = ${column.attr};
	}
	</#if> 
	</#list>
	
}
