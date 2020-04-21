package com.carwash.server.dto;

import lombok.Value;

import javax.validation.constraints.Email;

@Value
public class SignUpDto {
    private String username;
    @Email
    private String email;
    private String phone;
    private String password;
}
