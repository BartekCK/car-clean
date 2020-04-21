package com.carwash.server.dto;

import lombok.Data;
import lombok.Value;

@Value
public class SignInDto {
    private final String username;
    private final String password;
}
