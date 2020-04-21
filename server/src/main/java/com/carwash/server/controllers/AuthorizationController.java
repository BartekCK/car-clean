package com.carwash.server.controllers;

import com.carwash.server.dto.SignInDto;
import com.carwash.server.dto.SignUpDto;
import com.carwash.server.services.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
@AllArgsConstructor
public class AuthorizationController {

    private AuthorizationService authorizationService;

    @PostMapping("signin")
    public ResponseEntity authenticateUser(@RequestBody SignInDto signInDto) {
        return authorizationService.loginUser(signInDto);
    }

    @PostMapping("signup")
    public ResponseEntity<String> createUser(@RequestBody SignUpDto signUpDto) {
        return authorizationService.createUser(signUpDto);
    }
}
