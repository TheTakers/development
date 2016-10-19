package com.sophia.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.sophia.constant.ComponentType;
import com.sophia.constant.SQLViewConstant;
import com.sophia.domain.SQLDefine;
import com.sophia.domain.SQLView;
import com.sophia.domain.SQLViewField;
import com.sophia.repository.SQLViewRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.request.QueryRequest;
import com.sophia.response.GridResponse;
import com.sophia.service.JdbcTemplateService;
import com.sophia.service.SQLDefineService;
import com.sophia.service.SQLViewService;
import com.sophia.utils.SQLFilter;
import com.sophia.web.util.GUID;

@Service
public class SQLViewServiceImpl extends JpaRepositoryImpl<SQLViewRepository> implements SQLViewService  {

	Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	@Autowired JdbcTemplateService jdbcTemplateService;
	@Autowired SQLDefineService sqlDefineService;

	private String sql="select t.* from TB_SM_VIEW t ";
	public String save(SQLView sqlView){

		//生成GROUP PATH
		return getRepository().save(sqlView).getId();
	}
	@Override
	public Map<String,Object> findById(String id) {
		SQLFilter sqlFilter = SQLFilter.getInstance();
		sqlFilter.setMainSql(sql);
		sqlFilter.EQ("id", id);
		return namedParameterJdbcTemplate.queryForMap(sqlFilter.getSql(), sqlFilter.getParams());
	}
	public GridResponse<Map<String,Object>> list(QueryRequest queryRequest){
		SQLFilter sqlFilter = SQLFilter.getInstance();
		sqlFilter.addCondition(queryRequest.getCondition());
		sqlFilter.setMainSql(sql);
		if(queryRequest.getTreeNode()!=null){
			sqlFilter.EQ("parentid", queryRequest.getTreeNode().getString("id"));
		}
		return jdbcTemplateService.grid(sqlFilter,queryRequest.getPageSize(),queryRequest.getPageNo());
	}
	
	private SQLDefine getSQLDefine(String sqlId){
		return sqlDefineService.getRepository().findBySqlId(sqlId);
	}
	/**
	 * 通过sql获取其对应的字段列表
	 * 
	 * @param sqlIndex
	 */
	@Override
	@Transactional
	public List<SQLViewField> showFullColumnsBySql(String sqlId) throws Exception {
		List<SQLViewField> list = new ArrayList<SQLViewField>();
		String tempTableName  = "TEMP_TABLE_VIEW_SQL_"+GUID.nextId();
		try {
			SQLDefine sqlDefine = getSQLDefine(sqlId);
			if(sqlDefine == null){
				logger.error("未知的SQLID:{}",sqlId);
				return list;
			}
			
			//创建一个临时表
			String viewSql = "CREATE TABLE " + tempTableName + " AS SELECT T.* FROM (" + sqlDefine.getSelectSql()+") T WHERE 1=2 ";
			jdbcTemplateService.execute(viewSql);

			//通过临时表 找到对应的字段属性
			String showColumnSql = "SHOW FULL COLUMNS FROM "+ tempTableName;
			List<Map<String, Object>> queryForList = namedParameterJdbcTemplate.queryForList(showColumnSql,new HashMap<String,String>());
			 
			//将属性存入JSON方便下面获取  {"name":"姓名","sex":"性别"}
			SQLViewField field = null;
			for (Map<String,Object> item : queryForList) {
				field = new SQLViewField();
				field.setId(GUID.nextId());
				field.setTitle(setTitle(item.get("Comment"), item.get("Field").toString()));
				field.setField(item.get("Field").toString());
				this.setTypeAndLenth(item.get("Type").toString(), field);
				field.setIdx(queryForList.indexOf(item));
				list.add(field);
			}
			
			//删除临时表
			String dropViewSql = "DROP TABLE "+ tempTableName;
			jdbcTemplateService.execute(dropViewSql);
		} catch (Exception e) {
			logger.error("获取字段列表异常{}", e);
			jdbcTemplateService.execute("DROP TABLE "+tempTableName);
		}
		return list;
	}
	
	private void setTypeAndLenth(String type,SQLViewField field){
		 String[] array = type.split("\\(");
		 String t = array[0];
		 field.setDataType(t.toUpperCase());
		 if(array.length > 1){
			 String length = array[1].replace(")", "");
			 if(StringUtils.isNoneBlank(length)){
				 field.setLength(length);
			 }
		 }
		 
		//判断是否是日期类型
		if (getDataType(t).equals(SQLViewConstant.COLUMNTYPE_DATE)) {
			field.setComponentType(ComponentType.DATEPICKER.getValue());
			field.setExpand("yyyy-MM-dd hh:mm:ss");
		}
	}
	
	private String setTitle(Object value,String field){
		return value != null && StringUtils.isNoneBlank(value.toString()) ? value.toString() : field;
	}
	/**
	 * 获取字段类型
	 * @param dataType
	 * @return String
	 */
	private String getDataType(String dataType) {
		String dbType = dataType.toLowerCase();
		
		String number = "int,double,float,decimal,number,numeric";
		String date = "date,timestamp,datetime";
		String text = "clob,text";
		String varchar = "varchar,varchar2,char";
		
		boolean isChar = isSpecType(dbType, varchar);
		if (isChar) {
			return SQLViewConstant.COLUMNTYPE_VARCHAR;
		}
		
		boolean isNumber = isSpecType(dbType, number);
		if (isNumber) {
			return SQLViewConstant.COLUMNTYPE_NUMBER;
		}
		
		boolean isDate = isSpecType(dbType, date);
		if (isDate) {
			return SQLViewConstant.COLUMNTYPE_DATE;
		}
		
		boolean isText = isSpecType(dbType, text);
		if (isText) {
			return SQLViewConstant.COLUMNTYPE_TEXT;
		}
		return dbType;
	}
	
	/**
	 * 是否包含指定的数据类型。
	 * 
	 * @param dbType
	 * @param dataType
	 */
	private boolean isSpecType(String dbType, String dataType) {
		String[] aryType = dataType.split(",");
		for (String str : aryType) {
			if (dbType.equals(str) || dbType.indexOf(str) > -1) {
				return true;
			}
		}
		return false;
	}
	
}
