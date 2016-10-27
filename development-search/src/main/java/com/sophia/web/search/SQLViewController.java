package com.sophia.web.search;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.sophia.request.QueryRequest;
import com.sophia.request.SQLViewRequest;
import com.sophia.response.GridResponse;
import com.sophia.service.SQLViewService;
import com.sophia.web.constant.Constant;

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
			sqlViewService.save(request);
			return responseOk(Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> delete(@RequestBody JSONObject param) {
		try {
			sqlViewService.getRepository().delete(param.getString("id"));
			return responseOk(Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
 
	@ResponseBody
	@RequestMapping(value="/findById",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> findById(@RequestBody JSONObject param) {
		try {
			return responseOk(sqlViewService.findById(param.getString("id")));
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/createField",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> generateField(@RequestBody JSONObject json) {
		try {
			return responseOk(sqlViewService.showFullColumnsBySql(json.getString("sqlId")));
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	@ResponseBody
	@RequestMapping(value="/findBySqlId/{sqlId}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> findBySqlId(@PathVariable String sqlId,JSONObject param) {
		try {
			return responseOk(sqlViewService.getDataBySqlId(sqlId,null));
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	/**
	 * 跳转SQLVIEW页
	 * @param code
	 * @param result
	 * @return
	 */
	@RequestMapping("/index/{code}")
    public ModelAndView viewIndex(@PathVariable String code, ModelMap result) {
		result.addAttribute("code", code);
        return new ModelAndView(module +"/sqlViewIndex", result);
    }
	
	/**
	 * 根据编号获取视图
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/{code}",method={RequestMethod.GET,RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getByCode(@PathVariable String code) {
		try {
			return responseOk(sqlViewService.getSqlViewByCode(code));
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	/**
	 * 保存SQLVIEW记录
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/persistent/{code}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> createView(@PathVariable String code,@RequestBody JSONObject formParam) {
		try {
			sqlViewService.persistentByCode(code, formParam);
			return responseOk(Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	
	/**
	 * 修改视图的数据
	 * @param code
	 * @param formParam
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modfity/{code}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> modfityView(@PathVariable String code,@RequestBody JSONObject formParam) {
		try {
			sqlViewService.modifyByCode(code, formParam);
			return responseOk(Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
	/**
	 * 删除视图的数据
	 * @param code
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete/{code}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> deleteView(@RequestBody JSONObject formParam) {
		try {
			if(StringUtils.isEmpty("")){
				throw new Exception("id不能为空");
			}
			sqlViewService.deleteByCode(null, null);
			return responseOk(Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
}
