package com.carwash.server.repositories;

import com.carwash.server.models.Adress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends JpaRepository<Adress, Integer> {

    Adress findByOrderId(int orderId);

    //boolean existsBy
}
