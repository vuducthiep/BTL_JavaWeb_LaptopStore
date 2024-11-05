package com.example.ProjectLaptopStore.Config;

import com.example.ProjectLaptopStore.Entity.Enum.User_Enum;
import jakarta.security.auth.message.callback.SecretKeyCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;


import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;

@Configuration
@EnableAutoConfiguration
@EnableMethodSecurity
public class SecurityConfig {
//    private String[] GET_admin = {
//            "/admin/sellproduct/","/admin/totalmount/","/admin/totalcustomer/",
//            "/admin/totalcustomer/","/admin/newcustomer/","/admin/countcustomer/",
//            "/admin/quantitysell/","/admin/totalamount/","/admin/topproduct/",
//            "/admin/topsuppliers/","/admin/topcustomer/","/admin/listbill/",
//            "/admin/list_invoicedetail/","/admin/dashboard/"
//
//    };
//    private String[] POST_admin = {
//            "/admin/createproduct/","/admin/createcustomer/","/admin/createsupplier/",
//
//    };
//
//    private String[] DELETE_admin = {
//            "/admin/deleteproduct/{ids}","/admin/deletecustomer/{ids}","/admin/deletesuppliers/{ids}"
//
//    };
//    private String[] PUT_admin = {
//            "/admin/updateproduct/","/admin/updatecustomer/","/admin/updatesupplier/",
//
//    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request
                        .requestMatchers(HttpMethod.POST, "/user/**","/token-valid").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority("SCOPE_admin")
//                        .requestMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority("SCOPE_admin")
//                        .requestMatchers(HttpMethod.POST,"/admin/**").hasAnyAuthority("SCOPE_admin")
//                        .requestMatchers(HttpMethod.DELETE,"/admin/**").hasAnyAuthority("SCOPE_admin")
//                        .requestMatchers(HttpMethod.PUT,"/admin/**").hasAnyAuthority("SCOPE_admin")
//                        .requestMatchers(HttpMethod.GET,"/user/profile/").hasAnyAuthority("SCOPE_user", "SCOPE_admin")
//                        .requestMatchers(HttpMethod.GET,"/admin/users").hasAnyRole("admin")
                        .anyRequest().authenticated()
        );


        httpSecurity.oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer ->
                        httpSecurityOAuth2ResourceServerConfigurer.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HmacSHA512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return converter;
    }

}
