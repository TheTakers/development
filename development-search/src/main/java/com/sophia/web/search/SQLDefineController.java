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
import com.sophia.domain.SQLDefine;
import com.sophia.service.SQLDefineService;
import com.sophia.service.SQLIDService;
import com.sophia.vo.QueryRequest;
import com.sophia.vo.search.sqldefine.SQLDefineRequest;
import com.sophia.web.constant.Constant;
import com.sophia.web.util.GUID;

/**
 * 查询
 * @author zkning
 */
@Controller
@RequestMapping(SQLDefineController.module)
public class SQLDefineController extends BaseController{
	
	
	@Autowired SQLDefineService sqlDefineService;
	@Autowired SQLIDService sqlIDService;
	
	
	public static final String module = "search/sqldefine";
	
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
	public Map<String, Object> list(@RequestBody @Valid QueryRequest queryGridRequest) {
		try {
			Page<SQLDefine> data = sqlDefineService.getRepository().findAll(new PageRequest(queryGridRequest.getPageNo(), queryGridRequest.getPageSize()));
			return responseOk(Constant.SUCCESS_MESSAGE,data);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> save(@RequestBody @Valid SQLDefineRequest request) {
		try {
			SQLDefine target = new SQLDefine();
			
			BeanUtils.copyProperties(request, target);
			if(StringUtils.isBlank(request.getId())){
				target.setId(GUID.nextId());
			}
			sqlDefineService.save(target);
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
			return responseOk(sqlDefineService.getRepository().findOne(json.getString("id")));
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> delete(@RequestBody String param) {
		try {
			JSONObject json = new JSONObject().parseObject(param);
			sqlDefineService.getRepository().delete(json.getString("id"));
			return responseOk(Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
}
