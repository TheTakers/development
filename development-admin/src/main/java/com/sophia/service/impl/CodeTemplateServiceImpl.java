package com.sophia.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
import com.sophia.dto.DataTypeDto;
import com.sophia.repository.CodeTemplateRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.CodeTemplateService;
import com.sophia.utils.SimpleUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
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
		for (int i = 1; i < srsmd.getColumnCount() + 1; i++) {
			String columnName = srsmd.getColumnName(i);
			Map<String,String> columnMap = new HashMap<>();
			DataTypeDto vo = SimpleUtils.mysqlTypeConvertJavaType(srsmd.getColumnType(i));
			columnMap.put("name",columnName);
			columnMap.put("dtype",vo.getType());
			columnMap.put("attr",SimpleUtils.underline2Camel(columnName, true));
			columnMap.put("package",vo.getTypePackage());
			columnMap.put("methodName",SimpleUtils.underline2Camel(columnName, false));
			columnList.add(columnMap);
		}
		return columnList;
	}
	private static final String param_table = "tableName";
	private static final String param_filepath = "filepath";
	
	//实体属性集合
	private static final String param_columnList = "columnList";
	
	//引入包名
	private static final String param_packageName = "packageName";
	
	@Override
	public void createCodeTemplate(JSONObject tplparam){
		List<CodeTemplate> codeTemplateList = this.getRepository().findAll();
		Template template;
		Template fileTemplate;
		FileOutputStream fos = null;
		Writer out = null;
		
		//模板参数
		Map<String,String> param = new HashMap<>();
		tplparam.put(param_columnList, this.getColumnList(tplparam.getString(param_table)));
		try{
			for(CodeTemplate codeTemplate : codeTemplateList){
				
				//配置模板
				Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
				StringTemplateLoader stl = new StringTemplateLoader();
				stl.putTemplate("code_tpl", codeTemplate.getTemplate());
				stl.putTemplate("file_tpl", codeTemplate.getFilepath());
				cfg.setTemplateLoader(stl);
				
				//模板内容
				StringWriter pathWriter = new StringWriter();
				
				//替换文件名
				fileTemplate = cfg.getTemplate("file_tpl");
				fileTemplate.process(tplparam, pathWriter);
				String paramFilepath = tplparam.getString(param_filepath );
				
				//获取包名
				String packageName = paramFilepath.substring(paramFilepath.lastIndexOf("/") + 1);
				tplparam.put(param_packageName, packageName);
				
				//完整文件名
				String filePath = paramFilepath + "/" + pathWriter.toString();
				
				//完整文件路径
				String realPath = filePath.substring(0, filePath.lastIndexOf("/")); 
				File fp = new File(realPath);   
				
				// 创建目录   
				if (!fp.exists()) {   
					fp.mkdirs();
				}   
				
				//输出文件
				fos = new FileOutputStream(filePath);
				out = new OutputStreamWriter(fos);
				param.put("param", tplparam.toJSONString());
				template = cfg.getTemplate("code_tpl");
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
		
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		StringTemplateLoader stl = new StringTemplateLoader();
//		Template template = new Template("code", new StringReader("<#assign text=\"${param}\" /><#assign vars=text?eval />名称:${vars.name}"));
		stl.putTemplate("tpl", "<#assign text=\"${param}\" /><#assign vars=text?eval />名称:${vars.name}");
		cfg.setTemplateLoader(stl);
		Template tpl = cfg.getTemplate("tpl");
		Map<String,String> vars = new HashMap<>();
		vars.put("name", "xxx");
		Map<String,String> param = new HashMap<>();
		param.put("param", JSON.toJSONString(vars));
		Writer out = new OutputStreamWriter(System.out);
		tpl.process(param, out);
		out.flush();
		out.close();
	}
}
