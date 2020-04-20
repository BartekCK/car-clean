package com.carwash.server.repositories;

import com.carwash.server.models.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepository extends JpaRepository<Services, Integer> {
}
