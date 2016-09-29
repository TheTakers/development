package com.sophia.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.sophia.constant.SQLViewConstant;
import com.sophia.domain.SQLDefine;
import com.sophia.domain.SQLView;
import com.sophia.domain.SQLViewField;
import com.sophia.repository.SQLViewRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.request.GridResponse;
import com.sophia.request.QueryRequest;
import com.sophia.service.JdbcTemplateService;
import com.sophia.service.SQLDefineService;
import com.sophia.service.SQLViewService;
import com.sophia.utils.SQLFilter;
import com.sophia.web.util.GUID;

@Service
public class SQLViewServiceImpl extends JpaRepositoryImpl<SQLViewRepository> implements SQLViewService  {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String COLUMNTYPE_INT	  = "int";
	public static final String COLUMNTYPE_VARCHAR = "varchar";
	public static final String COLUMNTYPE_CLOB	  = "clob";
	public static final String COLUMNTYPE_NUMBER  = "number";
	public static final String COLUMNTYPE_DATE	  = "date";
	public static final String COLUMNTYPE_TEXT	  = "text";
	
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
	public List<SQLViewField> getObtainFieldListBySql(String sqlId) throws Exception {
		List<SQLViewField> list = new ArrayList<SQLViewField>();
		String tempTableName  = "TEMP_TABLE_VIEW_SQL_"+GUID.nextId();
		try {
			SQLDefine sqlDefine = getSQLDefine(sqlId);
			if(sqlDefine == null){
				logger.warn("SQLID:{},未发现",sqlId);
				return list;
			}
			
			//创建一个临时表
			String viewSql = "create table " + tempTableName + " as select t.* from (" + sqlDefine.getSelectSql()+") t where 1=2 ";
			jdbcTemplateService.execute(viewSql);

			//通过临时表 找到对应的字段属性
			String showColumnSql = "show full columns from "+ tempTableName;
			List<Map<String, Object>> queryForList = namedParameterJdbcTemplate.queryForList(showColumnSql,new HashMap<String,String>());
			 
			//将属性存入jo当中 方便下面获取  {"name":"姓名","sex":"性别"}
			JSONObject jo = new JSONObject();
			for (Map<String,Object> map : queryForList) {
				if (map.get("Comment") != null) {
					jo.put((String) map.get("Field"), map.get("Comment"));
				}
			}
			
			//删除临时表
			String dropViewSql = "drop table "+ tempTableName;
			jdbcTemplateService.execute(dropViewSql);

			//通过sql获取字段的内容
			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(sql,new HashMap<String,String>());
			SqlRowSetMetaData srsmd = srs.getMetaData();
			
			// 列从1开始算
			for (int i = 1; i < srsmd.getColumnCount() + 1; i++) {
				String cn = srsmd.getColumnName(i);
				String ctn = srsmd.getColumnTypeName(i);
				
				SQLViewField field = new SQLViewField();
				field.setId(GUID.nextId());
				
				//字段name
				field.setTitle(cn);
				String columnLabel = srsmd.getColumnLabel(i);
				field.setField(columnLabel);
				
				//设置字段备注    并且 判断jo中是否含有该字段的备注
				if (jo.containsKey(columnLabel)) {
					field.setRemark(jo.getString(columnLabel));
				} else {
					field.setRemark(columnLabel);
				}
				
				//设置字段类型
				String dataType = getDataType(ctn);
				field.setDataType(dataType);
				
				//设置默认属性
				field.setDiaplay(SQLViewConstant.YES);
				field.setIsSearch(SQLViewConstant.NO);
				field.setComponentType(SQLViewConstant.COMPONENTTYPE_TEXT);
				
				//判断是否是日期类型
				if (dataType.equals(COLUMNTYPE_DATE)) {
					field.setComponentType(SQLViewConstant.COMPONENTTYPE_TEXT);
					field.setExpand("yyyy-MM-dd hh:mm:ss");
				}
				field.setIdx(i);
				list.add(field);
			}
		} catch (Exception e) {
			logger.error("获取字段列表异常{}", e);
			jdbcTemplateService.execute("drop table "+tempTableName);
		}
		return list;
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
			return COLUMNTYPE_VARCHAR;
		}
		
		boolean isNumber = isSpecType(dbType, number);
		if (isNumber) {
			return COLUMNTYPE_NUMBER;
		}
		
		boolean isDate = isSpecType(dbType, date);
		if (isDate) {
			return COLUMNTYPE_DATE;
		}
		
		boolean isText = isSpecType(dbType, text);
		if (isText) {
			return COLUMNTYPE_TEXT;
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
