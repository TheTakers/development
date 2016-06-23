package com.sophia.service.impl;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sophia.service.ZKClientService;

@Service
public class ZKServiceClientImpl implements ZKClientService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	protected ZkClient zkClient = null;
	
	
	public void config(String zkServers,Integer connectionTimeout){
		
		this.zkClient = new ZkClient(zkServers, connectionTimeout);
		
	}
}
