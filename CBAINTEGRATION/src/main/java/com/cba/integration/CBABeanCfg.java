package com.cba.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CBABeanCfg {
   @Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
