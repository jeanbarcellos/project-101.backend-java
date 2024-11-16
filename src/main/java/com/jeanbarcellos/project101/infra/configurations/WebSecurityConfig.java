package com.jeanbarcellos.project101.infra.configurations;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

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
import com.jeanbarcellos.project101.presentation.web.filters.FilterChainExceptionHandler;
import com.jeanbarcellos.project101.presentation.web.filters.TokenAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Value("${app-config.authorization.endpoints-public}")
    private String[] endpointsPublic;

    @Value("${app-config.cors.allowedMethods}")
    private String[] corsAllowedMethods;

    @Value("${app-config.cors.allowedOrigins}")
    private String[] corsAllowedOrigins;

    @Value("${app-config.cors.allowedHeaders}")
    private String[] corsAllowedHeaders;

    @Autowired
    private SecurityAuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private FilterChainExceptionHandler filterChainExceptionHandler;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration auth)
            throws Exception {
        return auth.getAuthenticationManager();
    }

    // Configurar Autenticação
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(this.authenticationService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    // Configuration segurança HTTP
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Política CORS
                .cors(withDefaults())

                // Política CSRF
                .csrf(csrf -> csrf.disable())

                // Manipulador de autenticação
                // .authenticationManager(authenticationManager)

                // Gerenciamento de sessão
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Autorizações de acesso
                .authorizeRequests(requests -> requests
                        // Acesso público
                        .antMatchers(this.endpointsPublic).permitAll()
                        // Acesso somente com autenticação
                        .anyRequest().authenticated())

                // Tratamento de exceções
                .exceptionHandling(handling -> handling.authenticationEntryPoint(this.authenticationEntryPoint()))

                // Filtros
                .addFilterBefore(this.filterChainExceptionHandler,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenAuthenticationFilter(this.jwtService, this.authenticationService),
                        UsernamePasswordAuthenticationFilter.class);

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

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return new SecurityAuthenticationEntryPoint();
    }

}
