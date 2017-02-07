package com.sophia.utils;

import java.sql.Types;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.sophia.constant.SQLViewConstant;
import com.sophia.vo.DataTypeResult;

/**
 * 简陋的工具类
 * @author zkning
 *
 */
public class SimpleUtils {
	
	/**
	 * 判断是否true
	 * @param 1：TRUE,0: FALSE
	 * @return
	 */
	public static Boolean isTrue(Integer value){
		return SQLViewConstant.YES == value;
	}
	
	/**
	 * 下划线转驼峰
	 * @param line
	 * @param smallCamel 首字母是否小写
	 * @return
	 */
	public static String underline2Camel(String line,boolean smallCamel){
        if(StringUtils.isEmpty(line)){
        	return "";
        }
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(smallCamel&&matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
            int index=word.lastIndexOf('_');
            if(index>0){
                sb.append(word.substring(1, index).toLowerCase());
            }else{
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
	
	/**
	 * 获取字段类型
	 * @param dataType
	 * @return String
	 */
	public static String getDataType(String dataType) {
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
	private static boolean isSpecType(String dbType, String dataType) {
		String[] aryType = dataType.split(",");
		for (String str : aryType) {
			if (dbType.equals(str) || dbType.indexOf(str) > -1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * MySQL类型转Java类型
	 * @return
	 */
	public static DataTypeResult mysqlTypeConvertJavaType(Integer idx){
		DataTypeResult dataTypeVo = new DataTypeResult();
		switch (idx) {
		case Types.VARCHAR:
		case Types.CHAR:
		case Types.LONGVARCHAR:
			dataTypeVo.setType("String");
			return dataTypeVo;
		case Types.INTEGER:
		case Types.TINYINT:
		case Types.SMALLINT:
			dataTypeVo.setType("Integer");
			return dataTypeVo;
		case Types.DECIMAL:
			dataTypeVo.setType("BigDecimal");
			dataTypeVo.setTypePackage("import java.math.BigDecimal;");
			return dataTypeVo;
		case Types.BIT:
			dataTypeVo.setType("Long");
			return dataTypeVo;
		case Types.BIGINT:
			dataTypeVo.setType("Boolean");
			return dataTypeVo;
		case Types.FLOAT:
			dataTypeVo.setType("Float");
			return dataTypeVo;
		case Types.DOUBLE:
			dataTypeVo.setType("Double");
			return dataTypeVo;
		case Types.DATE:
			dataTypeVo.setType("Date");
			dataTypeVo.setTypePackage("import java.util.Date;");
			return dataTypeVo;
		case Types.TIME:
			dataTypeVo.setType("Time");
			dataTypeVo.setTypePackage("import java.sql.Time;");
			return dataTypeVo;
		case Types.TIMESTAMP:
			dataTypeVo.setType("Timestamp");
			dataTypeVo.setTypePackage("import java.sql.Timestamp;");
			return dataTypeVo; 
		}
		return null;
	}
}
