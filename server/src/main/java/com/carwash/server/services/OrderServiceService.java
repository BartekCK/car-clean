package com.carwash.server.services;

import com.carwash.server.dto.CreateOrderServiceDto;
import com.carwash.server.dto.GetOrderServiceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface OrderServiceService {
    ResponseEntity<? extends Object> addReservationService(String username, CreateOrderServiceDto createOrderServiceDto);

    ResponseEntity<List<Integer>> getFreeHoursByDay(LocalDate localDate);

    ResponseEntity<List<GetOrderServiceDto>> getAllUserService(String username);

    ResponseEntity<GetOrderServiceDto> changeServiceStatus(Long idService, String status);

    ResponseEntity<GetOrderServiceDto> getServiceByIdAndUser(Long idService, String username);

    ResponseEntity<List<GetOrderServiceDto>> getAllServiceByDay(LocalDate localDate);
}
