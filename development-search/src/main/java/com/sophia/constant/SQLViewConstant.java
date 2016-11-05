package com.sophia.constant;

public class SQLViewConstant {
	
	public static final String COLUMNTYPE_INT	  = "int";
	public static final String COLUMNTYPE_VARCHAR = "varchar";
	public static final String COLUMNTYPE_CLOB	  = "clob";
	public static final String COLUMNTYPE_NUMBER  = "number";
	public static final String COLUMNTYPE_DATE	  = "date";
	public static final String COLUMNTYPE_TEXT	  = "text";

	public static final Integer YES	  = 1;
	public static final Integer NO	  = 0;
	
	/**
	 * 导航按钮
	 */
	public static final Integer BUTTON_NAV	 = 1;
	/**
	 * 列表按钮
	 */
	public static final Integer BUTTON_TABLE = 0;
	
	public static final String VERSION	  = "VERSION";
	public static final String CREATE_TIME	  = "CREATETIME";
	public static final String CREATE_USER	  = "CREATEUSER";
	public static final String LAST_UPDATE_TIME	  = "LASTUPDATETIME";
	public static final String LAST_UPDATE_USER	  = "LASTUPDATEUSER";
	
	/**
	 * 所有root节点统一上级为0
	 */
	public static final String TREE_ROOT = "0";
	
	/**
	 * SQL IN 空默认值
	 * 000000
	 */
	public static final String IN_NONE_CODE = "000000";
}
