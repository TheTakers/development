
package com.sophia;

import org.springframework.boot.SpringApplication;

/**
 * @author zkning
 */
public class Application {
	private SpringApplication application;

	public Application(SpringApplication application) {
		this.application = application;
	}
	
	public void initialize(){
		//TODO..
	}
	
	public static Application getInstance(SpringApplication application){
		return new Application(application);
	}
}
