package com.carwash.server.controllers;

import com.carwash.server.dto.CarDto;
import com.carwash.server.dto.SignInDto;
import com.carwash.server.dto.SignUpDto;
import com.carwash.server.services.AuthorizationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CarControllerTestIntegration {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    MockMvc mockMvc;

    SignUpDto signUpDto;

    String token;

    @BeforeAll()
    void createUser() throws Exception {
        signUpDto = new SignUpDto("XaCS", "XaCS@gmail.com", "123123123", "123");
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
        token = badToken.substring(1, badToken.length() - 1);
    }

    @Test
    void addUserCar() throws Exception {

        CarDto carDto = new CarDto(1, "mercedes", "THI66666");

        mockMvc.perform(post("/api/v1/users/cars")
                .header("authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(carDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllUserCars() throws Exception {
        for (int i = 0; i < 5; i++) {
            CarDto carDto = new CarDto(1, "mercedes", "THI66666");

            mockMvc.perform(post("/api/v1/users/cars")
                    .header("authorization", "Bearer " + token)
                    .content(objectMapper.writeValueAsString(carDto))
                    .contentType("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        mockMvc.perform(get("/api/v1/users/cars")
                .header("authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    void deleteUserCar() throws Exception {
        CarDto carDto = new CarDto(1, "mercedes", "THI66666");

        MvcResult result = mockMvc.perform(post("/api/v1/users/cars")
                .header("authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(carDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        int deleteCarId = json.get("id").asInt();
        System.out.println(deleteCarId);

        mockMvc.perform(delete("/api/v1/users/cars/{id}", deleteCarId)
                .header("authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();


        System.out.println(2);
    }


    @AfterAll()
    void deleteUser() {
        authorizationService.deleteUser(signUpDto.getUsername());
    }

}
