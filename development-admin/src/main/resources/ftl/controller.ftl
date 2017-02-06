<#assign text="${param}" />
<#assign vars=text?eval />
package com.${vars.packageName}.web.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.${vars.packageName}.api.BaseController;

/**
 * ${vars.comment}控制器
 * @author ${vars.author}
 */
@Controller
@RequestMapping("/${vars.beanName}")
public class ${vars.beanName}Controller extends BaseController{

}
