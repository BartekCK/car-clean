package com.carwash.server.repositories;


import com.carwash.server.models.authority.Role;
import com.carwash.server.models.authority.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
