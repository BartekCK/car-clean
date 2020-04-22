package com.carwash.server.dto;

import com.carwash.server.models.OrderService;
import lombok.Value;

@Value
public class OrderServiceDto {

    private Long id;

    private String date;

    private int time;

    private String status;

    private String paidStatus;

    private CarDto car;

    private UserDto user;

    private EmployeeDto employee;

    private ServicesDto services;

    static OrderServiceDto build(OrderService orderService) {
        return new OrderServiceDto(
                orderService.getId(),
                orderService.getDate().toString(),
                orderService.getTime(),
                orderService.getStatus().toString(),
                orderService.getPaidStatus().toString(),
                CarDto.build(orderService.getCar()),
                UserDto.build(orderService.getUser()),
                EmployeeDto.build(orderService.getEmployee()),
                ServicesDto.build(orderService.getServiceid())
        );
    }
}
