package com.sophia.web.basic;

import java.util.Map;

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
import com.sophia.domain.Menu;
import com.sophia.service.MenuService;
import com.sophia.vo.GridResponse;
import com.sophia.vo.QueryRequest;
import com.sophia.vo.basic.MenuRequest;
import com.sophia.web.constant.Constant;
import com.sophia.web.util.GUID;


@Controller
@RequestMapping(MenuController.module)
public class MenuController extends BaseController{
	
	@Autowired MenuService menuService;
	
	public static final String module = "/basic/menu";
	
	@RequestMapping("/index")
    public ModelAndView index(ModelMap result) {
        return new ModelAndView(module +"/index", result);
    }
	
	@RequestMapping("/main")
    public ModelAndView main(ModelMap result) {
        return new ModelAndView(module +"/main", result);
    }
	
	
	@RequestMapping("/edit")
    public ModelAndView edit(ModelMap result) {
        return new ModelAndView(module + "/edit", result);
    }
	
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> list(@RequestBody @Valid QueryRequest queryRequest) {
		try {
			GridResponse data = menuService.list(queryRequest);
			return responseOk(Constant.SUCCESS_MESSAGE,data);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@RequestMapping("/selector")
    public ModelAndView selector(ModelMap result) {
        return new ModelAndView(module + "/selector", result);
    }
	
	@ResponseBody
	@RequestMapping(value="/treeData",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object treeData() {
		try {
			return JSONObject.toJSONString(menuService.getRepository().findAll());
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/menuTreeData",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> menuTreeData(@RequestBody String param) {
		try {
			JSONObject paramJson = new JSONObject().parseObject(param);
			return responseOk(menuService.getMenuByName(paramJson.getString("name")));
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/findById",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> findById(@RequestBody String param) {
		try {
			JSONObject json = new JSONObject().parseObject(param);
			return responseOk(menuService.findById(json.getString("id")));
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> delete(@RequestBody String param) {
		try {
			JSONObject json = new JSONObject().parseObject(param);
			menuService.delete(json.getString("id"));
			return responseOk(Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> save(@RequestBody @Valid MenuRequest request) {
		try {
			Menu target = new Menu();
			BeanUtils.copyProperties(request, target);
			if(StringUtils.isBlank(request.getId())){
				target.setId(GUID.nextId());
			}
			menuService.save(target);
			return responseOk(Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/breadcrumb",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getPath(@RequestBody String action) {
		try {
			JSONObject json = new JSONObject().parseObject(action);
			return responseOk(menuService.getMenuPath(json.getString("action")));
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
}
