package com.carwash.server.dto.payments;

import com.carwash.server.dto.GetOrderServiceDto;
import com.carwash.server.dto.OrderProductsDto;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class PaymentDto {
    private String totalPrice;
    private String subtotalPrice;
    private String shippingPrice;
    private ShippingDto shippingDto;

    private GetOrderServiceDto orderServiceDto;
    private OrderProductsDto orderProductsDto;


    public PaymentDto(Integer totalPrice, Integer subtotalPrice, Integer shippingPrice, ShippingDto shippingDto, GetOrderServiceDto orderServiceDto, OrderProductsDto orderProductsDto) {
        this.totalPrice = String.valueOf(totalPrice);
        this.subtotalPrice = String.valueOf(subtotalPrice);
        this.shippingPrice = String.valueOf(shippingPrice);
        this.shippingDto = shippingDto;
        this.orderServiceDto = orderServiceDto;
        this.orderProductsDto = orderProductsDto;
    }
}
