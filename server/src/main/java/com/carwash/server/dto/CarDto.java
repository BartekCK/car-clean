package com.carwash.server.dto;

import com.carwash.server.models.Car;
import lombok.Value;

@Value
public class CarDto {
    private final int id;
    private final String brand;
    private final String platesNumber;

    public static CarDto build(Car car) {
        if (car == null)
            return null;
        return new CarDto(car.getId(), car.getBrand(), car.getPlatesNumber());
    }

}
