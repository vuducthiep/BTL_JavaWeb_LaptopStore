//package com.example.ProjectLaptopStore.Config;
//
//import com.example.ProjectLaptopStore.Entity.Enum.User_Enum;
//import jakarta.security.auth.message.callback.SecretKeyCallback;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//import javax.crypto.spec.SecretKeySpec;
//import javax.swing.*;
//
//@Configuration
//@EnableAutoConfiguration
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests(request ->
//                request
//                        .requestMatchers(HttpMethod.POST, "/register","login","/token-valid").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/register","login").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/users/").hasAnyAuthority("SCOPE_admin")
//                        .anyRequest().authenticated()
//        );
//
//
//        httpSecurity.oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer ->
//                        httpSecurityOAuth2ResourceServerConfigurer.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
//                );
//        httpSecurity.csrf(AbstractHttpConfigurer::disable);
//        return httpSecurity.build();
//    }
//
//    @Value("${jwt.signerKey}")
//    private String signerKey;
//
//    @Bean
//    JwtDecoder jwtDecoder(){
//        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HmacSHA512");
//        return NimbusJwtDecoder
//                .withSecretKey(secretKeySpec)
//                .macAlgorithm(MacAlgorithm.HS512)
//                .build();
//    }
//}
