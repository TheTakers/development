package com.sophia.web.search;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.sophia.api.BaseController;
import com.sophia.service.UserService;
import com.sophia.vo.UserParam;
import com.sophia.web.constant.Constant;

/**
 * 查询
 * @author zkning
 */
@Controller
@RequestMapping("/search")
public class SearchController extends BaseController{
	
	
	@Autowired UserService userService;
	
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
	
	@ResponseBody
	@RequestMapping(value="/grid",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
	public Map<String, Object> grid(@RequestBody @Valid UserParam userParam) {
		try {
			Map<String, Object> result = Maps.newHashMap();
			result.put("pageSize", 20);
			result.put("pageNo", 1);
			result.put("pageCount", 2000);
			return responseOk(result);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}

}
