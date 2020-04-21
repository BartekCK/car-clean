package com.carwash.server.services;


import com.carwash.server.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<String> createUser(UserDTO userDTO);


}
