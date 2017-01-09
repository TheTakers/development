<#assign text="${param}" />
<#assign vars=text?eval />
package com.${packageName}.service.impl;

import org.springframework.stereotype.Service;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.repository.${vars.beanName}Repository;
import com.sophia.service.${vars.beanName}Service;

/**
 * ${vars.comment}服务实现类
 * @author ${vars.author}
 */  
@Service
public class ${vars.beanName}ServiceImpl extends JpaRepositoryImpl<${vars.beanName}Repository> implements ${vars.beanName}Service{
	
}
