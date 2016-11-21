package com.sophia.utils;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ConditionUtil {
	
	private static final String VALUE  = "value";
	private static final String NAME  = "name";
	private static final String COLUMN  = "column";
	private static final String EXP  = "exp";
	
	private static final String AND  = " AND ";
	public static String parse(String condition){
		
		StringBuffer condBuff = new StringBuffer();
		
		JSONArray array = JSONArray.parseArray(condition);
		
		JSONObject cond;
		for(Object idx : array){
			cond = (JSONObject)idx;
			
			if(StringUtils.isBlank(cond.getString(VALUE))){
				condBuff.append( COLUMN )
						.append( EXP )
						.append( VALUE );
				
					condBuff.append(AND);
			}
		}
		return condBuff.deleteCharAt(condBuff.lastIndexOf(AND)).toString();
	}
}
