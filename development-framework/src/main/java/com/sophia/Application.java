
package com.sophia;

import org.springframework.boot.SpringApplication;

import com.sophia.context.ApplicationEnvironmentPreparedEventListener;

public class Application {
	private SpringApplication application;

	public Application(SpringApplication application) {
		this.application = application;
	}
	
	public void initialize(){
		this.application.addListeners(new ApplicationEnvironmentPreparedEventListener());
	}
	
	public static Application getInstance(SpringApplication application){
		return new Application(application);
	}
}
