package com.sophia.web.search;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.sophia.api.BaseController;
import com.sophia.domain.SQLDefine;
import com.sophia.request.QueryRequest;
import com.sophia.request.SQLDefineRequest;
import com.sophia.response.GridResponse;
import com.sophia.response.Response;
import com.sophia.service.SQLDefineService;
import com.sophia.web.util.GUID;

/**
 * 查询
 * @author zkning
 */
@Controller
@RequestMapping(SQLDefineController.module)
public class SQLDefineController extends BaseController{
	
	
	@Autowired SQLDefineService sqlDefineService;
	
	
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
	public Response<Object> list(@RequestBody @Valid QueryRequest queryRequest) {
		try {
			GridResponse data = sqlDefineService.list(queryRequest);
			return Response.SUCCESS(data);
		} catch (Exception e) {
			return Response.FAILURE(e);
		}
	}
	
	/**
	 * 根据SQLID获取数据
	 * @param sqlId
	 * @param queryRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list/{sqlId}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<Object> list(@PathVariable String sqlId,@RequestBody @Valid QueryRequest queryRequest) {
		try {
			GridResponse data = sqlDefineService.list(sqlId,queryRequest);
			return Response.SUCCESS(data);
		} catch (Exception e) {
			return Response.FAILURE(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/listAll/{sqlId}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object getListAllBySqlId(@PathVariable String sqlId) {
		try {
			return sqlDefineService.findAllBySqlId(sqlId);
		} catch (Exception e) {
			return Response.FAILURE(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<Object> save(@RequestBody @Valid SQLDefineRequest request) {
		try {
			SQLDefine target = new SQLDefine();
			BeanUtils.copyProperties(request, target);
			if(StringUtils.isBlank(request.getId())){
				target.setId(GUID.nextId());
			}
			sqlDefineService.save(target);
			return Response.SUCCESS();
		} catch (Exception e) {
			return Response.FAILURE(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/findById",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<Object> findById(@RequestBody JSONObject row) {
		try {
			return Response.SUCCESS(sqlDefineService.findById(row.getString("id")));
		} catch (Exception e) {
			return Response.FAILURE(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<Object> delete(@RequestBody JSONObject param) {
		try {
			sqlDefineService.getRepository().delete(param.getString("id"));
			return Response.SUCCESS();
		} catch (Exception e) {
			return Response.FAILURE(e);
		}
	}
	
	/**
	 * 获取所有table 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findAllTable",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<Object> findAllTable() {
		try {
			List<Map<String,Object>> list = sqlDefineService.findAllTable();
			return Response.SUCCESS(list);
		} catch (Exception e) {
			return Response.FAILURE(e);
		}
	}
}
