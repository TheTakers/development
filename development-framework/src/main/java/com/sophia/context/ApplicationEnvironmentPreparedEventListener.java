package com.sophia.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.EnumerableCompositePropertySource;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import com.alibaba.fastjson.JSONObject;
import com.sophia.web.util.ZKClient;

/**
 * Zookeeper config
 * @author zkning
 *
 */
public class ApplicationEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

	Logger logger = LoggerFactory.getLogger(getClass());

	private static final String appliction = "applicationConfigurationProperties";
	private static final String applictionConfig = "applicationConfig:";
	private static final String cloudAppliction = "cloudConfigurationProperties";

	private static final String zk_switch = "apache.zk.switch";
	private static final String zk_server = "apache.zk.server";
	private static final String zk_timeout="apache.zk.timeout";
	private static final String sophiaConfg="/development";


	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		applictionConfiguration(event);
	}

	private void applictionConfiguration(ApplicationEnvironmentPreparedEvent event){

		MutablePropertySources mutablePropertySources= event.getEnvironment().getPropertySources();

		if(mutablePropertySources.contains(appliction)){
			PropertySource<?> propertySource = mutablePropertySources.get(appliction);
			if(_switch(propertySource)){

				Map<String,Object> zookeeperConfig = zkConfig(propertySource);
				if(!zookeeperConfig.isEmpty())
					mutablePropertySources.replace(appliction,new MapPropertySource(cloudAppliction,zookeeperConfig));
			}
		}

	}

	private Boolean _switch(PropertySource<?> propertySource){

		String v = propertySource.getProperty(zk_switch) != null ? propertySource.getProperty(zk_switch).toString() : "";

		try{

			return StringUtils.isNotBlank(v) && Boolean.valueOf(v);
		}catch(Exception e){
			logger.error(zk_switch + ":",v);
			return false;
		}
	}

	private Map<String,Object> zkConfig(PropertySource<?> propertySource){

		Map<String,Object> zkConfig = new HashMap<String, Object>();
		try{
			ZkClient zkClient = ZKClient.getInstance(propertySource.getProperty(zk_server).toString(), 
									Integer.valueOf(propertySource.getProperty(zk_timeout).toString()));
			
			if(zkClient.exists(sophiaConfg)){

				JSONObject configJson=JSONObject.parseObject(zkClient.readData(sophiaConfg).toString());
				for(String jk : configJson.keySet()){
					zkConfig.put(jk, configJson.get(jk));
				}
				logger.info("获取Zookeeper配置成功:",configJson.toJSONString());
			}else{
				
				List<EnumerableCompositePropertySource> sources = (List<EnumerableCompositePropertySource>)propertySource.getSource();
			 
				
				for(EnumerableCompositePropertySource source : sources){
					
					if(source.getName().contains(applictionConfig)){
						
						String node;
						for(String key : source.getPropertyNames()){
							
							node = zkClient.create(sophiaConfg + "/"+ key,source.getProperty(key), CreateMode.PERSISTENT);
							
							//添加监听
							zkClient.subscribeDataChanges(node, new IZkDataListener() {
								
								@Override
								public void handleDataDeleted(String dataPath) throws Exception {
									logger.warn("Zookeeper配置节点已被删除");
								}
								
								@Override
								public void handleDataChange(String dataPath, Object data) throws Exception {
									logger.warn("Zookeeper配置节点已更新");
								}
							});
							
							logger.info("创建Zookeeper节点{}成功",node);
						}
						break;
					}
				}
			}
		}catch(Exception e){
			logger.error("获取Zookeeper配置异常,仍应用本地配置",e);
		}
		return zkConfig;
	}
}
