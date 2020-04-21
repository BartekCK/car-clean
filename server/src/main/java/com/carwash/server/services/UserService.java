package com.carwash.server.services;


import com.carwash.server.dto.SignUpDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<String> createUser(SignUpDto signUpDto);


}
