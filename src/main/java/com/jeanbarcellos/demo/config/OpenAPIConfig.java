package com.jeanbarcellos.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jeanbarcellos.demo.config.constants.APIConstants;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {

        var config = new OpenAPI();

        config.info(new Info()
                .title("Demo API")
                .description("App demo para explorar recursos do Spring")
                .version("v0.0.1")
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://springdoc.org")));

        config.externalDocs(new ExternalDocumentation()
                .description("Project 101 Documentation")
                .url("https://github.com/jeanbarcellos/project-101.back.java"));

        config.components(new Components()
                .addSecuritySchemes(APIConstants.BEARER_KEY,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .name("Autenticação")
                                .description("Informe o Token JWT")));

        return config;
    }

}
