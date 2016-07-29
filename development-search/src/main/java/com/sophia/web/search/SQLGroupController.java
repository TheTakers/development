package com.sophia.web.search;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.sophia.api.BaseController;
import com.sophia.domain.SQLGroup;
import com.sophia.service.SQLGroupService;
import com.sophia.service.SQLIDService;
import com.sophia.vo.QueryGridRequest;
import com.sophia.vo.SQLGroupRequest;
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
	public Map<String, Object> list(@RequestBody @Valid QueryGridRequest queryGridRequest) {
		try {
			Page<SQLGroup> data = sqlGroupService.getRepository().findAll(new PageRequest(queryGridRequest.getPageNo(), queryGridRequest.getPageSize()));
			return responseOk(Constant.SUCCESS_MESSAGE,data);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@RequestMapping("/selector")
    public ModelAndView selector(HttpServletRequest request, ModelMap result) {
        return new ModelAndView(module + "/selector", result);
    }
	
	@ResponseBody
	@RequestMapping(value="/treeData",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object treeData() {
		try {
			return JSONObject.toJSONString(sqlGroupService.getRepository().findAll());
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> save(@RequestBody @Valid SQLGroupRequest request) {
		try {
			SQLGroup target = new SQLGroup();
			
			if(StringUtils.isBlank(request.getId())){
				target.setId(GUID.nextId());
			}
			BeanUtils.copyProperties(request, target);
			sqlGroupService.save(target);
			return responseOk(Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
}
