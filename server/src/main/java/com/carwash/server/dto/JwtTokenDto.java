package com.carwash.server.dto;

import lombok.Data;
import lombok.Value;

@Value
public class JwtTokenDto {
    private String token;
    private String type = "Bearer";

    public JwtTokenDto(String accessToken) {
        this.token = accessToken;
    }

}
