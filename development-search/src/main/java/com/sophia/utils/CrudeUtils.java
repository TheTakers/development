package com.sophia.utils;

import com.sophia.constant.SQLViewConstant;

/**
 * 简陋的工具类
 * @author zkning
 *
 */
public class CrudeUtils {
	
	/**
	 * 判断是否true
	 * @param 1：TRUE,0: FALSE
	 * @return
	 */
	public static Boolean isTrue(Integer value){
		return SQLViewConstant.YES == value;
	}
}
