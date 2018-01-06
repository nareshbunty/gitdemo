package com.cba.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={
		"com.cba.controller",
		"com.cba.security",
		"com.cba.processing",
		"com.cba.dao",
		"com.cba.integration"
})
public class CbaControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CbaControllerApplication.class, args);
	}
}
