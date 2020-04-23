package com.carwash.server.controllers;

import com.carwash.server.dto.CreateOrderServiceDto;
import com.carwash.server.dto.GetOrderServiceDto;
import com.carwash.server.models.enums.OrderServiceStatus;
import com.carwash.server.services.OrderServiceService;
import com.carwash.server.utilies.AuthMiner;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/users/services")
public class OrderServiceController {

    private final OrderServiceService orderServiceService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<? extends Object> addReservationServiceByUser(
            Authentication authentication,
            @RequestBody CreateOrderServiceDto createOrderServiceDto) {
        return orderServiceService.addReservationService(AuthMiner.getUsername(authentication), createOrderServiceDto);
    }

    @GetMapping("hours")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Integer>> getFreeHoursByDay(@RequestBody LocalDate localDate) {
        return orderServiceService.getFreeHoursByDay(localDate);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<GetOrderServiceDto>> getAllUserService(Authentication authentication) {
        return orderServiceService.getAllUserService(AuthMiner.getUsername(authentication));
    }

    @PutMapping("{idService}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<CreateOrderServiceDto> changeServiceStatus(@PathVariable("idService") Long idService, @RequestBody OrderServiceStatus orderServiceStatus) {
        return orderServiceService.changeServiceStatus(idService, orderServiceStatus);
    }

    @PutMapping("{idService}/paid")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity payForServiceByUser(@PathVariable("idService") Long idService, Authentication authentication) {
        return orderServiceService.payForServiceByUser(idService, AuthMiner.getUsername(authentication));
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity noHandlerFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
