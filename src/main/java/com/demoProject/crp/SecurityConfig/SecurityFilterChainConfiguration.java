package com.demoProject.crp.SecurityConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityFilterChainConfiguration {

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChainConfig(HttpSecurity httpSecurity)throws Exception{
        //Disable CORS
        httpSecurity.cors(corsConfig -> corsConfig.disable());

        //Disable CSRF
        httpSecurity.csrf(csrfConfig -> csrfConfig.disable());

        httpSecurity.authorizeHttpRequests(
                requestMatcher -> requestMatcher
                        .requestMatchers("/admin/sign-up", "/admin/sign-in", "/customer/sign-up","/customer/sign-in").permitAll()
                        .requestMatchers("/admin/show-all-customer", "/admin/add-admin").hasAnyAuthority("ADMIN")
                        .requestMatchers("/customer/all-todos", "/customer/todo-by-id/{id}").hasAnyAuthority("CUSTOMER")
                        .anyRequest().authenticated()
        );
        //Authentication Entry Point
        httpSecurity.exceptionHandling(
                exceptionConfig -> exceptionConfig.authenticationEntryPoint((authenticationEntryPoint))
        );

        //Set session as stateless
        httpSecurity.sessionManagement(
                sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        //add jwt authentication filter

        httpSecurity.addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
