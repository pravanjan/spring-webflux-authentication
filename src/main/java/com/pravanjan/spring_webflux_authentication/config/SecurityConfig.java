package com.pravanjan.spring_webflux_authentication.config;

import com.pravanjan.spring_webflux_authentication.services.JWTAuthenticationManager;
import com.pravanjan.spring_webflux_authentication.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JWTAuthenticationManager authenticationManager;
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.
                authorizeExchange(exchanges-> exchanges
                        .pathMatchers("/login","signup").permitAll()
                        .anyExchange().authenticated()
                ).build();
    }

}
