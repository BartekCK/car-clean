package com.carwash.server.controllers;

import com.carwash.server.configuration.jwt.JwtProvider;
import com.carwash.server.dto.JwtTokenDTO;
import com.carwash.server.dto.LoginDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/index")
@AllArgsConstructor
public class LoginController {

    private JwtProvider provider;
    private AuthenticationManager manager;

    @PostMapping("signin")//OK
    public ResponseEntity authenticateUser(@RequestBody LoginDTO loginDTO) {

        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = provider.genereteToken(authentication);

        return ResponseEntity.ok(new JwtTokenDTO(token));
    }
}
