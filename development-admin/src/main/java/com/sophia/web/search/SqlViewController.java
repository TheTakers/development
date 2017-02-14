package com.sophia.web.search;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.sophia.response.Response;
import com.sophia.service.SqlViewService;
import com.sophia.vo.QueryParam;
import com.sophia.vo.SqlViewParam;
import com.sophia.vo.SqlViewQueryParam;

@Controller
@RequestMapping(SqlViewController.module)
public class SqlViewController extends BaseController{
	
	@Autowired SqlViewService sqlViewService;
	
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
	public Object list(@RequestBody @Valid QueryParam queryRequest) {
		Pager<Map<String,Object>> data = sqlViewService.list(queryRequest);
		return Response.SUCCESS(data);
	}
	
	@RequestMapping("/selector")
    public ModelAndView selector(HttpServletRequest request, ModelMap result) {
        return new ModelAndView(module + "/selector", result);
    }
	
	@ResponseBody
	@RequestMapping(value="/treeData",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object tree() {
		return JSONObject.toJSONString(sqlViewService.getRepository().findAll());
	}
	
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object save(@RequestBody @Valid SqlViewParam request) {
		try {
			sqlViewService.save(request);
			return Response.SUCCESS();
		} catch (Exception e) {
			return Response.FAILURE(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object delete(@RequestBody JSONObject param) {
		sqlViewService.getRepository().delete(param.getString("id"));
		return Response.SUCCESS();
	}
 
	@ResponseBody
	@RequestMapping(value="/findById",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object findById(@RequestBody JSONObject param) {
		return Response.SUCCESS(sqlViewService.findById(param.getString("id")));
	}
	
	@ResponseBody
	@RequestMapping(value="/createField",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object generateField(@RequestBody JSONObject json) {
		return Response.SUCCESS(sqlViewService.showFullColumnsBySql(json.getString("sqlId")));
	}
	
	/**
	 * 根据SQLVIEW编号和数据ID获取指定数据
	 * @param sqlId
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findBySqlId/{sqlId}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object findBySqlId(@PathVariable String sqlId,@RequestBody JSONObject row) {
		return Response.SUCCESS(sqlViewService.getDataBySqlId(sqlId,row));
	}
	
	
	/**
	 * 根据SQLVIEW CODE查询sqlview 和 当前查询的行数据
	 * @param code
	 * @param row
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getSqlViewAndSqlDefineRowDataByCode/{code}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object getSqlViewAndSqlDefineRowDataByCode(@PathVariable String code,@RequestBody JSONObject row) {
		return Response.SUCCESS(sqlViewService.getSqlViewAndSqlDefineRowDataByCode(code,row));
	}
	
	/**
	 * 根据编号获取视图
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getSqlViewByCode/{code}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public  Object getSqlViewByCode(@PathVariable String code) {
		Map<String,Object> result = new HashMap<>();
		result.put("sqlView", sqlViewService.getSqlViewByCode(code));
		return Response.SUCCESS(result);
	}
	
	/**
	 * 跳转SQLVIEW页
	 * @param code
	 * @param result
	 * @return
	 */
	@RequestMapping("/index/{code}")
    public ModelAndView uiStandardIndex(@PathVariable String code, ModelMap result) {
		result.addAttribute("code", code);
        return new ModelAndView(module +"/uiStandardIndex", result);
    }
	
	/**
	 * 根据编号获取视图
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/{code}",method={RequestMethod.GET,RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object getByCode(@PathVariable String code) {
		return Response.SUCCESS(sqlViewService.getSqlViewByCode(code));
	}
	
	/**
	 * 保存SQLVIEW记录
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/persistent/{code}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object createView(@PathVariable String code,@RequestBody JSONObject formParam) {
		sqlViewService.persistentByCode(code, formParam);
		return Response.SUCCESS();
	}
	
	
	/**
	 * 修改视图的数据
	 * @param code
	 * @param formParam
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modfity/{code}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object modfityView(@PathVariable String code,@RequestBody JSONObject formParam) {
		sqlViewService.modifyByCode(code, formParam);
		return Response.SUCCESS();
	}
	
	/**
	 * 删除视图的数据
	 * @param code
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete/{code}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public  Object deleteView(@PathVariable String code,@RequestBody JSONObject row) {
		sqlViewService.deleteByCode(code, row);
		return Response.SUCCESS();
	}
	
	/**
	 * 根据SQLVIEW CODE查询列表
	 * @param code
	 * @param queryRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findAll/{code}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public  Object findSqlViewGrid(@PathVariable String code,@RequestBody @Valid SqlViewQueryParam queryRequest) {
		return Response.SUCCESS(sqlViewService.findSqlViewGrid(code, queryRequest));
	}
	
	/**
	 * 根据视图编号获取字段列表
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findFieldListByCode/{code}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object findFieldListByViewCode(@PathVariable String code) {
		return Response.SUCCESS(sqlViewService.findFieldListByViewCode(code));
	}
}
