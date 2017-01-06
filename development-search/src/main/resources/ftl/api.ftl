<#assign text="${param}" />
<#assign vars=text?eval />
package com.sophia.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${vars.comment}API
 * @author ${vars.author}
 */
@RestController
@RequestMapping(value = "/api/${vars.beanName}")
public class ${vars.beanName}RestController extends BaseController {

}
