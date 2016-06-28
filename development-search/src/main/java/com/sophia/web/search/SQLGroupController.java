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

import com.sophia.api.BaseController;
import com.sophia.domain.SQLGroup;
import com.sophia.service.SQLGroupService;
import com.sophia.vo.SQLGroupParam;
import com.sophia.vo.UserParam;
import com.sophia.web.constant.Constant;
import com.sophia.web.util.GUID;


@Controller
@RequestMapping(SQLGroupController.module)
public class SQLGroupController extends BaseController{
	
	@Autowired SQLGroupService sqlGroupService;
	
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
		 
			return responseOk(Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> save(@RequestBody @Valid SQLGroupParam sqlGroupParam) {
		try {
			SQLGroup sqlGroup = new SQLGroup();
			sqlGroup.setId(GUID.nextId());
			sqlGroup.setGroupCode(sqlGroupParam.getGroupCode());
			sqlGroup.setGroupName(sqlGroup.getGroupName());
			sqlGroup.setGroupId(sqlGroupParam.getGroupId());
			sqlGroupService.insert(sqlGroup);
			return responseOk(Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
}
