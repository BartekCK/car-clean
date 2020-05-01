package com.carwash.server.repositories;

import com.carwash.server.models.Car;
import com.carwash.server.models.OrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderServiceRepository extends JpaRepository<OrderService, Long> {

    Boolean existsByDateEqualsAndTimeEquals(LocalDate date, int time);

    Optional<List<OrderService>> findByDate(LocalDate date);

    Optional<List<OrderService>> findAllByUserId(Long userId);

    Optional<OrderService> findByIdAndAndUserUsername(Long orderServiceId, String username);

    Optional<List<OrderService>> findAllByDate(LocalDate date);

    List<OrderService> findAllByCar(Car car);

}
