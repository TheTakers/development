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
import com.sophia.domain.Pager;
import com.sophia.domain.SqlDefine;
import com.sophia.response.Response;
import com.sophia.service.SqlDefineService;
import com.sophia.vo.QueryParam;
import com.sophia.vo.SqlDefineParam;
import com.sophia.web.util.GUID;

/**
 * 查询
 * @author zkning
 */
@Controller
@RequestMapping(SqlDefineController.module)
public class SqlDefineController extends BaseController{
	@Autowired SqlDefineService sqlDefineService;
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
	public Object list(@RequestBody @Valid QueryParam queryRequest) {
		Pager<Map<String,Object>> data = sqlDefineService.list(queryRequest);
		return Response.SUCCESS(data);
	}
	
	/**
	 * 根据SQLID获取数据
	 * @param sqlId
	 * @param queryRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list/{sqlId}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object list(@PathVariable String sqlId,@RequestBody @Valid QueryParam queryRequest) {
		Pager<Map<String,Object>> data = sqlDefineService.list(sqlId,queryRequest);
		return Response.SUCCESS(data);
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
	public Object save(@RequestBody @Valid SqlDefineParam request) {
		SqlDefine target = new SqlDefine();
		BeanUtils.copyProperties(request, target);
		if(StringUtils.isBlank(request.getId())){
			target.setId(GUID.nextId());
		}
		sqlDefineService.save(target);
		return Response.SUCCESS();
	}
	
	@ResponseBody
	@RequestMapping(value="/findById",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object findById(@RequestBody JSONObject row) {
		try {
			return Response.SUCCESS(sqlDefineService.findById(row.getString("id")));
		} catch (Exception e) {
			return Response.FAILURE(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object delete(@RequestBody JSONObject param) {
		sqlDefineService.getRepository().delete(param.getString("id"));
		return Response.SUCCESS();
	}
	
	/**
	 * 获取所有table 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findAllTable",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object findAllTable() {
		List<Map<String,Object>> list = sqlDefineService.findAllTable();
		return Response.SUCCESS(list);
	}
}
