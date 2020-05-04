package com.carwash.server.dto;

import com.carwash.server.dto.payments.ShippingDto;
import com.carwash.server.models.Order;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class OrderProductsDto {

    private final int id;

    private final int bill;

    private final String paid_status;

    private final List<ProductDto> prods;

    private final ShippingDto shippingDto;

    public static OrderProductsDto build(Order order) {

        List<ProductDto> con = order.getOrderProducts().stream().map(product -> ProductDto.build(product)).collect(Collectors.toList());
        ShippingDto tempShippingDto =  ShippingDto.build(order.getAdress());
        return new OrderProductsDto(order.getId(),
                order.getBill(),
                order.getPaid_status().toString(),
                con,
                tempShippingDto
               );
    }
}
