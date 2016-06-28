package com.sophia.web.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sophia.api.BaseController;
import com.sophia.service.UserService;

/**
 * 查询
 * @author zkning
 */
@Controller
@RequestMapping(SQLDefineController.module)
public class SQLDefineController extends BaseController{
	
	
	@Autowired UserService userService;
	
	public static final String module = "search/sqldefine";
	
	 
}
