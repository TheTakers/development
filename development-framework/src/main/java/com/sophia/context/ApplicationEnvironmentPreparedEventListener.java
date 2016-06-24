package com.sophia.context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

public class ApplicationEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String appliction = "applicationConfigurationProperties";
	
	private static final String _switch = "cloud.zk.switch";
	
	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		applictionConfiguration(event);
	}
	
	private void applictionConfiguration(ApplicationEnvironmentPreparedEvent event){
		
		ConfigurableEnvironment envi = event.getEnvironment();
		
        MutablePropertySources mutablePropertySources= envi.getPropertySources();
        
        if (mutablePropertySources != null) {
            Iterator<PropertySource<?>> iterator = mutablePropertySources.iterator();
            while (iterator.hasNext()) {
                PropertySource<?> propertySource = iterator.next();
                
                if(appliction.equals(propertySource.getName())){
                
                	if(_switch(propertySource)){
                		
                		Map<String, Object> map = new HashMap<>();  
                    	map.put("spring.master.url","jdbc:mysql://192.168.1.81:3306/db_eam_mirror?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");  
                    	propertySource = new MapPropertySource("cloudConfigurationProperties",map);
                    	
                    	
                    	//替换掉原来的配置
                    	mutablePropertySources.replace("applicationConfigurationProperties", propertySource);
                	}
                }
            }
        }
	
	}
	
	private Boolean _switch(PropertySource<?> propertySource){
		
    	String v = propertySource.getProperty(_switch) != null ? propertySource.getProperty(_switch).toString() : "";
    	
    	try{
    		
    		return StringUtils.isNotBlank(v) && Boolean.valueOf(v);
    	}catch(Exception e){
    		logger.error(_switch + ":",v);
    		return false;
    	}
	}

}
