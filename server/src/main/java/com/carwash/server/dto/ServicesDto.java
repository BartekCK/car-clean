package com.carwash.server.dto;

import com.carwash.server.models.Services;
import lombok.Value;

@Value
public class ServicesDto {
    private final int id;

    private final String name;

    private final int price;

    private final String description;

    private final String image;

    private ServicesDto(Services services) {
        this.id = services.getId();
        this.name = services.getName();
        this.price = services.getPrice();
        this.description = services.getDescription();
        this.image = services.getImage();
    }

    public static ServicesDto createServiceDto(Services services) {
        return new ServicesDto(services);
    }
}
