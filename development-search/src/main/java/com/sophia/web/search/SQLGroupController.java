package com.sophia.web.search;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import com.sophia.service.SQLIDService;
import com.sophia.vo.QueryGridParam;
import com.sophia.vo.SQLGroupParam;
import com.sophia.web.constant.Constant;
import com.sophia.web.util.GUID;


@Controller
@RequestMapping(SQLGroupController.module)
public class SQLGroupController extends BaseController{
	
	@Autowired SQLGroupService sqlGroupService;
	@Autowired SQLIDService sqlidService;
	
	public static final String module = "/search/sqlgroup";
	
	@RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, ModelMap result) {
        return new ModelAndView(module +"/index", result);
    }
	
	@RequestMapping("/edit")
    public ModelAndView edit(HttpServletRequest request, ModelMap result) {
        return new ModelAndView(module + "/edit", result);
    }
	
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> list(@RequestBody @Valid QueryGridParam queryGridParam) {
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
			sqlGroup.setCode(sqlGroupParam.getCode());
			sqlGroup.setName(sqlGroupParam.getName());
			sqlGroup.setParentId(sqlGroupParam.getParentId());
			sqlGroup.setRemark(sqlGroupParam.getDesc());
			sqlGroupService.insert(sqlGroup);
			return responseOk(Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
}
