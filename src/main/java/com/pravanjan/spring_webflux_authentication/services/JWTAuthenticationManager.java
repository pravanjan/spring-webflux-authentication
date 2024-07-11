package com.pravanjan.spring_webflux_authentication.services;

import com.pravanjan.spring_webflux_authentication.config.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
@AllArgsConstructor
public class JWTAuthenticationManager implements ReactiveAuthenticationManager {
    private final JWTUtil jwtUtil;
    private final UserService userService;
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        String userName = jwtUtil.extractUsername(token);

      return   userService.findByUsername(userName).
                map(user -> {
                    if (jwtUtil.validateToken(token, user.getUserName())){
                        return authentication;
                    }
                    else {
                        throw  new AuthenticationException("Invalid token ") {};
                    }
                });
    }

    public ServerAuthenticationConverter authenticationConverter(){
        return new ServerAuthenticationConverter() {
            @Override
            public Mono<Authentication> convert(ServerWebExchange exchange) {
                  String token = exchange.getRequest().getHeaders().getFirst("Authorization");
                  if(token !=null && token.startsWith("Bearer ")){
                      token = token.substring(7);
                      return Mono.just(SecurityContextHolder.getContext().getAuthentication());
                  }
                return Mono.empty();
            }
        };
    }
}
