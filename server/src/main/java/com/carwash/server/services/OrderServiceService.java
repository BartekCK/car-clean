package com.carwash.server.services;

import com.carwash.server.dto.OrderServiceDto;
import com.carwash.server.models.enums.OrderServiceStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface OrderServiceService {
    ResponseEntity<? extends Object> addReservationService(String username, OrderServiceDto orderServiceDto);

    ResponseEntity<List<Integer>> getFreeHoursByDay(LocalDate localDate);

    ResponseEntity<List<OrderServiceDto>> getAllUserService(String username);

    ResponseEntity<OrderServiceDto> changeServiceStatus(Long idService, OrderServiceStatus orderServiceStatus);

    ResponseEntity payForServiceByUser(Long idService, String username);
}
