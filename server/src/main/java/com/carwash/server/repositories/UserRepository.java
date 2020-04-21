package com.carwash.server.repositories;


import com.carwash.server.dto.UserDTO;
import com.carwash.server.models.UserPrincipal;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.List;


public interface UserRepository {

    ResponseEntity<String> saveUser(UserDTO userDTO);


    @Transactional
    UserPrincipal findByUsername(String username);

}
