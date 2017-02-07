package com.sophia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zkning
 */
@SpringBootApplication
@EnableScheduling
public class AdminApplication{
	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AdminApplication.class);
		Application.getInstance(application).initialize();
		application.run(args);
	}
}
