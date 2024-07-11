package com.pravanjan.spring_webflux_authentication.controller;

import com.pravanjan.spring_webflux_authentication.config.JWTUtil;
import com.pravanjan.spring_webflux_authentication.model.AuthRequest;
import com.pravanjan.spring_webflux_authentication.model.AuthResponse;
import com.pravanjan.spring_webflux_authentication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login (@RequestBody AuthRequest authRequest){
        return userService.findByUsername(authRequest.getUserName())
                .map(user -> {
                    if (user.getPassword().equals(authRequest.getPassword())){
                        return ResponseEntity.ok(new AuthResponse(jwtUtil.
                                generateToken(authRequest.getUserName())));
                    }
                    else {
                        throw  new BadCredentialsException("Invalid user name or  password");
                    }
                }).switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password")));
    }

}
