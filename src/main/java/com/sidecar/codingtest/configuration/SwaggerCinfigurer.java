package com.sidecar.codingtest.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerCinfigurer {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Bean
	public Docket swaggerConfiguration() {
		LOGGER.info("inside SwaggerCinfigurer");
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.sidecar.codingtest"))
				.paths(PathSelectors.ant("*/api/*"))				
				.build()
				.pathMapping("/").genericModelSubstitutes(ResponseEntity.class)
				.securitySchemes(Arrays.asList(apiKey()))
				.securityContexts(Collections.singletonList(securityContext()));
				
	}
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Sidecar SpringBoot Project")
                .description("Spring Boot REST Api")
                .termsOfServiceUrl("localhost")
                .version("1.0")
                .build();
    }
	private ApiKey apiKey() {
		return new ApiKey("jwtToken", "Authorization", "header");
	}
	private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("*/api/*"))
            .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
            = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(
            new SecurityReference("JWT", authorizationScopes));
    }
}
