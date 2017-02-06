package com.sophia.web.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sophia.api.BaseController;
import com.sophia.response.Response;
import com.sophia.service.CodeTemplateService;
import com.sophia.web.util.GUID;

/**
 * 
 * @author zkning
 */
@Controller
@RequestMapping(CommonController.module)
public class CodeTemplateController extends BaseController{
	public static final String module = "/basic/codeTemplate";
	@Autowired CodeTemplateService codeTemplateService;
	
	@ResponseBody
	@RequestMapping(value="/create",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object createCode(@RequestBody JSONObject param) {
		codeTemplateService.createCodeTemplate(param);
		return Response.SUCCESS(GUID.createCode(null));
	}
	
}
