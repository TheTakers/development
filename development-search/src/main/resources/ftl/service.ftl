<#assign text="${param}" />
<#assign vars=text?eval />
package com.${packageName}.service;

import com.sophia.repository.JpaRepository;
import com.sophia.repository.${vars.beanName}Repository;

/**
 * ${vars.comment}服务
 * @author ${vars.author}
 */   
public interface ${vars.beanName}Service  extends JpaRepository<${vars.beanName}Repository>{
	
}