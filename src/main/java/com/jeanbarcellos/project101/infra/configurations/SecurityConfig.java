package com.jeanbarcellos.project101.infra.configurations;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jeanbarcellos.project101.presentation.web.filters.TokenAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Value("${app-config.authorization.endpoints-public}")
    private String[] endpointsPublic;

    @Value("${app-config.cors.allowedMethods}")
    private String[] corsAllowedMethods;

    @Value("${app-config.cors.allowedOrigins}")
    private String[] corsAllowedOrigins;

    @Value("${app-config.cors.allowedHeaders}")
    private String[] corsAllowedHeaders;

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuration segurança HTTP
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Política CORS
                .cors(withDefaults())

                // Política CSRF
                .csrf(CsrfConfigurer::disable)

                // Gerenciamento de sessão
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Autorizações de acesso
                .authorizeHttpRequests(authorizeConfig -> {
                    // Acesso público
                    authorizeConfig.requestMatchers(this.endpointsPublic).permitAll();
                    // Acesso somente com autenticação
                    authorizeConfig.anyRequest().authenticated();
                })

                // Tratamento de exceções
                .exceptionHandling(handling -> handling.authenticationEntryPoint(this.authenticationEntryPoint))

                // Filtros
                .addFilterBefore(this.tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Configuração do CORs
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        config.setAllowedMethods(Arrays.asList(this.corsAllowedMethods));
        config.setAllowedHeaders(Arrays.asList(this.corsAllowedHeaders));
        config.setAllowedOrigins(Arrays.asList(this.corsAllowedOrigins));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }


}
