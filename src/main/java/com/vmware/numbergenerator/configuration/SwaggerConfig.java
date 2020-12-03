
package com.vmware.numbergenerator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger configuration and API info
 */
@Configuration
public class SwaggerConfig {
	private ApiInfo apiMetaData() {
		return new ApiInfoBuilder().title("Number Generator Operations")
		        .description("REST APIs for number sequence generation")
		        .contact(new Contact("Shweta Bahore", "", "Shweta.Bahore12@gmail.com"))
		        .version("1.0.0")
		        .build();
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
		        .apis(RequestHandlerSelectors.basePackage("com.vmware.numbergenerator"))
		        .paths(PathSelectors.any())
		        .build()
		        .apiInfo(apiMetaData());
	}
}
