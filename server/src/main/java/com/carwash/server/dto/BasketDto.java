package com.carwash.server.dto;

import com.carwash.server.models.Basket;
import com.carwash.server.models.Product;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

@Value
public class BasketDto {
    private final int id;

    private final int bill;

    //private final List<String> basketProducts;
/*
    private final String name;

    private final int price;

    private String description;*/

    //private final List<Integer> prodIds

    private final List<ProductDto> prods;

/*    public static BasketDto build(Basket basket) {
        List<String> li = basket.getBasketProducts().stream().map(product -> product.getName()).collect(Collectors.toList());
        return new BasketDto(basket.getId(), basket.getBill(), li);
    }*/

    public static BasketDto build(Basket basket) {
        //List<ProductDto> produs = basket.getBasketProducts();
//        List <Product> lis = basket.getBasketProducts();
        List <ProductDto> con = basket.getBasketProducts().stream().map(product -> ProductDto.build(product)).collect(Collectors.toList());
//        BeanUtils.copyProperties(con,lis);
        return new BasketDto(basket.getId(), basket.getBill(), con);
    }
}
