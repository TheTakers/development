package com.sophia.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sophia.constant.SQLExpression;
import com.sophia.vo.ConditionVo;

/**
 * SQL过滤	
 * @author zkning
 */
public class SQLFilter {

	private String mainSql = null;

	private StringBuffer condition = new StringBuffer();
	private StringBuffer orderBy = new StringBuffer();
	
	private static String WHERE =" WHERE ";

	private Map<String,Object> params = new HashMap<>();
	
	public SQLFilter(String conditon){
		List<ConditionVo> conditionVoList = JSONObject.parseArray(conditon, ConditionVo.class);
		addCondition(conditionVoList);
	}

	public SQLFilter(List<ConditionVo> conditionVoList){
		addCondition(conditionVoList);
	}
	
	public SQLFilter(){}

	public void addCondition(List<ConditionVo> conditionVoList){
		if(conditionVoList == null)
			return;

		for(ConditionVo cond : conditionVoList){
			
			//排序字段
			String sortField = "";
			if(CrudeUtils.isTrue(cond.getIsSort())){
				sortField = cond.getField();
			}
			addCondition(cond.getField(),cond.getExpr(),cond.getValue(),sortField);
		}
	}

	public void addCondition(ConditionVo cond){
		addCondition(cond.getField(),cond.getExpr(),cond.getValue(),null);
	} 

	public void EQ(String alias,String value){
		addCondition(alias,SQLExpression.EQ,value,null);
	}

	public void addCondition(String alias,String expr ,String value,String sort){
		if(StringUtils.isNotBlank(value)){
			if(StringUtils.isNotBlank(condition))
				condition.append(" AND ");

			condition.append(" t.").append(alias);
			
			//拼装条件表达式
			if(SQLExpression.IN.equals(expr) || 
					SQLExpression.NOT_IN.equals(expr)){
				condition.append(" " + expr +" (").append(":").append(alias).append(") ");
			}else if(SQLExpression.NULL.equals(expr) || 
					SQLExpression.NOT_NULL.equals(expr)){
				condition.append(expr);
			}else{
				condition.append(" " + expr +" ").append(":").append(alias);
			}
			
			//输入值
			if(SQLExpression.LIKE.equals(expr)){
				params.put(alias, "%"+value+"%");
			}else if(SQLExpression.NULL.equals(expr) || 
					SQLExpression.NOT_NULL.equals(expr)){
			}else if(SQLExpression.IN.equals(expr) || 
					SQLExpression.NOT_IN.equals(expr)){
				params.put(alias, Lists.newArrayList(value.split(",")));
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
		storeSql.append("select t.* from (")
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
		storeSql.append("select t.* from (")
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
