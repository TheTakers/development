package com.sophia.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zkning
 */
public class SQLFilter {
	
	private String mainSql = null;
	
	private StringBuffer condition = new StringBuffer();
	private StringBuffer orderBy = new StringBuffer();

	private static String alias = "field";
	private static String expr ="expr";
	private static String value ="value";
	private static String sort ="sort";

	private static String LIKE ="like";
	private static String WHERE =" where ";

	private Map<String,Object> params = new HashMap<>();

	public SQLFilter(String conditon){
		addCondition(JSONObject.parseArray(conditon));
	}

	public SQLFilter(JSONArray conditon){
		addCondition(conditon);
	}
	public SQLFilter(){}

	public void addCondition(JSONArray array){
		if(array == null)
			return;

		for(int idx =0;idx<array.size();idx++){
			JSONObject cond = array.getJSONObject(idx);
			addCondition(get(cond, alias),get(cond, expr),get(cond, value),get(cond, sort));
		}
	}

	public String get(JSONObject cond,String key){
		return cond.getString(key);
	}

	public void EQ(String alias,String value){
		addCondition(alias,"=",value,null);
	}

	public void addCondition(String alias,String expr ,String value,String sort){

		if(StringUtils.isNotBlank(value)){

			if(StringUtils.isNotBlank(condition))
				condition.append(" AND ");

			condition.append(alias)
			.append(" " + expr +" ")
			.append(":").append(alias);

			if(LIKE.equals(expr)){
				params.put(alias, "%"+value+"%");
			}else{
				params.put(alias, value);
			}

		}

		addSort(alias, sort);
	}

	/**
	 * 增加排序
	 * @param alias
	 * @param sort
	 */
	public void addSort(String alias,String sort){

		if(StringUtils.isBlank(sort))
			return;

		if(StringUtils.isBlank(orderBy)){

			orderBy.append(" ORDER BY ");
		}else{

			orderBy.append(" ,");
		}
		orderBy.append(alias).append(" ").append(sort);
	}
	
	/**
	 * count sql
	 * @return
	 */
	public String getCountSql(){
		StringBuffer storeSql = new StringBuffer();
		storeSql.append("select count(1) from (")
		.append(this.mainSql)
		.append(") t ");

		if(StringUtils.isNotBlank(condition)){
			storeSql.append( WHERE ).append(condition); 
		}
		return storeSql.toString();
	}
	
	/**
	 * SQL
	 * @return
	 */
	public String getSql(){
		StringBuffer storeSql = new StringBuffer();
		storeSql.append("select * from (")
		.append(mainSql)
		.append(") t ");

		if(StringUtils.isNotBlank(condition)){
			storeSql.append( WHERE ).append(condition); 
		}
		return storeSql.append(orderBy).toString();
	}
	
	/**
	 * 分页SQL
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public String getLimitSql(Integer pageNo,Integer pageSize){
		
		StringBuffer storeSql = new StringBuffer();
		storeSql.append("select * from (")
		.append(mainSql)
		.append(") t ");

		if(StringUtils.isNotBlank(condition)){
			storeSql.append( WHERE ).append(condition); 
		}
		
		storeSql.append(orderBy)
				.append(" LIMIT ")
				.append(pageNo * pageSize)
				.append(",")
				.append(pageSize);
		
		return storeSql.toString();
	}
	
	/**
	 * condition sql
	 * @return
	 */
	public String conditionSql(){
		return condition.append(orderBy).toString();
	}
	
	/**
	 * 获取参数
	 * @return
	 */
	public Map<String, Object> getParams() {
		return params;
	}
	
	/**
	 * 添加参数
	 * @param alis
	 * @param value
	 */
	public void put(String alis,Object value){
		params.put(alis, value);
	}
	
	public String getMainSql() {
		return mainSql;
	}

	public void setMainSql(String main) {
		this.mainSql = main;
	}

	public static SQLFilter getInstance(String conditon){
		return new SQLFilter(conditon);
	}
	
	

	public static SQLFilter getInstance(){
		return new SQLFilter();
	}
	
}
