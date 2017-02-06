package com.sophia.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.sophia.Application;
import com.sophia.service.CodeTemplateService;
import com.sophia.service.MenuService;
import com.sophia.service.SQLIDService;
import com.sophia.service.UserService;

/**
 * @author zkning
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@SpringApplicationConfiguration(classes = Application.class)
public class JUnitTest {  
	
	@Autowired UserService userService;
	@Autowired SQLIDService sqlidService;
	@Autowired MenuService menuService;
	@Autowired CodeTemplateService codeTemplateService;
 
	@Test
	public void createCode(){
		JSONObject json = new JSONObject();
		
		//类名
		json.put("beanName", "Menu");
		
		//表
		json.put("tableName", "tb_basic_menu");
		
		//作者
		json.put("author", "zkning");
		
		//注释
		json.put("comment", "菜单管理");
		
		//模块名
		json.put("module", "basic");
		
		//文件路径
		json.put("filepath", "D:/createCode/src/main/java/com/sophia");
		codeTemplateService.createCodeTemplate(json);
	}
}
