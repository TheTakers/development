<#assign text="${param}" />
<#assign vars=text?eval />
package com.sophia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sophia.domain.${vars.beanName};
/**
 * ${vars.comment} Repository
 * @author ${vars.author}
 */
@Repository
public interface ${vars.beanName}Repository extends JpaRepository<${vars.beanName}, String>{

}
