package com.sophia.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sophia.domain.SQLDefine;
import com.sophia.exception.ServiceException;
import com.sophia.service.SQLDefineService;
import com.sophia.vo.DefSelectParamDto;
import com.sophia.vo.SelectParamDto;

@Service
public class SearchServiceImpl {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired SQLDefineService sqlDefineService;

	public List<Map<String, Object>> find(String sqlId,Map<String,Object> paramMap){

		if(StringUtils.isBlank(sqlId)){
			throw new ServiceException("SQLId不能为空");
		}

		SQLDefine sqlDefine = sqlDefineService.getRepository().findBySqlId(sqlId);

		if(sqlDefine == null){
			throw new ServiceException("SQLId未定义");
		}

		DefSelectParamDto defSelectParam = null;	
		if(StringUtils.isNotBlank(sqlDefine.getDefSelectParams())){ 

			defSelectParam = JSON.toJavaObject((JSON)JSON.toJSON(sqlDefine.getDefSelectParams()), DefSelectParamDto.class);

		}

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			conn = getConnection(sqlDefine.getDatasource());

			st = conn.prepareStatement(sqlDefine.getSelectSql());

			if(defSelectParam != null){
				for(SelectParamDto param : defSelectParam.getDefSelectParam()){

					st.setObject(param.getIndex(), param.getValue());
				}
			}

			rs = st.executeQuery();
		} catch (SQLException e) {

			logger.error("{}查询异常",sqlId);
			throw new ServiceException("查询异常");
		}finally {
			release(conn, st, rs);
		}

		return null;
	}

	/**
	 * 解析结果集
	 * @param rs
	 * @throws SQLException 
	 */
	private List<Map<String,Object>> parseResultSet(ResultSetMetaData metaData,ResultSet rs) throws SQLException{

		List<Map<String, Object>> datas = new ArrayList<>();
		Map<String, Object>  data = new HashMap<String, Object>();

		while (rs.next()) {

			data = new HashMap<String, Object>();

			for (int i = 1; i < metaData.getColumnCount(); i++) {
				data.put(metaData.getColumnLabel(i), rs.getObject(metaData.getColumnLabel(i)));
			}
			datas.add(data);
		}
		return datas;
	}

	private void setValue(Map<String,Object> paramMap){

	}

	private Connection getConnection(String dataSource){

		return null;
	}

	private void release(Connection conn,Statement st,ResultSet rs){

		try{
			// 关闭结果集
			if (rs != null) {
				rs.close(); 
			}

			// 关闭Statement
			if (st != null) {
				st.close(); 
			}

			// 关闭连接
			if (conn != null) {
				conn.close(); 
			}
		}catch(SQLException ex){
			logger.error("资源释放异常{}",ex);
		}
	}
}
