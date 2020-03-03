package com.syncgoogle.springoauth2project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringOauth2ProjectApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SpringOauth2ProjectApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringOauth2ProjectApplication.class);
	}

}
