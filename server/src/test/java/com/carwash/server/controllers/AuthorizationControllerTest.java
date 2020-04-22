package com.carwash.server.controllers;

import com.carwash.server.configuration.jwt.JwtAuthErrorEntryPoint;
import com.carwash.server.configuration.jwt.JwtProvider;
import com.carwash.server.dto.SignInDto;
import com.carwash.server.dto.SignUpDto;
import com.carwash.server.repositories.RoleRepository;
import com.carwash.server.repositories.UserRepository;
import com.carwash.server.services.AuthorizationService;
import com.carwash.server.services.UserServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthorizationControllerTest {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    JwtAuthErrorEntryPoint jwtAuthErrorEntryPoint;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void createUser_loginUser_checkAuthorities_delete() throws Exception {

        SignUpDto signUpDto = new SignUpDto("XaCS", "XaCS@gmail.com", "123123123", "123");
        try {
            authorizationService.deleteUser(signUpDto.getUsername());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        mockMvc.perform(post("/api/v1/signup")
                .content(objectMapper.writeValueAsString(signUpDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());

        SignInDto signInDto = new SignInDto(signUpDto.getUsername(), signUpDto.getPassword());

        MvcResult result = mockMvc.perform(post("/api/v1/signin")
                .content(objectMapper.writeValueAsString(signInDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        //GET TOKEN VALUE
        String jwtToken = result.getResponse().getContentAsString();
        JsonNode json = objectMapper.readTree(jwtToken);
        assertNotNull(json);

        //CHANE JSON TO STRING WITHOUT "..."
        String badToken = json.get("token").toString();
        String token = badToken.substring(1, badToken.length() - 1);
        System.out.println(token);

        mockMvc.perform(get("/api/v1/users")
                .header("authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());

        authorizationService.deleteUser(signUpDto.getUsername());

    }
}
