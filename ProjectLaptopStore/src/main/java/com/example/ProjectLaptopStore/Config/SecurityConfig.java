package com.example.ProjectLaptopStore.Config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.swing.*;

@Configuration
@EnableAutoConfiguration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.GET, "/users/").hasAnyRole("Admin")
                        .requestMatchers(HttpMethod.POST, "/register","login").permitAll()
                        .anyRequest().authenticated()
        );



        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
}
