package com.carwash.server.services;

import com.carwash.server.configuration.jwt.JwtProvider;
import com.carwash.server.dto.JwtTokenDto;
import com.carwash.server.dto.SignInDto;
import com.carwash.server.dto.SignUpDto;
import com.carwash.server.models.User;
import com.carwash.server.models.authority.Role;
import com.carwash.server.models.authority.RoleName;
import com.carwash.server.repositories.RoleRepository;
import com.carwash.server.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private JwtProvider provider;
    private AuthenticationManager manager;


    @Override
    public ResponseEntity<String> createUser(SignUpDto signUpDto) {

        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return ResponseEntity.badRequest().body("Użytkownik "+ signUpDto.getUsername() + " już istnieje");
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return ResponseEntity.badRequest().body("Użytkownik o adresie email "+ signUpDto.getEmail() + " już istnieje");
        }
        if (userRepository.existsByUsername(signUpDto.getPhone())) {
            return ResponseEntity.badRequest().body("Użytkownik o numerze telefonu "+ signUpDto.getPhone() + " już istnieje");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository
                .findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Rola nie istnieje")));

        User user = new User(signUpDto.getUsername(),
                passwordEncoder.encode(signUpDto.getPassword()),
                signUpDto.getEmail(),
                signUpDto.getPhone(),
                roles);

        userRepository.save(user);
        return ResponseEntity.ok("Użytkownik pomyślnie został zarejestrowany");
    }

    @Override
    public ResponseEntity loginUser(SignInDto signInDto) {
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
