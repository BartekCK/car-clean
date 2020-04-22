package com.carwash.server.services;

import com.carwash.server.dto.OrderServiceDto;
import com.carwash.server.models.*;
import com.carwash.server.models.enums.OrderServiceStatus;
import com.carwash.server.models.enums.PaidStatus;
import com.carwash.server.repositories.CarRepository;
import com.carwash.server.repositories.OrderServiceRepository;
import com.carwash.server.repositories.ServicesRepository;
import com.carwash.server.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceServiceImpl implements OrderServiceService {

    private final OrderServiceRepository orderServiceRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final ServicesRepository servicesRepository;


    @Override
    public ResponseEntity<? extends Object> addReservationService(String username, OrderServiceDto orderServiceDto) {
        try {
            if (orderServiceRepository.existsByDateEqualsAndTimeEquals(LocalDate.parse(orderServiceDto.getDate()), orderServiceDto.getTime()))
                throw new RuntimeException("Wybrany termin jest niedostępny");
        } catch (DateTimeException e) {
            throw new RuntimeException("Błędny format daty, poprawny to YYYY-MM-DD");
        }


        Car car = carRepository.findByUserUsernameAndId(username, orderServiceDto.getCar().getId()).orElseThrow(() -> new RuntimeException("Błędny pojazd"));
        User user = userRepository.findByUsername(username).orElse(null);
        Employee employee = null;
        Services services = servicesRepository.findById(orderServiceDto.getServices().getId()).orElseThrow(() -> new RuntimeException("Błędny wybrany serwis"));

        if (user == null && employee == null) {
            throw new RuntimeException("Brak osoby rozpoczynającej");
        }

        OrderService orderService = OrderService.builder()
                .date(LocalDate.parse(orderServiceDto.getDate()))
                .time(orderServiceDto.getTime())
                .status(OrderServiceStatus.WAITING)
                .paidStatus(PaidStatus.NOT_PAID)
                .car(car)
                .user(user)
//                .employee() //LATER
                .serviceid(services)
                .build();


        System.out.println(orderService);
        return ResponseEntity.ok(orderServiceDto);
    }

    @Override
    public ResponseEntity<List<Integer>> getFreeHoursByDay(LocalDate localDate) {
        return null;
    }

    @Override
    public ResponseEntity<List<OrderServiceDto>> getAllUserService(String username) {
        return null;
    }

    @Override
    public ResponseEntity<OrderServiceDto> changeServiceStatus(Long idService, OrderServiceStatus orderServiceStatus) {
        return null;
    }

    @Override
    public ResponseEntity payForServiceByUser(Long idService, String username) {
        return null;
    }
}
