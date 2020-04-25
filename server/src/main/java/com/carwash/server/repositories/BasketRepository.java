package com.carwash.server.repositories;

import com.carwash.server.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Integer> {

    Optional<Basket> findByUserUsername(String username);
}
