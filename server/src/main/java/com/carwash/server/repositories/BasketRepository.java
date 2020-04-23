package com.carwash.server.repositories;

import com.carwash.server.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
}
