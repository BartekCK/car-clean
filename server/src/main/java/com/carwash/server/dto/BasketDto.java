package com.carwash.server.dto;

import com.carwash.server.models.Basket;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class BasketDto {
    private final int id;

    private final int bill;

    private final List<String> basketProducts;
/*
    private final String name;

    private final int price;

    private String description;*/

    //private final List<Integer> prodIds

    public static BasketDto build(Basket basket) {
        List<String> li = basket.getBasketProducts().stream().map(product -> product.getName()).collect(Collectors.toList());
        return new BasketDto(basket.getId(), basket.getBill(), li);
    }
}
