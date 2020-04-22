package com.carwash.server.controllers;

import com.carwash.server.dto.CarDto;
import com.carwash.server.services.CarServices;
import com.carwash.server.utilies.AuthMiner;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/users/cars")
@AllArgsConstructor
public class CarController {

    private final CarServices carServices;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CarDto> addUserCar(Authentication authentication, @RequestBody CarDto carDto) {
        return carServices.addUserCar(AuthMiner.getUsername(authentication), carDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<CarDto> getAllUserCars(Authentication authentication) {
        return carServices.getAllUserCars(AuthMiner.getUsername(authentication));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    public CarDto getUserCar(Authentication authentication, @PathVariable("id") int carId) {
        return carServices.getUserCar(AuthMiner.getUsername(authentication), carId);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteUserCar(Authentication authentication, @PathVariable("id") int carId) {
        return carServices.deleteUserCar(AuthMiner.getUsername(authentication), carId);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity noHandlerFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
