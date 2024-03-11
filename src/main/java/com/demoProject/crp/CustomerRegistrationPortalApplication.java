package com.demoProject.crp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.example"}, excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com/example/target/.*"))

public class CustomerRegistrationPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerRegistrationPortalApplication.class, args);
	}

}
