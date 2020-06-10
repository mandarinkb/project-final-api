package com.projectfinalapi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectFinalApiApplication {

	//private static final Logger LOGGER = LogManager.getLogger(ProjectFinalApiApplication.class.getName());
	public static void main(String[] args) {
		SpringApplication.run(ProjectFinalApiApplication.class, args);
/*		
		String str1 = "hello";
		String str2 = "world";
        LOGGER.info("Info Message {} and {}",str1,str2);
        LOGGER.error("Error Message Logged !!!");
*/        
	}

}
