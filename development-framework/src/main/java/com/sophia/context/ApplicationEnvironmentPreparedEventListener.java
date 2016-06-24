package com.sophia.context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
	
	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		applictionConfiguration(event);
	}
	
	private void applictionConfiguration(ApplicationEnvironmentPreparedEvent event){
		
		ConfigurableEnvironment envi = event.getEnvironment();
		
        MutablePropertySources muPropertySources= envi.getPropertySources();
        
        if (muPropertySources != null) {
            Iterator<PropertySource<?>> iterator = muPropertySources.iterator();
            while (iterator.hasNext()) {
                PropertySource<?> propertySource = iterator.next();
                
                if(appliction.equals(propertySource.getName())){

                	System.out.println(propertySource.getName()+"/"+propertySource.containsProperty("server.port")+"/"+propertySource.getProperty("server.port"));
                	System.out.println(propertySource.getName()+"/"+propertySource.containsProperty("spring.master.url")+"/"+propertySource.getProperty("spring.master.url"));
                	Map<String, Object> map = new HashMap<>();  
                	map.put("spring.master.url","jdbc:mysql://192.168.1.81:3306/db_eam_mirror?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");  
                	
                	propertySource = new MapPropertySource("cloudConfigurationProperties",map);
                	muPropertySources.replace("applicationConfigurationProperties", propertySource);
                	
                	 
                	System.out.println(propertySource.getName()+"/"+propertySource.containsProperty("spring.master.url")+"/"+propertySource.getProperty("spring.master.url"));
                	System.out.println(propertySource.getName()+"/"+propertySource.containsProperty("encoding")+"/"+propertySource.getProperty("encoding"));
                }
                
            }
        }
	
	}

}
