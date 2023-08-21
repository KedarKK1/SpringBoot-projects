package com.example.myWebFluxSSEApp;

//import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;

import org.springframework.http.HttpStatus;

@SpringBootApplication
public class Application implements ErrorPageRegistrar {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		 // Configure a custom error page for all unmapped URLs
		ErrorPage errorPage = new ErrorPage(HttpStatus.NOT_FOUND, "/customErrorPage");
		registry.addErrorPages(errorPage);
	}

}
