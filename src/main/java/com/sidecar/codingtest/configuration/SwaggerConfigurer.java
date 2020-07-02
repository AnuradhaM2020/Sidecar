
package com.sidecar.codingtest.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sidecar.codingtest.constants.SecurityConstants;
import com.sidecar.codingtest.controller.EmployeeController;
import com.sidecar.codingtest.controller.UserController;
import com.sidecar.codingtest.filter.JwtFilter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration

@EnableSwagger2WebMvc

@ComponentScan(basePackageClasses = { EmployeeController.class, UserController.class })
public class SwaggerConfigurer {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JwtFilter jwtFilter;

	@Bean
	public Docket swaggerConfiguration() {
		LOGGER.info("inside SwaggerCinfigurer");
		final List<ResponseMessage> globalResponses = Arrays.asList(
				new ResponseMessageBuilder().code(200).message("OK").build(),
				new ResponseMessageBuilder().code(400).message("Bad Request").build(),
				new ResponseMessageBuilder().code(500).message("Internal Error").build());
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("/api/v1.*")).build().pathMapping("/")
				.genericModelSubstitutes(ResponseEntity.class).securitySchemes(Arrays.asList(apiKey()))
				.useDefaultResponseMessages(false).globalResponseMessage(RequestMethod.GET, globalResponses)
				.globalResponseMessage(RequestMethod.POST, globalResponses)
				.globalResponseMessage(RequestMethod.DELETE, globalResponses)
				.securityContexts(Collections.singletonList(securityContext()));

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Sidecar SpringBoot Project").description("Spring Boot REST API")
				.termsOfServiceUrl("http://localhost:8080").version("1.0").build();
	}

	private ApiKey apiKey() {
		return new ApiKey("jwt", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Collections.singletonList(new SecurityReference("jwt", authorizationScopes));
	}

	@Bean
	SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder().clientSecret(SecurityConstants.SECRET_KEY)
				.enableCsrfSupport(false).build();
	}

	protected void configure(HttpSecurity http) throws Exception {
  http.authorizeRequests().antMatchers(SecurityConstants.
  LOG_IN_URL).permitAll().anyRequest().authenticated()
  .and().exceptionHandling().and().sessionManagement().sessionCreationPolicy(
  SessionCreationPolicy.STATELESS) .and().csrf().disable();
  http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
 
	}
	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder().validatorUrl(null).build();
	}
}
