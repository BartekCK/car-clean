package com.carwash.server.dto;

import lombok.Value;

import javax.validation.constraints.Email;

@Value
public class SignUpDto {
    private final String username;
    @Email
    private final String email;
    private final String phone;
    private final String password;
}
