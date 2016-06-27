package com.sophia.web.util;
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
	
	public static void main(String[] args) {
		System.out.println(GUID.nextId());
	}
}
