package com.jeanbarcellos.project101.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.jeanbarcellos.project101.application.services.JwtService;
import com.jeanbarcellos.project101.web.filters.FilterChainExceptionHandler;
import com.jeanbarcellos.project101.web.filters.TokenAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Value("${app-config.authorization.endpoints-public}")
    private String[] endpointsPublic;

    private List<String> corsAllowedMethods = Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS");

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
                .userDetailsService(this.authenticationService)
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
                .antMatchers(this.endpointsPublic).permitAll()
                // Acesso somente com autenticação
                .anyRequest().authenticated()
                .and()

                // Tratamento de exceções
                .exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint())
                .and()

                // Filtros
                .addFilterBefore(this.filterChainExceptionHandler,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenAuthenticationFilter(this.jwtService, this.authenticationService),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Configuração do CORs
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        config.setAllowedMethods(this.corsAllowedMethods);
        // config.addAllowedOrigin("*");
        // config.addAllowedHeader("*");
        // config.addAllowedMethod("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new SecurityAuthenticationEntryPoint();
    }

}
