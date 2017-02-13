package com.sophia.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.sophia.constant.ComponentType;
import com.sophia.constant.SQLExpression;
import com.sophia.constant.SQLViewConstant;
import com.sophia.constant.TreeNodeHandleType;
import com.sophia.domain.Pager;
import com.sophia.domain.SQLDefine;
import com.sophia.domain.SQLView;
import com.sophia.domain.SQLViewField;
import com.sophia.exception.ServiceException;
import com.sophia.repository.SQLViewRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.SqlIdJdbcService;
import com.sophia.service.SQLViewFieldService;
import com.sophia.service.SQLViewService;
import com.sophia.service.SqlDefineService;
import com.sophia.utils.SimpleUtils;
import com.sophia.utils.SqlFilter;
import com.sophia.vo.ConditionResult;
import com.sophia.vo.QueryParam;
import com.sophia.vo.SQLViewParam;
import com.sophia.vo.SQLViewQueryParam;
import com.sophia.vo.TreeResult;
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
	@Autowired SqlDefineService sqlDefineService;
	@Autowired SQLViewFieldService sqlViewFieldService;
	@Autowired SqlIdJdbcService sqlIdJdbcService;
	@Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private String sql="select t.* from TB_SM_VIEW t ";
	public String save(SQLViewParam sqlViewRequest){

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

		//排序conditions，buttons
		sqlView.setConditionList(JsonArraySort(sqlView.getConditions(), "idx"));
		sqlView.setButtonList(JsonArraySort(sqlView.getButtons(), "idx"));
		return  sqlView;
	}
	public Pager<Map<String,Object>> list(QueryParam queryRequest){
		SqlFilter sqlFilter = new SqlFilter(queryRequest.getCondition());
		sqlFilter.setMainSql(sql);
		if(queryRequest.getTreeNode()!=null){
			sqlFilter.EQ("parentid", queryRequest.getTreeNode().getString("id"));
		}
		return sqlIdJdbcService.filter(sqlFilter,queryRequest.getPageSize(),queryRequest.getPageNo());
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
	public List<SQLViewField> showFullColumnsBySql(String sqlId) {
		List<SQLViewField> list = new ArrayList<SQLViewField>();

		SQLDefine sqlDefine = getSQLDefine(sqlId);
		if(sqlDefine == null){
			throw new ServiceException("SQLID:["+sqlId+"]未定义");
		}

		//获取主表实际列用来过滤
		String masterSql = "SELECT * FROM " + sqlDefine.getMasterTable() + " T WHERE 1=2 " ;
		SqlRowSet resultSet = namedParameterJdbcTemplate.queryForRowSet(masterSql,new HashMap<String,String>());
		SqlRowSetMetaData srsmd = resultSet.getMetaData();
		Map<String,Object> masterFieldMap = new HashMap<>();
		for (int i = 1; i < srsmd.getColumnCount() + 1; i++) {
			masterFieldMap.put(srsmd.getColumnLabel(i), srsmd.getColumnLabel(i));
		}

		//获取查询所有列
		String viewSql = " SELECT T.* FROM (" + sqlDefine.getSelectSql()+") T WHERE 1=2 ";

		//通过临时表 找到对应的字段属性
		resultSet = namedParameterJdbcTemplate.queryForRowSet(viewSql,new HashMap<String,String>());
		srsmd = resultSet.getMetaData();
		SQLViewField field = null;
		for (int i = 1; i < srsmd.getColumnCount() + 1; i++) {
			field = new SQLViewField();
			field.setId(GUID.nextId());
			this.setFieldTitle(field, srsmd.getColumnLabel(i));
			field.setField(srsmd.getColumnLabel(i));// as 后的值 ，getColumnName 原始值
			field.setLength(String.valueOf(srsmd.getPrecision(i)));
			field.setDataType(srsmd.getColumnTypeName(i));

			//判断是否是日期类型
			if (SimpleUtils.getDataType(field.getDataType()).equals(SQLViewConstant.COLUMNTYPE_DATE)) {
				field.setComponentType(ComponentType.DATEPICKER.getValue());
				field.setOptions("%Y-%m-%d %H:%i:%s");
			}
			field.setIdx(i-1);
			if(masterFieldMap.containsKey(field.getField())){

				//修改类型
				field.setModiftyType(SQLViewConstant.MODIFTY_NORMAL);

				//是否增加
				field.setIsInsert(SQLViewConstant.YES);
			}
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

	/**
	 * JsonArray排序
	 * @param jsonArray
	 * @param sortKey
	 * @return
	 */
	private  List<JSONObject> JsonArraySort(String jsonArray,final String sortKey){
		if(StringUtils.isNotBlank(jsonArray)){
			List<JSONObject> condList = JSONObject.parseArray(jsonArray, JSONObject.class);
			Collections.sort(condList,new Comparator<JSONObject>() {

				@Override
				public int compare(JSONObject o1, JSONObject o2) {
					return (o1.getInteger(sortKey) != null ?  o1.getInteger(sortKey) : 0) - ( o2.getInteger(sortKey) != null ? o2.getInteger(sortKey) : 0);

				}
			});
			return condList;
		}
		return new ArrayList<JSONObject>();
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

		//排序conditions，buttons
		sqlView.setConditionList(JsonArraySort(sqlView.getConditions(), "idx"));
		sqlView.setButtonList(JsonArraySort(sqlView.getButtons(), "idx"));
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

		/*补充字段
		insertSQL.append(SQLViewConstant.CREATE_TIME).append(",")
		.append(SQLViewConstant.CREATE_USER).append(")");
		values.append(":").append(SQLViewConstant.CREATE_TIME).append(",")
						  .append(":").append(SQLViewConstant.CREATE_USER).append(") ");
		paramMap.put(SQLViewConstant.CREATE_TIME, new Date());
		paramMap.put(SQLViewConstant.CREATE_USER, SecurityContextHolder.getContext().getAuthentication().getName());*/

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
			if(SQLViewConstant.MODIFTY_NORMAL.equals(field.getModiftyType())){
				modifySQL.append(field.getField()).append("= :")
				.append(field.getField())
				.append(",");
				paramMap.put(field.getField(), row.get(field.getField()));
			}
		}
		modifySQL.deleteCharAt(modifySQL.lastIndexOf(","));

		//是否包含主键
		if(StringUtils.isBlank(sqlDefine.getMasterTableId())){
			throw new ServiceException("SQLID:"+sqlView.getSqlId()+",未设置主键");
		}

		/*补充字段
		modifySQL.append(SQLViewConstant.LAST_UPDATE_TIME).append("= :").append(SQLViewConstant.LAST_UPDATE_TIME).append(",")
				 .append(SQLViewConstant.LAST_UPDATE_USER).append("= :").append(SQLViewConstant.LAST_UPDATE_USER);
		paramMap.put(SQLViewConstant.LAST_UPDATE_TIME, new Date());
		paramMap.put(SQLViewConstant.LAST_UPDATE_USER, SecurityContextHolder.getContext().getAuthentication().getName());*/

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
		return namedParameterJdbcTemplate.queryForMap(sql, paramMap);
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
		result.put("row", namedParameterJdbcTemplate.queryForMap(sql, paramMap));
		result.put("sqlView", sqlView);
		return result;
	}
	@Override
	public Pager<Map<String, Object>> findSqlViewGrid(String code,SQLViewQueryParam queryRequest){
		SQLView sqlView = getRepository().getByCode(code);
		ConditionResult conditionResult = getTreeNode(sqlView.getTreeData(),queryRequest.getTreeNode());
		SqlFilter sqlFilter = new SqlFilter(queryRequest.getCondition());
		if(null != conditionResult){
			sqlFilter.addCondition(conditionResult);
		}

		//获取显示字段
		List<SQLViewField> sqlViewFieldList = sqlViewFieldService.getRepository().getByViewId(sqlView.getId());
		SQLDefine sqlDefine = sqlDefineService.findBySqlId(sqlView.getSqlId());
		if(CollectionUtils.isEmpty(sqlViewFieldList)){
			sqlFilter.setMainSql(sqlDefine.getSelectSql());
		}else{

			//拼装sql显示列表
			StringBuffer sb = new StringBuffer("SELECT ");
			ConditionResult sortCond;
			for(SQLViewField field : sqlViewFieldList){
				if(SQLViewConstant.COLUMNTYPE_DATE.equals(SimpleUtils.getDataType(field.getDataType())) &&
						StringUtils.isNotBlank(field.getOptions())){
					sb.append("DATE_FORMAT(").append(field.getField()).append(",'").append(field.getOptions()).append("') AS ").append(field.getField());
				}else{
					sb.append(field.getField());
				}
				sb.append(",");

				//排序
				if(StringUtils.isNotBlank(field.getSort())){
					sortCond = new ConditionResult();
					sortCond.setField(field.getField());
					sortCond.setSort(field.getSort());
					sqlFilter.addCondition(sortCond);
				}
			}
			sb.deleteCharAt(sb.lastIndexOf(",")).append(" from ( ").append(sqlDefine.getSelectSql()).append(") t ");
			sqlFilter.setMainSql(sb.toString());
		}
		return sqlIdJdbcService.filter(sqlFilter,queryRequest.getPageSize(),queryRequest.getPageNo());
	}

	/**
	 * 获取树节点条件
	 * @param treeConfig
	 * @return
	 */
	private ConditionResult getTreeNode(String treeConfig,JSONObject treeNode){
		TreeResult treeDto = JSONObject.parseObject(treeConfig, TreeResult.class);
		if(!SimpleUtils.isTrue(treeDto.getIsShow())){
			return null;
		}
		
		//获取sqlDefine 
		SQLDefine sqlDefine = sqlDefineService.findBySqlId(treeDto.getSqlId());
		Map<String,Object> paramMap = new HashMap<String, Object>();
		String idValue = SQLViewConstant.TREE_ROOT;

		//第一次默认为加载ROOT节点
		if(null != treeNode){
			idValue = treeNode.getString(treeDto.getIdKey());
		}
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		switch (treeDto.getNodeOpts()) {
		case TreeNodeHandleType.TREEHANDLETYPE_ALL:
			result = findAllNode(warpTreeSql(sqlDefine.getSelectSql(), treeDto.getpIdKey()), idValue, treeDto);
			break;
		case TreeNodeHandleType.TREEHANDLETYPE_CHILD:
			paramMap.put(treeDto.getpIdKey(), idValue);
			result = namedParameterJdbcTemplate.queryForList(warpTreeSql(sqlDefine.getSelectSql(), treeDto.getpIdKey()), paramMap);
			break;
		case TreeNodeHandleType.TREEHANDLETYPE_SELF:
			paramMap.put(treeDto.getIdKey(), idValue);
			result.add(namedParameterJdbcTemplate.queryForMap(warpTreeSql(sqlDefine.getSelectSql(), treeDto.getIdKey()), paramMap));
			break;
		}
		ConditionResult conditionDto = new ConditionResult();
		conditionDto.setField(treeDto.getRelationField());
		conditionDto.setExpr(SQLExpression.IN);
		//TODO 换成in语句
		conditionDto.setValue(appendIds(result,treeDto.getIdKey()));
		return conditionDto;
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
	private List<Map<String, Object>> findAllNode(String sql,Object pId,TreeResult treeVo){
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

	@Override
	public List<SQLViewField> findFieldListByViewCode(String viewCode) {
		SQLView sqlView = getRepository().getByCode(viewCode);
		return sqlViewFieldService.getRepository().getByViewIdOrderByIdxAsc(sqlView.getId());
	}
}
