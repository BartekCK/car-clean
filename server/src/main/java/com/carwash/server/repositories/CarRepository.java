package com.carwash.server.repositories;

import com.carwash.server.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    Optional<Car> findByUserUsernameAndId(String username, int carId);

    List<Optional<Car>> findByUserUsername(String username);
}
