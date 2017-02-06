package com.sophia.service;

import com.alibaba.fastjson.JSONObject;
import com.sophia.repository.CodeTemplateRepository;
import com.sophia.repository.JpaRepository;

/**
 * 模板服务
 * @author zkning
 */
public interface CodeTemplateService extends JpaRepository<CodeTemplateRepository>{
	
	/**
	 * 根据表获取列集合
	 * @param table
	 * @return
	 * @throws Exception 
	 */
	public void createCodeTemplate(JSONObject json);
}
