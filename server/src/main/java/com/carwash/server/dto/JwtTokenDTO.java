package com.carwash.server.dto;

import lombok.Data;

@Data
public class JwtTokenDTO {
    private String token;
    private String type = "Bearer";

    public JwtTokenDTO(String accessToken) {
        this.token = accessToken;
    }

}
