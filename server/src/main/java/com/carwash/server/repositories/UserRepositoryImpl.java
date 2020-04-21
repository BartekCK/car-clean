package com.carwash.server.repositories;


import com.carwash.server.dto.SignUpDto;
import com.carwash.server.models.User;
import com.carwash.server.models.UserPrincipal;
import com.carwash.server.models.authority.Role;
import com.carwash.server.models.authority.RoleName;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJPARepository userJPARepository;

    private final RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> saveUser(SignUpDto signUpDto) {


        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository
                .findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User role not found")));

        User user = new User(signUpDto.getUsername(),
                passwordEncoder.encode(signUpDto.getPassword()),
                signUpDto.getEmail(),
                signUpDto.getPhone(),
                roles);

        userJPARepository.save(user);
        return ResponseEntity.ok("User registered.");
    }


    @Override
    @Transactional
    public UserPrincipal findByUsername(String username) {
        User user = userJPARepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return UserPrincipal.build(user);
    }


}
