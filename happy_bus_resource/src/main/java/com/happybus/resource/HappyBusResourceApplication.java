package com.happybus.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.happybus"})
public class HappyBusResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappyBusResourceApplication.class, args);
	}
}
