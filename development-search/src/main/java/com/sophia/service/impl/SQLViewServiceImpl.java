package com.sophia.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.sophia.constant.ComponentType;
import com.sophia.constant.SQLExpression;
import com.sophia.constant.SQLViewConstant;
import com.sophia.constant.TreeNodeHandleType;
import com.sophia.domain.SQLDefine;
import com.sophia.domain.SQLView;
import com.sophia.domain.SQLViewField;
import com.sophia.exception.ServiceException;
import com.sophia.repository.SQLViewRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.request.QueryRequest;
import com.sophia.request.SQLViewQueryRquest;
import com.sophia.request.SQLViewRequest;
import com.sophia.response.GridResponse;
import com.sophia.service.JdbcTemplateService;
import com.sophia.service.SQLDefineService;
import com.sophia.service.SQLViewFieldService;
import com.sophia.service.SQLViewService;
import com.sophia.utils.CrudeUtils;
import com.sophia.utils.SQLFilter;
import com.sophia.vo.ConditionVo;
import com.sophia.vo.TreeVo;
import com.sophia.web.util.GUID;

/**
 * SQLVIEW服务
 * @author zkning
 */
@Service
@Transactional
public class SQLViewServiceImpl extends JpaRepositoryImpl<SQLViewRepository> implements SQLViewService  {

	Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	@Autowired JdbcTemplateService jdbcTemplateService;
	@Autowired SQLDefineService sqlDefineService;
	@Autowired SQLViewFieldService sqlViewFieldService;
	@Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private String sql="select t.* from TB_SM_VIEW t ";
	public String save(SQLViewRequest sqlViewRequest){

		//基本信息
		SQLView sqlView = new SQLView();
		BeanUtils.copyProperties(sqlViewRequest, sqlView);
		if(StringUtils.isBlank(sqlViewRequest.getId())){
			sqlView.setId(GUID.nextId());
		}
		sqlView.setButtons(sqlViewRequest.getButtonList().toJSONString());
		sqlView.setTreeData(sqlViewRequest.getTreeData().toJSONString());
		sqlView.setConditions(sqlViewRequest.getFilterList().toJSONString());

		//先删除列表
		List<SQLViewField> sqlViewFieldList = sqlViewFieldService.getRepository().getByViewId(sqlView.getId());
		if(!CollectionUtils.isEmpty(sqlViewFieldList)){
			sqlViewFieldService.getRepository().delete(sqlViewFieldList);
		}
		//保存SQL列表
		for(SQLViewField field : sqlViewRequest.getColumnList()){
			field.setId(GUID.nextId());
			field.setViewId(sqlView.getId());
		}
		sqlViewFieldService.getRepository().save(sqlViewRequest.getColumnList());

		//生成GROUP PATH
		return getRepository().save(sqlView).getId();
	}
	@Override
	public SQLView findById(String id) {
		//基本信息
		SQLView sqlView = getRepository().findOne(id);
		//获取列表
		List<SQLViewField> sqlViewFieldList = sqlViewFieldService.getRepository().getByViewIdOrderByIdxAsc(sqlView.getId());
		sqlView.setColumnList(sqlViewFieldList);
		return  sqlView;
	}
	public GridResponse<Map<String,Object>> list(QueryRequest queryRequest){
		SQLFilter sqlFilter = new SQLFilter(queryRequest.getCondition());
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
	public List<SQLViewField> showFullColumnsBySql(String sqlId) throws Exception {
		List<SQLViewField> list = new ArrayList<SQLViewField>();

		SQLDefine sqlDefine = getSQLDefine(sqlId);
		if(sqlDefine == null){
			logger.error("SQLID:{}未定义",sqlId);
			throw new Exception("SQLID:["+sqlId+"]未定义");
		}

		//创建一个临时表
		String viewSql = " SELECT T.* FROM (" + sqlDefine.getSelectSql()+") T WHERE 1=2 ";

		//通过临时表 找到对应的字段属性
		SqlRowSet resultSet = namedParameterJdbcTemplate.queryForRowSet(viewSql,new HashMap<String,String>());
		SqlRowSetMetaData srsmd = resultSet.getMetaData();
		SQLViewField field = null;
		for (int i = 1; i < srsmd.getColumnCount() + 1; i++) {
			field = new SQLViewField();
			field.setId(GUID.nextId());
			this.setFieldTitle(field, srsmd.getColumnLabel(i));
			field.setField(srsmd.getColumnLabel(i));// as 后的值 ，getColumnName 原始值
			field.setLength(String.valueOf(srsmd.getPrecision(i)));
			field.setDataType(srsmd.getColumnTypeName(i));

			//判断是否是日期类型
			if (getDataType(field.getDataType()).equals(SQLViewConstant.COLUMNTYPE_DATE)) {
				field.setComponentType(ComponentType.DATEPICKER.getValue());
				field.setExpand("yyyy-MM-dd hh:mm:ss");
			}
			field.setIdx(i);
			list.add(field);
		}
		return list;
	}
	
	/**
	 * 设置名称
	 * @param sqlViewField
	 * @param field
	 */
	private void setFieldTitle(SQLViewField sqlViewField,String field){
		switch (field.toUpperCase()) {
		case SQLViewConstant.LAST_UPDATE_TIME:
			sqlViewField.setTitle("更新时间");
			break;
		case SQLViewConstant.LAST_UPDATE_USER:
			sqlViewField.setTitle("更新者");
			break;
		case SQLViewConstant.CREATE_TIME:
			sqlViewField.setTitle("创建时间");
			break;
		case SQLViewConstant.CREATE_USER:
			sqlViewField.setTitle("创建者");
			break;
		case SQLViewConstant.VERSION:
			sqlViewField.setTitle("版本号");
			break;
		default:
			sqlViewField.setTitle(field);
			break;
		}
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

	public SQLView getSqlViewByCode(String code){
		SQLView sqlView = getRepository().getByCode(code);
		if(sqlView == null){
			throw new ServiceException("编号:"+ code + "未定义");
		}
		List<SQLViewField> columnList = sqlViewFieldService.getRepository().getByViewIdOrderByIdxAsc(sqlView.getId());
		SQLDefine sqlDefine = sqlDefineService.getRepository().findBySqlId(sqlView.getSqlId());
		if(sqlDefine == null){
			throw new ServiceException("SQLID:"+ sqlView.getSqlId() + "未定义");
		}
		sqlView.setSqlDefine(sqlDefine);
		sqlView.setColumnList(columnList);
		return sqlView;
	}

	public void persistentByCode(String code,JSONObject row){

		SQLView sqlView = getRepository().getByCode(code);
		if(sqlView == null){
			throw new ServiceException("编号:"+ code + "未定义");
		}
		//获取sqlDefine 
		SQLDefine sqlDefine = sqlDefineService.getRepository().findBySqlId(sqlView.getSqlId());
		if(sqlDefine == null){
			throw new ServiceException("SQLID:"+ sqlView.getSqlId() + "未定义");
		}
		//获取修改列
		List<SQLViewField> sqlViewFields = sqlViewFieldService.getRepository().getByViewId(sqlView.getId());
		if(CollectionUtils.isEmpty(sqlViewFields)){
			throw new ServiceException("视图编号:"+ sqlView.getSqlId() + "未设置字段列表");
		}

		//拼装SQL
		StringBuffer insertSQL = new StringBuffer("INSERT INTO ")
				.append(sqlDefine.getMasterTable())
				.append("(");

		//输入
		StringBuffer values = new StringBuffer("(");

		//参数
		Map<String,Object> paramMap = new HashMap<>();
		for(SQLViewField field : sqlViewFields){
			if(SQLViewConstant.YES.equals(field.getIsInsert())){

				 
				insertSQL.append(field.getField()).append(",");
				values.append(":").append(field.getField()).append(",");
				paramMap.put(field.getField(), row.get(field.getField()));
			}
		}

		//自动生成id
		if(StringUtils.isNotBlank(sqlDefine.getMasterTableId())){
			insertSQL.append(sqlDefine.getMasterTableId()).append(",");
			values.append(":").append(sqlDefine.getMasterTableId()).append(",");
			row.put(sqlDefine.getMasterTableId(), GUID.nextId());
		}else{
			throw new ServiceException("SQLID:"+sqlView.getSqlId()+",未设置主键");
		}

		//补充字段
		insertSQL.append(SQLViewConstant.CREATE_TIME).append(",")
		.append(SQLViewConstant.CREATE_USER).append(")");
		values.append(":").append(SQLViewConstant.CREATE_TIME).append(",")
						  .append(":").append(SQLViewConstant.CREATE_USER).append(") ");
		paramMap.put(SQLViewConstant.CREATE_TIME, new Date());
		paramMap.put(SQLViewConstant.CREATE_USER, SecurityContextHolder.getContext().getAuthentication().getName());

		final String sql = insertSQL.append(values).toString();
		if(namedParameterJdbcTemplate.update(sql, paramMap) < 1){
			throw new ServiceException("数据插入失败");
		}
	}

	public void modifyByCode(String code,JSONObject row){

		SQLView sqlView = getRepository().getByCode(code);
		if(sqlView == null){
			throw new ServiceException("视图编号:"+ code + "未定义");
		}
		
		//获取sqlDefine 
		SQLDefine sqlDefine = sqlDefineService.getRepository().findBySqlId(sqlView.getSqlId());
		if(sqlDefine == null){
			throw new ServiceException("SQLID:"+ sqlView.getSqlId() + "未定义");
		}
		
		//获取修改列
		List<SQLViewField> sqlViewFields = sqlViewFieldService.getRepository().getByViewId(sqlView.getId());
		if(CollectionUtils.isEmpty(sqlViewFields)){
			throw new ServiceException("视图编号:"+ sqlView.getSqlId() + "未设置字段列表");
		}

		//拼装SQL
		StringBuffer modifySQL = new StringBuffer(" UPDATE ")
				.append(sqlDefine.getMasterTable())
				.append(" SET ");

		//参数
		Map<String,Object> paramMap = new HashMap<>();
		for(SQLViewField field : sqlViewFields){
			if(SQLViewConstant.YES.equals(field.getIsUpdate())){
				modifySQL.append(field.getField()).append("= :")
				.append(field.getField())
				.append(",");
				paramMap.put(field.getField(), row.get(field.getField()));
			}
		}

		//是否包含主键
		if(StringUtils.isBlank(sqlDefine.getMasterTableId())){
			throw new ServiceException("SQLID:"+sqlView.getSqlId()+",未设置主键");
		}

		//补充字段
		modifySQL.append(SQLViewConstant.LAST_UPDATE_TIME).append("= :").append(SQLViewConstant.LAST_UPDATE_TIME).append(",")
				 .append(SQLViewConstant.LAST_UPDATE_USER).append("= :").append(SQLViewConstant.LAST_UPDATE_USER);
		paramMap.put(SQLViewConstant.LAST_UPDATE_TIME, new Date());
		paramMap.put(SQLViewConstant.LAST_UPDATE_USER, SecurityContextHolder.getContext().getAuthentication().getName());
		modifySQL.append(" WHERE ")
			  .append(sqlDefine.getMasterTableId())
			  .append("= :").append(sqlDefine.getMasterTableId());
		paramMap.put(sqlDefine.getMasterTableId(),row.get(sqlDefine.getMasterTableId()));
		if(namedParameterJdbcTemplate.update(modifySQL.toString(), paramMap) < 1){
			throw new ServiceException("数据修改失败");
		}
	}

	public void deleteByCode(String code,JSONObject row){

		SQLView sqlView = getRepository().getByCode(code);
		if(sqlView == null){
			throw new ServiceException("编号:"+ code + "未定义");
		}
		
		//获取sqlDefine 
		SQLDefine sqlDefine = sqlDefineService.getRepository().findBySqlId(sqlView.getSqlId());
		if(sqlDefine == null){
			throw new ServiceException("SQLID:"+ sqlView.getSqlId() + "未定义");
		}
		
		//获取修改列
		List<SQLViewField> sqlViewFields = sqlViewFieldService.getRepository().getByViewId(sqlView.getId());
		if(CollectionUtils.isEmpty(sqlViewFields)){
			throw new ServiceException("视图编号:"+ sqlView.getSqlId() + "未设置字段列表");
		}

		if(StringUtils.isEmpty(sqlDefine.getMasterTableId())){
			throw new ServiceException("未配置主键");
		}

		//拼装SQL
		StringBuffer deleteSql = new StringBuffer(" DELETE FROM ")
				.append(sqlDefine.getMasterTable())
				.append(" WHERE ")
				.append(sqlDefine.getMasterTableId())
				.append(" = :").append(sqlDefine.getMasterTableId());

		//参数
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put(sqlDefine.getMasterTableId(),row.get(sqlDefine.getMasterTableId()));
		if(namedParameterJdbcTemplate.update(deleteSql.toString(), paramMap) < 1){
			throw new ServiceException("数据删除失败");
		}
	}

	public Map<String,Object> getDataBySqlId(String sqlId,JSONObject row){
		
		//获取sqlDefine 
		SQLDefine sqlDefine = sqlDefineService.getRepository().findBySqlId(sqlId);
		if(sqlDefine == null){
			throw new ServiceException("SQLID:"+ sqlId + "未定义");
		}
		if(null == row){
			throw new ServiceException("SQLID:"+ sqlDefine.getSqlId() + ",行数据未获取");
		}
		
		//获取主键值
		String pkId = row.getString(sqlDefine.getMasterTableId());
		String sql = "select t.* from (" + sqlDefine.getSelectSql() +") t "+ " WHERE t." + sqlDefine.getMasterTableId() +" = :"+sqlDefine.getMasterTableId();
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put(sqlDefine.getMasterTableId(), pkId);
		return jdbcTemplateService.queryForMap(sql, paramMap);
	}
	
	public Map<String,Object> getSqlViewAndSqlDefineRowDataByCode(String code,JSONObject row){
		Map<String,Object> result = new HashMap<>();

		//查询SQLVIEW
		SQLView sqlView = getRepository().getByCode(code);
		if(sqlView == null){
			throw new ServiceException("编号:"+code+"视图未定义");
		}
		
		//查询设置字段
		sqlView.setColumnList(sqlViewFieldService.getRepository().getByViewIdOrderByIdxAsc(sqlView.getId()));
		
		//获取sqlDefine 
		SQLDefine sqlDefine = sqlDefineService.getRepository().findBySqlId(sqlView.getSqlId());
		if(sqlDefine == null){
			throw new ServiceException("SQLID:"+ sqlView.getSqlId() + "未定义");
		}
		sqlView.setSqlDefine(sqlDefine);
		if(null == row){
			throw new ServiceException("SQLID:"+ sqlDefine.getSqlId() + ",行数据未获取");
		}
		
		//获取主键值
		String pkId = row.getString(sqlDefine.getMasterTableId());
		String sql = "select t.* from (" + sqlDefine.getSelectSql() +") t "+ " WHERE t." + sqlDefine.getMasterTableId() +" = :"+sqlDefine.getMasterTableId();
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put(sqlDefine.getMasterTableId(), pkId);
		result.put("row", jdbcTemplateService.queryForMap(sql, paramMap));
		result.put("sqlView", sqlView);
		return result;
	}
	@Override
	public GridResponse<Map<String, Object>> findSqlViewGrid(String code,SQLViewQueryRquest queryRequest){
		SQLView sqlView = getRepository().getByCode(code);
		ConditionVo conditionVo = getTreeNode(sqlView.getTreeData(),queryRequest.getTreeNode());
		SQLFilter sqlFilter = new SQLFilter(queryRequest.getCondition());
		if(null != conditionVo){
			sqlFilter.addCondition(conditionVo);
		}
		SQLDefine sqlDefine = sqlDefineService.findBySqlId(sqlView.getSqlId());
		sqlFilter.setMainSql(sqlDefine.getSelectSql());
		return jdbcTemplateService.grid(sqlFilter,queryRequest.getPageSize(),queryRequest.getPageNo());
	}
	
	/**
	 * 获取树节点条件
	 * @param treeConfig
	 * @return
	 */
	private ConditionVo getTreeNode(String treeConfig,JSONObject treeNode){
		TreeVo treeVo = JSONObject.parseObject(treeConfig, TreeVo.class);
		if(!CrudeUtils.isTrue(treeVo.getIsShow())){
			return null;
		}
		//获取sqlDefine 
		SQLDefine sqlDefine = sqlDefineService.findBySqlId(treeVo.getSqlId());
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		String idValue = SQLViewConstant.TREE_ROOT;
		
		//第一次默认为加载ROOT节点
		if(null != treeNode){
			idValue = treeNode.getString(treeVo.getIdKey());
		}
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		switch (treeVo.getNodeOpts()) {
		case TreeNodeHandleType.TREEHANDLETYPE_ALL:
			result = findAllNode(warpTreeSql(sqlDefine.getSelectSql(), treeVo.getpIdKey()), idValue, treeVo);
			break;
		case TreeNodeHandleType.TREEHANDLETYPE_CHILD:
			paramMap.put(treeVo.getpIdKey(), idValue);
			result = namedParameterJdbcTemplate.queryForList(warpTreeSql(sqlDefine.getSelectSql(), treeVo.getpIdKey()), paramMap);
			break;
		case TreeNodeHandleType.TREEHANDLETYPE_SELF:
			paramMap.put(treeVo.getIdKey(), idValue);
			result.add(jdbcTemplateService.queryForMap(warpTreeSql(sqlDefine.getSelectSql(), treeVo.getIdKey()), paramMap));
			break;
		}
		ConditionVo conditionVo = new ConditionVo();
		conditionVo.setField(treeVo.getRelationField());
		conditionVo.setExpr(SQLExpression.IN);
		//TODO 换成in语句
		conditionVo.setValue(appendIds(result,treeVo.getIdKey()));
		return conditionVo;
	}
	
	/**
	 * 拼接in字符
	 * @param mapList
	 * @param key
	 * @return
	 */
	private String appendIds(List<Map<String,Object>> mapList,String key){
		StringBuffer sb = new StringBuffer();
		for(Map<String,Object> item : mapList){
			sb.append(item.get(key)).append(",");
		}
		return StringUtils.isBlank(sb) ? SQLViewConstant.IN_NONE_CODE : sb.deleteCharAt(sb.lastIndexOf(",")).toString();
	}
	
	/**
	 * 拼装树查询SQL
	 * @param sql
	 * @param field
	 * @return
	 */
	private String warpTreeSql(String sql,String field){
		return  new StringBuffer("select t.* from (")
					.append(sql).append(") t WHERE T.").append(field).append(" =:").append(field).toString();
	}
	
	/**
	 * 根据nodeId获取所有子节点
	 * @param sql
	 * @param pId
	 * @param treeVo
	 * @return
	 */
	private List<Map<String, Object>> findAllNode(String sql,Object pId,TreeVo treeVo){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(treeVo.getpIdKey(), pId);
		List<Map<String, Object>> queryResult = new ArrayList<Map<String,Object>>(); 
		List<Map<String, Object>> result = namedParameterJdbcTemplate.queryForList(sql, paramMap);
		if(!CollectionUtils.isEmpty(result)){
			List<Map<String, Object>> subResult = null;
			for(Map<String, Object> subMap : result){
				subResult = findAllNode(sql, subMap.get(treeVo.getIdKey()), treeVo);
				if(!CollectionUtils.isEmpty(subResult))
					queryResult.addAll(subResult);
			}
			queryResult.addAll(result);
		}
		return queryResult;
	}
}
