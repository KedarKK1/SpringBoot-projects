package com.example.apigateway;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;

@Configuration
public class SpringCloudConfig extends 
org.springframework.web.cors.CorsConfiguration {
	@Bean
	public CorsWebFilter corsWebFilter() {
	  final UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
	  final CorsConfiguration config=new CorsConfiguration();
//	  config.setAllowCredentials(true);
//	  config.addAllowedHeader("*");
//	  config.addAllowedOriginPattern("*");
//	  config.addAllowedMethod("OPTIONS");
//	  config.addAllowedMethod("POST");
//	  config.addAllowedMethod("GET");
//	  config.addAllowedMethod("PUT");
//	  config.addAllowedMethod("DELETE");
//	  source.registerCorsConfiguration("/**", config);
//	  return new CorsFilter(source);
	  config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
	  config.setMaxAge(3600L);
	  config.setAllowedMethods(Arrays.asList("GET", "POST","PUT", "DELETE"));
      config.addAllowedHeader("Content-Type");
      source.registerCorsConfiguration("/**", config);
      return new CorsWebFilter(source);


	}

}
