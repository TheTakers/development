package com.sophia.web.basic;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sophia.api.BaseController;
import com.sophia.web.constant.Constant;
import com.sophia.web.util.GUID;

/**
 * 
 * @author zkning
 */
@Controller
@RequestMapping(CommonController.module)
public class CommonController extends BaseController{
	
	public static final String module = "/basic/func";
	
	@ResponseBody
	@RequestMapping(value="/code",method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public Map<String, Object> code() {
		try {
			return responseOk(Constant.SUCCESS_MESSAGE,GUID.createCode());
		} catch (Exception e) {
			return responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
	
}
