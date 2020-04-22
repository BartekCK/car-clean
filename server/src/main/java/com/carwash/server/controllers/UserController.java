package com.carwash.server.controllers;

import com.carwash.server.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


}
