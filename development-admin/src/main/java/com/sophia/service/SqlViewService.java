package com.sophia.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.sophia.domain.Pager;
import com.sophia.domain.SqlView;
import com.sophia.domain.SqlViewField;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SqlViewRepository;
import com.sophia.vo.QueryParam;
import com.sophia.vo.SqlViewParam;
import com.sophia.vo.SqlViewQueryParam;

public interface SqlViewService extends JpaRepository<SqlViewRepository>{
	
	 String save(SqlViewParam sqlViewRequest);
	
	 SqlView findById(String id);
	
	 Pager<Map<String,Object>> list(QueryParam queryRequest);
	
	 List<SqlViewField> showFullColumnsBySql(String sql);
	
	/**
	 * 根据编号获取视图
	 * @param code
	 * @return
	 */
	 SqlView getSqlViewByCode(String code);
	
	
	/**
	 * 根据sqlview编号获取最新SQLview数据和当前sqlview对应的sqldefine的数据
	 * @param code
	 * @param row
	 * @return
	 */
	 Map<String,Object> getSqlViewAndSqlDefineRowDataByCode(String code,JSONObject row);
	
	/**
	 * 插入数据
	 * @param code 视图编号
	 * @param param 表单数据
	 */
	 void persistentByCode(String code,JSONObject row);
	
	/**
	 * 修改数据
	 * @param code 视图编号
	 * @param param 表单数据
	 */
	 void modifyByCode(String code,JSONObject row);
	
	/**
	 * 删除数据
	 * @param code 视图编号
	 * @param param 表单数据
	 */
	 void deleteByCode(String code,JSONObject row);
	
	/**
	 * 根据SQLID和数据ID获取某条数据
	 * @param sqlId
	 * @param row 行数据
	 */
	 Map<String,Object>  getDataBySqlId(String sqlId,JSONObject row);
	
	/**
	 * 查询视图列表
	 * @param code
	 * @param queryRequest
	 * @return
	 */
	 Pager<Map<String, Object>> findSqlViewGrid(String code,SqlViewQueryParam queryRequest);
	
	/**
	 * 根据视图编码获取字段
	 * @param viewCode
	 * @return
	 */
	 List<SqlViewField> findFieldListByViewCode(String viewCode);
}
