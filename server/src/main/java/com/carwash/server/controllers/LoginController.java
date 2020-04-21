package com.carwash.server.controllers;

import com.carwash.server.configuration.jwt.JwtProvider;
import com.carwash.server.dto.JwtTokenDto;
import com.carwash.server.dto.SignInDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
@AllArgsConstructor
public class LoginController {

    private JwtProvider provider;
    private AuthenticationManager manager;

    @PostMapping("signin")//OK
    public ResponseEntity authenticateUser(@RequestBody SignInDto signInDto) {

        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getUsername(),
                        signInDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = provider.generateToken(authentication);

        return ResponseEntity.ok(new JwtTokenDto(token));
    }
}
