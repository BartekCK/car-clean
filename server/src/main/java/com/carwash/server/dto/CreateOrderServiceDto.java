package com.carwash.server.dto;

import com.carwash.server.models.OrderService;
import lombok.Value;

@Value
public class CreateOrderServiceDto {

    private Long id;

    private String date;

    private int time;

    private String status;

    private String paidStatus;

    private int carId;

    private Long userId;

    private int servicesId;

    public static CreateOrderServiceDto build(OrderService orderService) {
        return new CreateOrderServiceDto(
                orderService.getId(),
                orderService.getDate().toString(),
                orderService.getTime(),
                orderService.getStatus().toString(),
                orderService.getPaidStatus().toString(),
                orderService.getCar().getId(),
                orderService.getUser().getId(),
                orderService.getServiceid().getId()
        );
    }
}
