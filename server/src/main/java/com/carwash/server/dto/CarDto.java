package com.carwash.server.dto;

import com.carwash.server.models.Car;
import lombok.Value;

@Value
public class CarDto {
    private int id;
    private String brand;
    private String platesNumber;

    public static CarDto build(Car car) {
        return new CarDto(car.getId(), car.getBrand(), car.getPlatesNumber());
    }
}
