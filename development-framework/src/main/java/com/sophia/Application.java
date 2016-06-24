
package com.sophia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.sophia.context.ApplicationEnvironmentPreparedEventListener;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
    	SpringApplication application = new SpringApplication(Application.class);
    	application.addListeners(new ApplicationEnvironmentPreparedEventListener());
    	application.run(args);
    }
}
