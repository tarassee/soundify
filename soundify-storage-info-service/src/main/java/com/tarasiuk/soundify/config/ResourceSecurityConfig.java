package com.tarasiuk.soundify.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceSecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/storages").hasAuthority("SCOPE_write")
                .antMatchers(HttpMethod.DELETE, "/storages").hasAuthority("SCOPE_write")
                .antMatchers(HttpMethod.GET, "/storages", "/storages/type/**").permitAll()
                .and()
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        jwt -> jwt.decoder(JwtDecoders.fromIssuerLocation(issuerUri))
                )).build();
    }

}
