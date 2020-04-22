package com.carwash.server.repositories;

import com.carwash.server.models.OrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface OrderServiceRepository extends JpaRepository<OrderService, Long> {

    Boolean existsByDateEqualsAndTimeEquals(LocalDate date, int time);
}
