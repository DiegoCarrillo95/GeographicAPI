package com.diego.geographicapi.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
	@Bean
	public Docket api() {                
	    return new Docket(DocumentationType.SWAGGER_2)  
	            .select()                                  
	            .apis(RequestHandlerSelectors.basePackage("com.diego.geographicapi.controller"))              
	            .paths(PathSelectors.any())                          
	            .build()
	            .apiInfo(apiInfo()); 
	}
	 
	private ApiInfo apiInfo() {
	     return new ApiInfo(
	       "GeographicAPI", 
	       "Manages countries, states, and cities data", 
	       "1", 
	       "", 
	       new Contact("Diego Carrillo", "https://github.com/DiegoCarrillo95", "diegoac95@gmail.com"), 
	       "", "", Collections.emptyList());
	}
}