package com.carwash.server.dto;

import com.carwash.server.models.Basket;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class BasketDto {
    private final int id;

    private final int bill;

    private final List<ProductDto> prods;


    public static BasketDto build(Basket basket) {

        List<ProductDto> con = basket.getBasketProducts().stream().map(product -> ProductDto.build(product)).collect(Collectors.toList());
        return new BasketDto(basket.getId(), basket.getBill(), con);
    }
}
