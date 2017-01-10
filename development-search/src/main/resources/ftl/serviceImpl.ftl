<#assign text="${param}" />
<#assign vars=text?eval />
package com.${vars.packageName}.service.impl;

import org.springframework.stereotype.Service;
import com.${vars.packageName}.repository.impl.JpaRepositoryImpl;
import com.${vars.packageName}.repository.${vars.beanName}Repository;
import com.${vars.packageName}.service.${vars.beanName}Service;

/**
 * ${vars.comment}服务实现类
 * @author ${vars.author}
 */  
@Service
public class ${vars.beanName}ServiceImpl extends JpaRepositoryImpl<${vars.beanName}Repository> implements ${vars.beanName}Service{
	
}
