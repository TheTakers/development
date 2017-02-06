<#assign text="${param}" />
<#assign vars=text?eval />
package com.${vars.packageName}.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.${vars.packageName}.api.BaseController;

/**
 * ${vars.comment} API
 * @author ${vars.author}
 */
@RestController
@RequestMapping(value = "/api/${vars.beanName}")
public class ${vars.beanName}RestController extends BaseController {

}
