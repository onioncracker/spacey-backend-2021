package com.heroku.spacey.config;

import com.heroku.spacey.utils.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration cors = new CorsConfiguration();
        cors.applyPermitDefaultValues();
        cors.addAllowedMethod(HttpMethod.DELETE);
        cors.addAllowedMethod(HttpMethod.PUT);
        cors.addAllowedMethod(HttpMethod.POST);
        cors.addAllowedMethod(HttpMethod.GET);
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/register", "/login", "/recover_password",
                        "/v3/api-docs/**", "/v3/api-docs.yaml",
                        "/swagger-resources/**", "/swagger-ui.html",
                        "/swagger-ui/**", "/webjars/**", "/api/category/**",
                        "/api/product/**", "/api/material/**", "/api/v1/**", "/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
