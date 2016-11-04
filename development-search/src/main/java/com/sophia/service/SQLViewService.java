package com.sophia.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.sophia.domain.SQLView;
import com.sophia.domain.SQLViewField;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLViewRepository;
import com.sophia.request.QueryRequest;
import com.sophia.request.SQLViewRequest;
import com.sophia.response.GridResponse;

public interface SQLViewService extends JpaRepository<SQLViewRepository>{
	
	public String save(SQLViewRequest sqlViewRequest);
	
	public SQLView findById(String id);
	
	public GridResponse<Map<String,Object>> list(QueryRequest queryRequest);
	
	public List<SQLViewField> showFullColumnsBySql(String sql) throws Exception;
	
	/**
	 * 根据编号获取视图
	 * @param code
	 * @return
	 */
	public SQLView getSqlViewByCode(String code);
	
	
	/**
	 * 根据sqlview编号获取最新SQLview数据和当前sqlview对应的sqldefine的数据
	 * @param code
	 * @param row
	 * @return
	 */
	public Map<String,Object> getSqlViewAndSqlDefineRowDataByCode(String code,JSONObject row);
	
	/**
	 * 插入数据
	 * @param code 视图编号
	 * @param param 表单数据
	 */
	public void persistentByCode(String code,JSONObject row);
	
	/**
	 * 修改数据
	 * @param code 视图编号
	 * @param param 表单数据
	 */
	public void modifyByCode(String code,JSONObject row);
	
	/**
	 * 删除数据
	 * @param code 视图编号
	 * @param param 表单数据
	 */
	public void deleteByCode(String code,JSONObject row);
	
	/**
	 * 根据SQLID和数据ID获取某条数据
	 * @param sqlId
	 * @param row 行数据
	 */
	public Map<String,Object>  getDataBySqlId(String sqlId,JSONObject row);
	
}
