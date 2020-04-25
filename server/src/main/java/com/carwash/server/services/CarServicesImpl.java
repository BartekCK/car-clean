package com.carwash.server.services;

import com.carwash.server.dto.CarDto;
import com.carwash.server.models.Car;
import com.carwash.server.models.OrderService;
import com.carwash.server.models.User;
import com.carwash.server.models.enums.OrderServiceStatus;
import com.carwash.server.repositories.CarRepository;
import com.carwash.server.repositories.OrderServiceRepository;
import com.carwash.server.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarServicesImpl implements CarServices {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final OrderServiceRepository orderServiceRepository;

    @Override
    public List<CarDto> getAllUserCars(String username) {
        return carRepository.findByUserUsername(username)
                .stream()
                .map(car ->
                        CarDto.build(car.orElseThrow(() ->
                                new RuntimeException("Pojazd nie został znaleziony"))))
                .collect(Collectors.toList());
    }

    @Override
    public CarDto getUserCar(String username, int carId) {
        Car car = carRepository.findByUserUsernameAndId(username, carId).orElseThrow(() -> new RuntimeException("Pojazd nie został znaleziony"));
        return CarDto.build(car);
    }

    @Override
    public ResponseEntity deleteUserCar(String username, int carId) {
        Car car = carRepository.findByUserUsernameAndId(username, carId).orElseThrow(() -> new RuntimeException("Pojazd nie został znaleziony"));

        List<OrderService> carOrders = orderServiceRepository
                .findAllByCar(car);
        if (!carOrders.isEmpty()) {
            boolean result = carOrders.stream()
                    .anyMatch(order -> order.getStatus() == OrderServiceStatus.WAITING || order.getStatus() == OrderServiceStatus.IN_PROGRESS);
            if (result == true)
                throw new RuntimeException("Wybranego pojazdu nie można usunąc ponieważ posiada usługi o statusie Rezerwacja lub Do końca zostało 15 min");
            carOrders.forEach(order -> order.setCar(null));
            orderServiceRepository.saveAll(carOrders);
        }
        carRepository.delete(car);
        return ResponseEntity.ok("Pojazd został usunięty");
    }

    @Override
    public ResponseEntity<CarDto> addUserCar(String username, CarDto carDto) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Użytkownik nie został znaleziony"));
        Car car = Car.builder()
                .brand(carDto.getBrand())
                .platesNumber(carDto.getPlatesNumber())
                .user(user)
                .build();
        carRepository.save(car);
        return ResponseEntity.ok(CarDto.build(car));
    }
}
