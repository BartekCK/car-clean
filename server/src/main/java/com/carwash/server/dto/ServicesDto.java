package com.carwash.server.dto;

import com.carwash.server.models.Services;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ServicesDto {
    private int id;

    private String name;

    private int price;

    private String description;

    private String image;

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
