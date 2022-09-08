package com.jeanbarcellos.demo.config;

import java.util.Arrays;

import com.jeanbarcellos.demo.application.services.JwtService;
import com.jeanbarcellos.demo.config.filters.FilterChainExceptionHandler;
import com.jeanbarcellos.demo.config.filters.TokenAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // @Value("${app-config.authorization.endpoints-public}")
    private static final String[] ENDPOINTS_PUBLIC = {
            "/auth/**",
            "/api/status",

            // Open Api / Swaggger
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml"
    };

    @Autowired
    private SecurityAuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private FilterChainExceptionHandler filterChainExceptionHandler;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // Configurations for authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // Configuration for authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Pulítica CORS
                .cors()
                .and()

                // Política CSRF
                .csrf().disable()

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
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()

                // Filtros
                .addFilterBefore(filterChainExceptionHandler, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenAuthenticationFilter(jwtService, authenticationService),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
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
