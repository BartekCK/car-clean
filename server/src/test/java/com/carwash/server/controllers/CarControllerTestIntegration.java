package com.carwash.server.controllers;

import com.carwash.server.dto.CarDto;
import com.carwash.server.dto.CreateOrderServiceDto;
import com.carwash.server.dto.SignUpDto;
import com.carwash.server.models.User;
import com.carwash.server.models.enums.OrderServiceStatus;
import com.carwash.server.repositories.UserRepository;
import com.carwash.server.services.AuthorizationService;
import com.carwash.server.services.OrderServiceServiceImpl;
import com.carwash.server.utilies.UserAuthAdd;
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

    @Autowired
    OrderServiceServiceImpl orderServiceService;

    @Autowired
    UserRepository userRepository;

    SignUpDto signUpDto;

    String token;

    private UserAuthAdd userAuthAdd;

    @BeforeAll()
    void createUser() throws Exception {

        userAuthAdd = new UserAuthAdd(authorizationService, mockMvc);
        userAuthAdd.createUser();
        userAuthAdd.loginUser();

    }

    @Test
    void should_addUserCar() throws Exception {

        CarDto carDto = new CarDto(1, "mercedes", "THI66666");

        mockMvc.perform(post("/api/v1/users/cars")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(carDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void should_getAllUserCars() throws Exception {
        for (int i = 0; i < 5; i++) {
            CarDto carDto = new CarDto(1, "mercedes", "THI66666");

            mockMvc.perform(post("/api/v1/users/cars")
                    .header("authorization", userAuthAdd.getBearerToken())
                    .content(objectMapper.writeValueAsString(carDto))
                    .contentType("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        mockMvc.perform(get("/api/v1/users/cars")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    void should_deleteUserCar() throws Exception {
        CarDto carDto = new CarDto(1, "mercedes", "THI66666");

        MvcResult result = mockMvc.perform(post("/api/v1/users/cars")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(carDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        int deleteCarId = json.get("id").asInt();


        mockMvc.perform(delete("/api/v1/users/cars/{id}", deleteCarId)
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void should_deleteUserCar_without_order_service() throws Exception {

        //CREATE CAR
        CarDto carDto = new CarDto(1, "mercedes", "THI66666");

        MvcResult result = mockMvc.perform(post("/api/v1/users/cars")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(carDto))
                .contentType("application/json"))
                .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        int deleteCarId = json.get("id").asInt();


        //CREATE ORDER
        User user = userRepository.findByUsername(userAuthAdd.getSignUpDto().getUsername()).orElseThrow(() -> new Exception());
        CreateOrderServiceDto createOrderServiceDto = new CreateOrderServiceDto
                ((long) 1, "2000-02-03", 12, " ", " ", deleteCarId,
                        user.getId(), 1
                );
        MvcResult result2 = mockMvc.perform(post("/api/v1/users/services")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(createOrderServiceDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        CreateOrderServiceDto dto = objectMapper.readValue(result2.getResponse().getContentAsString(), CreateOrderServiceDto.class);
        //CHANGE STATUS TO DONE
        orderServiceService.changeServiceStatus(dto.getId(), OrderServiceStatus.DONE.toString());

        //DELETE CAR
        mockMvc.perform(delete("/api/v1/users/cars/{id}", deleteCarId)
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        //DELETE ORDER WHERE CAR == NULL
        orderServiceService.deleteOrderServiceById(dto.getId());
    }

    @Test
    void should_not_deleteUserCar() throws Exception {
//CREATE CAR
        CarDto carDto = new CarDto(1, "mercedes", "THI66666");

        MvcResult result = mockMvc.perform(post("/api/v1/users/cars")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(carDto))
                .contentType("application/json"))
                .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        int deleteCarId = json.get("id").asInt();


        //CREATE ORDER
        User user = userRepository.findByUsername(userAuthAdd.getSignUpDto().getUsername()).orElseThrow(() -> new Exception());
        CreateOrderServiceDto createOrderServiceDto = new CreateOrderServiceDto
                ((long) 1, "2000-02-03", 12, " ", " ", deleteCarId,
                        user.getId(), 1
                );
        MvcResult result2 = mockMvc.perform(post("/api/v1/users/services")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(createOrderServiceDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        CreateOrderServiceDto dto = objectMapper.readValue(result2.getResponse().getContentAsString(), CreateOrderServiceDto.class);

        //DELETE CAR
        mockMvc.perform(delete("/api/v1/users/cars/{id}", deleteCarId)
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().is(404)).andReturn();

        //DELETE ORDER WHERE CAR == NULL
        orderServiceService.deleteOrderServiceById(dto.getId());
    }

    @AfterAll()
    void deleteUser() {
        userAuthAdd.deleteUser();
    }

}
