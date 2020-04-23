package com.carwash.server.dto;

import com.carwash.server.models.OrderService;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class GetOrderServiceDto {

    @NotNull
    private Long id;

    private CarDto carDto;

    private ServicesDto servicesDto;

    @NotNull
    private String date;

    @NotNull
    private int time;

    @NotNull
    private String status;

    @NotNull
    private String paidStatus;

    private Long userId;

    public static GetOrderServiceDto build(OrderService orderService) {
        return new GetOrderServiceDto(
                orderService.getId(),
                CarDto.build(orderService.getCar()),
                ServicesDto.build(orderService.getServiceid()),
                orderService.getDate().toString(),
                orderService.getTime(),
                orderService.getStatus().toString(),
                orderService.getPaidStatus().toString(),
                orderService.getUser().getId());
    }
}
