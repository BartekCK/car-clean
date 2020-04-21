package com.carwash.server.controllers;


import com.carwash.server.dto.UserDTO;
import com.carwash.server.models.UserPrincipal;
import com.carwash.server.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("signup")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public UserPrincipal getUserById(Authentication authentication) {
        System.out.println(authentication.getPrincipal());
        return null;
    }


}
