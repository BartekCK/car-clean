package com.carwash.server.services;

import com.carwash.server.dto.CreateOrderServiceDto;
import com.carwash.server.dto.GetOrderServiceDto;
import com.carwash.server.models.Car;
import com.carwash.server.models.OrderService;
import com.carwash.server.models.Services;
import com.carwash.server.models.User;
import com.carwash.server.models.enums.OrderServiceStatus;
import com.carwash.server.models.enums.PaidStatus;
import com.carwash.server.repositories.CarRepository;
import com.carwash.server.repositories.OrderServiceRepository;
import com.carwash.server.repositories.ServicesRepository;
import com.carwash.server.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceServiceImpl implements OrderServiceService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceServiceImpl.class);

    private final OrderServiceRepository orderServiceRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final ServicesRepository servicesRepository;

    @Autowired
    public OrderServiceServiceImpl(OrderServiceRepository orderServiceRepository, CarRepository carRepository, UserRepository userRepository, ServicesRepository servicesRepository) {
        this.orderServiceRepository = orderServiceRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.servicesRepository = servicesRepository;
    }

    @Value("${com.hour.work.start}")
    private int startWorkHour;
    @Value("${com.hour.work.end}")
    private int endWorkHour;


    @Override
    @Transactional
    public ResponseEntity<? extends Object> addReservationService(String username, CreateOrderServiceDto createOrderServiceDto) {
        try {
            if (orderServiceRepository.existsByDateEqualsAndTimeEquals(LocalDate.parse(createOrderServiceDto.getDate()), createOrderServiceDto.getTime()))
                throw new RuntimeException("Wybrany termin jest niedostępny");
        } catch (DateTimeException e) {
            throw new RuntimeException("Błędny format daty, poprawny to YYYY-MM-DD");
        }

        Car car = carRepository.findByUserUsernameAndId(username, createOrderServiceDto.getCarId()).orElseThrow(() -> new RuntimeException("Błędny pojazd"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Brak osoby rozpoczynającej"));

        Services services = servicesRepository.findById(createOrderServiceDto.getServicesId()).orElseThrow(() -> new RuntimeException("Błędny wybrany serwis"));

        OrderService orderService = OrderService.builder()
                .date(LocalDate.parse(createOrderServiceDto.getDate()))
                .time(createOrderServiceDto.getTime())
                .status(OrderServiceStatus.WAITING)
                .paidStatus(PaidStatus.NOT_PAID)
                .car(car)
                .user(user)
                .serviceid(services)
                .build();

        orderServiceRepository.save(orderService);
        return ResponseEntity.ok(CreateOrderServiceDto.build(orderService));
    }

    @Override
    public ResponseEntity<List<Integer>> getFreeHoursByDay(LocalDate localDate) {
        List<Integer> freeHours = new ArrayList<>();
        try {
            List<OrderService> orderServiceList = orderServiceRepository.findByDate(localDate).orElseThrow(() -> new Exception("Wybrany dzień nie ma zaplanowanych wizyt"));
            for (int i = startWorkHour; i <= endWorkHour; i++) {
                int temp = i;
                boolean decision = orderServiceList.stream().anyMatch(el -> el.getTime() == temp);
                if (!decision)
                    freeHours.add(i);
            }
        } catch (Exception e) {
            logger.info("{}", e.getMessage());
            for (int i = startWorkHour; i <= endWorkHour; i++)
                freeHours.add(i);
        }

        if (freeHours.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(freeHours);
    }

    @Override
    public ResponseEntity<List<GetOrderServiceDto>> getAllUserService(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Użytkownik " + username + " nie został znaleziony"));
        logger.info("{}", user.toString());
        List<OrderService> orderServiceList = orderServiceRepository.
                findAllByUserId(user.getId()).orElseThrow(() -> new RuntimeException("Brak usług"));
        logger.info("{}", orderServiceList.toString());

        List<GetOrderServiceDto> temp = orderServiceList.stream().map(order -> GetOrderServiceDto.build(order)).collect(Collectors.toList());
        return ResponseEntity.ok(temp);
    }

    @Override
    @Transactional
    public ResponseEntity<GetOrderServiceDto> changeServiceStatus(Long idService, String status) {
        OrderServiceStatus orderServiceStatus = OrderServiceStatus.fromString(status);

        OrderService order = orderServiceRepository.findById(idService).orElseThrow(() -> new RuntimeException("Brak serwisu"));
        if (order.getStatus() == OrderServiceStatus.DONE || order.getStatus() == OrderServiceStatus.CANCEL)
            return ResponseEntity.badRequest().body(GetOrderServiceDto.build(order));
        order.setStatus(orderServiceStatus);
        orderServiceRepository.save(order);
        return ResponseEntity.ok(GetOrderServiceDto.build(order));
    }

    @Override
    public ResponseEntity payForServiceByUser(Long idService, String username) {
        return null;
    }

    @Override
    public ResponseEntity<List<GetOrderServiceDto>> getAllServiceByDay(LocalDate localDate) {
        List<OrderService> orders = orderServiceRepository.findAllByDate(localDate).orElseThrow(() -> new RuntimeException("Brak rezerwacji tego dnia"));
        List<GetOrderServiceDto> orderServiceDtoList = orders.stream().map(el -> GetOrderServiceDto.build(el)).collect(Collectors.toList());
        return ResponseEntity.ok(orderServiceDtoList);
    }

    public void deleteOrderServiceById(Long idService) {
        orderServiceRepository.deleteById(idService);
    }
}
