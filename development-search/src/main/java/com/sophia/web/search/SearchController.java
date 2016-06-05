package com.sophia.web.search;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 查询
 * @author zkning
 */
@Controller
@RequestMapping("/search")
public class SearchController {

	@RequestMapping("/sqlDefine")
    @Secured("ROLE_ADMIN")
    public ModelAndView sqlDefine(HttpServletRequest request, ModelMap result) {
		
		
        return new ModelAndView("search/sqlDefine", result);
    }
	
	@RequestMapping("/sqlGroup")
    @Secured("ROLE_ADMIN")
    public ModelAndView sqlGroup(HttpServletRequest request, ModelMap result) {
		
		
        return new ModelAndView("search/sqlGroup", result);
    }

}
