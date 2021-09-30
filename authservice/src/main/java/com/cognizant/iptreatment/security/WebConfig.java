package com.cognizant.iptreatment.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		LOGGER.info("Start addCorsMappings");
		LOGGER.info("End addCorsMappings");
		registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
	}

}
