package com.sophia.web.search;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.sophia.domain.SQLView;
import com.sophia.service.SQLViewService;
import com.sophia.vo.GridResponse;
import com.sophia.vo.QueryRequest;
import com.sophia.vo.search.SQLViewRequest;
import com.sophia.web.constant.Constant;
import com.sophia.web.util.GUID;

@Controller
@RequestMapping(SQLViewController.module)
public class SQLViewController extends BaseController{
	
	@Autowired SQLViewService sqlViewService;
	
	public static final String module = "/search/sqlview";
	
	@RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, ModelMap result) {
        return new ModelAndView(module +"/sqlView", result);
    }
	
	@RequestMapping("/edit")
    public ModelAndView edit(HttpServletRequest request, ModelMap result) {
        return new ModelAndView(module + "/sqlViewEdit", result);
    }
	
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> list(@RequestBody @Valid QueryRequest queryRequest) {
		try {
			GridResponse<Map<String,Object>> data = sqlViewService.list(queryRequest);
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
			return JSONObject.toJSONString(sqlViewService.getRepository().findAll());
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> save(@RequestBody @Valid SQLViewRequest request) {
		try {
			SQLView target = new SQLView();
			
			BeanUtils.copyProperties(request, target);
			if(StringUtils.isBlank(request.getId())){
				target.setId(GUID.nextId());
			}
			sqlViewService.save(target);
			return responseOk(Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> delete(@RequestBody String param) {
		try {
			JSONObject json = new JSONObject().parseObject(param);
			sqlViewService.getRepository().delete(json.getString("id"));
			return responseOk(Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/findById",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> findById(@RequestBody String param) {
		try {
			JSONObject json = new JSONObject().parseObject(param);
			return responseOk(sqlViewService.findById(json.getString("id")));
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
}
