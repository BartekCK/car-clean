package com.carwash.server.repositories;


import com.carwash.server.dto.SignUpDto;
import com.carwash.server.models.UserPrincipal;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;


public interface UserRepository {

    ResponseEntity<String> saveUser(SignUpDto signUpDto);

    @Transactional
    UserPrincipal findByUsername(String username);

}
