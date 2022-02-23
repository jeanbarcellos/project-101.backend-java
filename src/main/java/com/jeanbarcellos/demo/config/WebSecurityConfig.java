package com.jeanbarcellos.demo.config;

import com.jeanbarcellos.demo.application.services.JwtService;
import com.jeanbarcellos.demo.config.filters.FilterChainExceptionHandler;
import com.jeanbarcellos.demo.config.filters.TokenAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] ENDPOINTS_PUBLIC = {
            "/auth/**",
            "/utils/**"
    };

    @Autowired
    private AuthenticationSecurityService authenticationService;

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
                .authorizeRequests()
                .antMatchers(ENDPOINTS_PUBLIC).permitAll() // Permitir acesso apenas de /auth
                .anyRequest().authenticated() // Demais requests devem estar autenticados
                .and()
                .csrf().disable() // Desabilita a política CSRF
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // gerenciamento de sessão;
                .and()
                .addFilterBefore(filterChainExceptionHandler,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenAuthenticationFilter(jwtService, authenticationService),
                        UsernamePasswordAuthenticationFilter.class);
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(ENDPOINTS_PUBLIC);
    }
}
