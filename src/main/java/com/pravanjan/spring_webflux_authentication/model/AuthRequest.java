package com.pravanjan.spring_webflux_authentication.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String userName;
    private String password;

}
