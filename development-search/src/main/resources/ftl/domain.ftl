<#assign text="${param}" />
<#assign vars=text?eval />
package com.sophia.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
	@Column(name="${column.name}")
	private ${column.dtype} ${column.attr};
	
	</#list>

	<#list vars.columnList as column>
	
	/**
	 * ${column.name}
	 */
	public ${column.dtype} get${column.methodName}() {
		return ${column.attr};
	}
	public void set${column.methodName}(${column.dtype} ${column.attr}) {
		this.${column.attr} = ${column.attr};
	}
	</#list>
	
}
