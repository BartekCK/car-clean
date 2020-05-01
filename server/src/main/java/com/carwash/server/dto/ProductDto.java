package com.carwash.server.dto;

import com.carwash.server.models.Product;
import com.carwash.server.models.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@AllArgsConstructor
public class ProductDto {
    private int id;

    private String name;

    private int price;

    private String description;

    private String prod_photo;

    private ProductCategory category;

    public static ProductDto build(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getProd_photo(),
                product.getCategory());
    }
}
