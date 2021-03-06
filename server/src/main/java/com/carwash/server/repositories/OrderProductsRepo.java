package com.carwash.server.repositories;

import com.carwash.server.models.Adress;
import com.carwash.server.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderProductsRepo extends JpaRepository<Order, Integer> {

    List<Optional<Order>> findByUserUsername(String username);

    Order findByUserUsernameAndId(String username, int id);

    Order findById(int id);
}
