package com.carwash.server.dto;

import com.carwash.server.models.Basket;
import lombok.Value;

@Value
public class BasketDto {
    private final int id;

    private final int bill;

    private BasketDto(Basket basket) {
        this.id = basket.getId();
        this.bill = basket.getBill();
    }

    public static BasketDto createBasketDto(Basket basket) {
        return new BasketDto(basket);
    }
}
