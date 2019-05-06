package com.greenfurniture.onlineorder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.greenfurniture.onlineorder.aspect.LoggingAspect;

@Configuration
public class AspectLoggingConfig {

	@Bean
	public LoggingAspect loggingAspect(){
		return new LoggingAspect();
	}
}
