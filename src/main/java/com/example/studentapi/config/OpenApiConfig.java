package com.example.studentapi.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI studentApi() {
		return new OpenAPI()
				.info(new Info()
						.title("Student Management API")
						.description("Simple Student Management REST API with Spring Boot 3")
						.version("1.0.0")
						.contact(new Contact().name("Student API").email("noreply@example.com")))
				.externalDocs(new ExternalDocumentation()
						.description("Swagger UI")
						.url("swagger-ui.html"));
	}
}


