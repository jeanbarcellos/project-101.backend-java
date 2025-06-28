package com.jeanbarcellos.project101.infra.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jeanbarcellos.core.constants.ApiConstants;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfig {

    @Value("${app-config.name}")
    private String appName;

    @Value("${app-config.description}")
    private String appDescription;

    @Value("${app-config.version}")
    private String appVersion;

    @Bean
    OpenAPI openAPI() {

        var config = new OpenAPI();

        var info = new Info()
                .title(this.appName)
                .description(this.appDescription)
                .version(this.appVersion);

        info.contact(new Contact()
                .name("Jean Barcellos")
                .url("www.jeanbarcellos.com.br"));
        config.info(info);

        config.externalDocs(new ExternalDocumentation()
                .description("Project 101 Documentation")
                .url("https://github.com/jeanbarcellos/project-101.backend-java"));

        config.components(new Components()
                .addSecuritySchemes(ApiConstants.BEARER_KEY,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .name("Autenticação")
                                .description("Informe o Token JWT")));

        return config;
    }

}
