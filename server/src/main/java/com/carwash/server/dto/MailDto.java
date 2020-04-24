package com.carwash.server.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class MailDto {
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String header;
    @NotNull
    private String message;

}
