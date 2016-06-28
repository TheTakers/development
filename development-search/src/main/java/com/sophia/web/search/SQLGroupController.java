package com.sophia.web.search;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sophia.api.BaseController;
import com.sophia.domain.User;
import com.sophia.service.UserService;
import com.sophia.vo.UserParam;
import com.sophia.web.constant.Constant;


@Controller
@RequestMapping(SQLGroupController.module)
public class SQLGroupController extends BaseController{
	
	@Autowired UserService userService;
	
	public static final String module = "/search/sqlgroup";
	
	@RequestMapping("/index")
    @Secured("ROLE_ADMIN")
    public ModelAndView sqlGroup(HttpServletRequest request, ModelMap result) {
        return new ModelAndView(module +"/index", result);
    }
	
	@RequestMapping("/edit")
    @Secured("ROLE_ADMIN")
    public ModelAndView sqlGroupEdit(HttpServletRequest request, ModelMap result) {
        return new ModelAndView(module + "/edit", result);
    }
	
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
	public Map<String, Object> grid(@RequestBody @Valid UserParam userParam) {
		try {
			Page<User> page = userService.grid(userParam);
			return responseOk(page);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
}
