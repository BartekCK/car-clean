package com.carwash.server.dto;

import lombok.Value;

@Value
public class SignInDto {
    private String username;
    private String password;
}
