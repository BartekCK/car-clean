package com.carwash.server.services;

import com.carwash.server.dto.SignInDto;
import com.carwash.server.dto.SignUpDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthorizationService {
    ResponseEntity<String> createUser(SignUpDto signUpDto);
    ResponseEntity loginUser(SignInDto signInDto);
}
