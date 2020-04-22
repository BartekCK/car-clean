package com.carwash.server.dto;

import com.carwash.server.models.Services;
import lombok.Value;

@Value
public class ServicesDto {
    private int id;

    private String name;

    private int price;

    private String description;

    private String image;

    public static ServicesDto build(Services services) {
        return new ServicesDto(
                services.getId(),
                services.getName(),
                services.getPrice(),
                services.getDescription(),
                services.getImage());
    }
}
