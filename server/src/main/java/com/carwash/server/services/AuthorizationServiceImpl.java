package com.carwash.server.services;

import com.carwash.server.configuration.jwt.JwtProvider;
import com.carwash.server.dto.*;
import com.carwash.server.models.*;
import com.carwash.server.models.authority.Role;
import com.carwash.server.models.authority.RoleName;
import com.carwash.server.repositories.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class);

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider provider;
    private final AuthenticationManager manager;
    private final EmployeeRepository employeeRepository;
    private final MailService mailService;
    private final OrderServiceRepository orderServiceRepository;
    private final OpinionService opinionService;

    private final BasketRepository basketRepository;


    @Override
    public ResponseEntity<String> createUser(SignUpDto signUpDto) {

        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return ResponseEntity.badRequest().body("Użytkownik " + signUpDto.getUsername() + " już istnieje");
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return ResponseEntity.badRequest().body("Użytkownik o adresie email " + signUpDto.getEmail() + " już istnieje");
        }
        if (userRepository.existsByUsername(signUpDto.getPhone())) {
            return ResponseEntity.badRequest().body("Użytkownik o numerze telefonu " + signUpDto.getPhone() + " już istnieje");
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

        Basket userbasket = Basket.builder()
                .bill(0)
                .basketProducts(null)
                .user(user)
                .build();
        basketRepository.save(userbasket);

        try {
            mailService.informationAboutRegistration(user);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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

    @Override
    public ResponseEntity deleteUser(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Użytkownik nie został znaleziony"));

        Basket basket = this.basketRepository.findByUserUsername(username).orElseThrow(() -> new RuntimeException("Użytkownik nie został znaleziony"));
        this.basketRepository.delete(basket);

        try {
            List<OrderService> userOrders = orderServiceRepository
                    .findAllByUserId(user.getId()).get();
            userOrders.forEach(order -> order.setUser(null));
            orderServiceRepository.saveAll(userOrders);
        } catch (Exception e) {

            try {
                opinionService.deleteOpinion(username);
            } catch (Exception ex) {
                logger.info("Delete opinions {}", ex.getMessage());
            }
            this.userRepository.delete(user);
            return new ResponseEntity(HttpStatus.OK);
        }
        try {
            opinionService.deleteOpinion(username);
        } catch (Exception e) {
            logger.info("Delete opinions {}", e.getMessage());
        }

        this.userRepository.delete(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    public UserDto getUser(Authentication authentication) {
        return UserDto.build((UserPrincipal) authentication.getPrincipal());
    }

    @Override
    public EmployeeDto createEmployee(User user, EmployeeDto employeeDto) {
        User futureEmployee = userRepository.findByUsername(user.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("User not found: " + user.getUsername()));
        Employee employee = new Employee(0, futureEmployee, employeeDto.getName(), employeeDto.getPosition());

        Role role = roleRepository
                .findByName(RoleName.ROLE_EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Rola nie istnieje"));

        Set<Role> tempSetRoles = futureEmployee.getRoles();
        tempSetRoles.add(role);
        futureEmployee.setRoles(tempSetRoles);

        userRepository.save(futureEmployee);
        employeeRepository.save(employee);

        return EmployeeDto.build(employee);
    }

    @Override
    @Transactional
    public UserDto deleteEmployee(User user) {
        User updateUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("User not found: " + user.getUsername()));

        Employee employee = employeeRepository.findById(updateUser.getEmployee().getId()).orElseThrow(() -> new RuntimeException("Wybrany użytkownik nie jest pracownikiem"));
        employeeRepository.delete(employee);

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository
                .findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Rola nie istnieje")));
        updateUser.setRoles(roles);
        userRepository.save(updateUser);
        return UserDto.build(user);
    }

}
