package com.sophia.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sophia.domain.CodeTemplate;
import com.sophia.repository.CodeTemplateRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.CodeTemplateService;
import com.sophia.utils.SimpleUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * 编码模板服务
 * @author zkning
 */
@Service
public class CodeTemplateServiceImpl extends JpaRepositoryImpl<CodeTemplateRepository>  implements CodeTemplateService {
	Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//创建模板
	private List<Map<String,String>> getColumnList(String table){

		//获取表的字段
		SqlRowSet resultSet = namedParameterJdbcTemplate.queryForRowSet("select * from " + table +" where 1 = 2 ",new HashMap<String,String>());
		SqlRowSetMetaData srsmd = resultSet.getMetaData();
		List<Map<String,String>> columnList = new ArrayList<>();
		Map<String,String> columnMap;
		for (int i = 1; i < srsmd.getColumnCount() + 1; i++) {
			String columnName = srsmd.getColumnName(i);
			String dtype = srsmd.getColumnTypeName(i);
			columnMap = new HashMap<>();
			columnMap.put("name", columnName);
			columnMap.put("dtype", dtype);
			columnMap.put("attr", SimpleUtils.underline2Camel(columnName, true));
			columnMap.put("methodName", SimpleUtils.underline2Camel(columnName, false));
			columnList.add(columnMap);
		}
		return columnList;
	}

	private static final String param_table = "tableName";
	private static final String param_filepath = "filepath";
	
	@Override
	public void createCodeTemplate(JSONObject json){
		List<CodeTemplate> codeTemplateList = this.getRepository().findAll();
		Template template;
		Template fileTemplate;
		FileOutputStream fos = null;
		Writer out = null;
		
		//模板参数
		Map<String,String> param = new HashMap<>();
		json.put("columnList", this.getColumnList(json.getString(param_table)));
		try{
			for(CodeTemplate codeTemplate : codeTemplateList){
				json.put("remark", codeTemplate.getRemark());
				
				//模板内容
				template = new Template(codeTemplate.getName(), new StringReader(codeTemplate.getTemplate()));
				StringWriter pathWriter = new StringWriter();
				
				//替换文件名
				fileTemplate = new Template(codeTemplate.getName(), new StringReader(codeTemplate.getFilepath()));
				fileTemplate.process(json, pathWriter);
				String filePath = json.getString(param_filepath ) + "/" + pathWriter.toString();
				
				//检查目录
				String realPath = filePath.substring(0, filePath.lastIndexOf("/")); 
				File fp = new File(realPath);   
				
				// 创建目录   
				if (!fp.exists()) {   
					fp.mkdirs();
				}   
				
				//输出文件
				//Writer out = new OutputStreamWriter(System.out);
				fos = new FileOutputStream(filePath);
				out = new OutputStreamWriter(fos);
				param.put("param", json.toJSONString());
				template.process(param, out);
			}
			
			if(null != fos){
				fos.close();
			}
			if(null != out){
				out.close();
			}
		}catch(Exception ex){
			logger.error("生成代码失败",ex);
		}
	}

	//	//导出模板
	public static void main(String[] args) throws TemplateException, IOException {
		Template template = new Template("code", new StringReader("<#assign text=\"${param}\" /><#assign vars=text?eval />名称:${vars.name}"));
		Map<String,String> vars = new HashMap<>();
		vars.put("name", "xxx");
		Map<String,String> param = new HashMap<>();
		param.put("param", JSON.toJSONString(vars));
		Writer out = new OutputStreamWriter(System.out);
		template.process(param, out);
		out.flush();
		out.close();
	}
}
