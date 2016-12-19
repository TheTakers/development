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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sophia.api.BaseController;
import com.sophia.domain.Menu;
import com.sophia.request.MenuRequest;
import com.sophia.request.QueryRequest;
import com.sophia.response.GridResponse;
import com.sophia.response.Response;
import com.sophia.service.MenuService;
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
	public Object list(@RequestBody @Valid QueryRequest queryRequest) {
		try {
			GridResponse<Map<String,Object>> data = menuService.list(queryRequest);
			return Response.SUCCESS(data);
		} catch (Exception e) {
			return Response.FAILURE(e);
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
			return Response.FAILURE(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/menuTreeData",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object menuTreeData(@RequestBody JSONObject param) {
		try {
			return Response.SUCCESS(menuService.getMenuByName(param.getString("name")));
		} catch (Exception e) {
			return Response.FAILURE(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/findById",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object findById(@RequestBody JSONObject row) {
		try {
			return Response.SUCCESS(menuService.findById(row.getString("id")));
		} catch (Exception e) {
			return Response.FAILURE(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object delete(@RequestBody String param) {
		try {
			JSONObject json = JSON.parseObject(param);
			menuService.delete(json.getString("id"));
			return Response.SUCCESS();
		} catch (Exception e) {
			return Response.FAILURE(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object save(@RequestBody @Valid MenuRequest request) {
		try {
			Menu target = new Menu();
			BeanUtils.copyProperties(request, target);
			if(StringUtils.isBlank(request.getId())){
				target.setId(GUID.nextId());
			}
			menuService.save(target);
			return Response.SUCCESS();
		} catch (Exception e) {
			return Response.FAILURE(e);
		}
	}
}
