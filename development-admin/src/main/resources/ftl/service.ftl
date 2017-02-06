<#assign text="${param}" />
<#assign vars=text?eval />
package com.${vars.packageName}.service;

import com.${vars.packageName}.repository.JpaRepository;
import com.${vars.packageName}.repository.${vars.beanName}Repository;

/**
 * ${vars.comment}服务
 * @author ${vars.author}
 */   
public interface ${vars.beanName}Service  extends JpaRepository<${vars.beanName}Repository>{
	
}