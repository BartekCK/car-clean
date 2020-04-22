package com.carwash.server.services;

import com.carwash.server.dto.SignInDto;
import com.carwash.server.dto.SignUpDto;
import com.carwash.server.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AuthorizationService {
    ResponseEntity<String> createUser(SignUpDto signUpDto);
    ResponseEntity loginUser(SignInDto signInDto);
    ResponseEntity deleteUser(String username);
    UserDto getUser(Authentication authentication);
}
