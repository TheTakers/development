package com.sophia.web.util;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author zkning
 *
 */
public class ZKClient {

	Logger logger = LoggerFactory.getLogger(getClass());

	private static ZkClient zkClient=null;
	
	public static ZkClient getInstance(String server,Integer timeout){
		if(zkClient==null){
			synchronized(ZKClient.class){
				if(zkClient==null){
					zkClient=new ZkClient(server,timeout);
				}
			}
		}
		return zkClient;
	}
}
