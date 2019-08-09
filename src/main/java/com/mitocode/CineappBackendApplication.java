package com.mitocode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CineappBackendApplication {
//extends SpringBootServletInitializer
	public static void main(String[] args) {
		SpringApplication.run(CineappBackendApplication.class, args);
	}
	
//	 @Override
//	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//	        return application.sources(CineappBackendApplication.class);
//	    }

}
