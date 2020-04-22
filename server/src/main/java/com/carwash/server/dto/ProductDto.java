package com.carwash.server.dto;

import com.carwash.server.models.Product;
import com.carwash.server.models.enums.ProductCategory;
import lombok.Value;

@Value
public class ProductDto {
    private final int id;

    private final String name;

    private final int price;

    private final String description;

    private final String prod_photo;

    private final ProductCategory category;

    private ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.prod_photo = product.getProd_photo();
        this.category = product.getCategory();
    }

    public static ProductDto createProductDto(Product product) {
        return new ProductDto(product);
    }
}
