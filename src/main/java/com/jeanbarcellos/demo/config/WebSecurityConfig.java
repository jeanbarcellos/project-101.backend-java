package com.jeanbarcellos.demo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jeanbarcellos.demo.application.services.JwtService;
import com.jeanbarcellos.demo.web.filters.FilterChainExceptionHandler;
import com.jeanbarcellos.demo.web.filters.TokenAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    // @Value("${app-config.authorization.endpoints-public}")
    private static final String[] ENDPOINTS_PUBLIC = {
            "/auth/**",
            "/api/status",

            // Open Api / Swaggger
            "/swagger/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",

            "/actuator/**"
    };

    @Autowired
    private SecurityAuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private FilterChainExceptionHandler filterChainExceptionHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth)
            throws Exception {
        return auth.getAuthenticationManager();
    }

    // Configurar Autenticação
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(authenticationService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    // Configuration segurança HTTP
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Política CORS
                .cors()
                .and()

                // Política CSRF
                .csrf().disable()

                // Manipulador de autenticação
                // .authenticationManager(authenticationManager)

                // gerenciamento de sessão;
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // Autorizações de acesso
                .authorizeRequests()
                // Acesso público
                .antMatchers(ENDPOINTS_PUBLIC).permitAll()
                // Acesso somente com autenticação
                .anyRequest().authenticated()
                .and()

                // Tratamento de exceções
                .exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint())
                .and()

                // Filtros
                .addFilterBefore(filterChainExceptionHandler,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenAuthenticationFilter(jwtService, authenticationService),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Configuração do CORs
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        // configuration.addAllowedOrigin("*");
        // configuration.addAllowedHeader("*");
        // configuration.addAllowedMethod("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new SecurityAuthenticationEntryPoint();
    }

}
