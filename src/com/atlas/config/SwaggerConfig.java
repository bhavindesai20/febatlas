package com.atlas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 
public class SwaggerConfig {
	
	@Bean
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2).
				select().apis(RequestHandlerSelectors.any()).
				paths(PathSelectors.any()).build().apiInfo(apiInfo()).pathMapping("/api");
	}
	
	private ApiInfo apiInfo(){
		ApiInfo apiInfo =new ApiInfo("Spring-Rest-Api-For-Movies", 
					"This is Spring Rest Api for Movie App", "0.0.1", "T&C - Public", "me@gmail.com", 
					"MIT", "https://github.com/bhavindesai20/febatlas");
		return apiInfo;
	}
}
