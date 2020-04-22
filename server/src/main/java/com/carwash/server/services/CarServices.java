package com.carwash.server.services;

import com.carwash.server.dto.CarDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarServices {
    List<CarDto> getAllUserCars(String username);

    CarDto getUserCar(String username, int carId);

    ResponseEntity deleteUserCar(String username, int carId);

    ResponseEntity<CarDto> addUserCar(String username, CarDto carDto);
}
