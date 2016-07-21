package com.sophia.web.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * GUID 
 * @author zkning
 */
public class GUID {
	
	/**
	 * 生成32位ID
	 * @return
	 */
	public static String nextId(){
		return  UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 生成编码
	 * @return
	 */
	public static synchronized String createCode(){
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(new Date());
	}
	
	public static void main(String[] args) {
		System.out.println(GUID.nextId());
	}
}
