package com.carwash.server.controllers;


import com.carwash.server.models.UserPrincipal;
import com.carwash.server.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public UserPrincipal getUserById(Authentication authentication) {
        System.out.println(authentication.getPrincipal());
        return null;
    }

}
