package com.carwash.server.repositories;

import com.carwash.server.models.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpinionRepository extends JpaRepository<Opinion, Integer> {
}
