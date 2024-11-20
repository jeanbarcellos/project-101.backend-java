package com.jeanbarcellos.project101.infra.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jeanbarcellos.core.validation.Validator;

@Configuration
public class AppConfig {

    @Bean
    Validator validator() {
        return new Validator();
    }
}
