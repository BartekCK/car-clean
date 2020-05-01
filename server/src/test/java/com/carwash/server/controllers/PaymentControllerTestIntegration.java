package com.carwash.server.controllers;

import com.carwash.server.dto.CreateOrderServiceDto;
import com.carwash.server.dto.GetOrderServiceDto;
import com.carwash.server.dto.payments.PaymentDto;
import com.carwash.server.models.User;
import com.carwash.server.repositories.UserRepository;
import com.carwash.server.services.AuthorizationService;
import com.carwash.server.services.OrderServiceServiceImpl;
import com.carwash.server.services.PaymentService;
import com.carwash.server.utilies.CarAdd;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaymentControllerTestIntegration {

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderServiceServiceImpl orderServiceService;


    @Autowired
    PaymentService paymentService;
    private CarAdd carAdd;


    @BeforeAll
    void createUserAndCar() throws Exception {
        carAdd = new CarAdd(authorizationService, mockMvc);
        carAdd.addUserCar();
    }


    @Test
    void payment() throws Exception {

        User user = userRepository.findByUsername(carAdd.getUserAuthAdd().getSignUpDto().getUsername()).orElseThrow(() -> new Exception());
        CreateOrderServiceDto createOrderServiceDto = new CreateOrderServiceDto
                ((long) 1, "2000-02-03", 12, " ", " ", carAdd.getCarId().intValue(),
                        user.getId(), 1
                );

        MvcResult result = mockMvc.perform(post("/api/v1/users/services")
                .header("authorization", carAdd.getUserAuthAdd().getBearerToken())
                .content(objectMapper.writeValueAsString(createOrderServiceDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        CreateOrderServiceDto dto = objectMapper.readValue(result.getResponse().getContentAsString(), CreateOrderServiceDto.class);

        GetOrderServiceDto getOrderServiceDto = orderServiceService.getAllUserService(carAdd.getUserAuthAdd().getSignUpDto().getUsername()).getBody().get(0);
        PaymentDto paymentDto = new PaymentDto(getOrderServiceDto.getServicesDto().getPrice(),
                getOrderServiceDto.getServicesDto().getPrice(), 0, null, getOrderServiceDto, null);


        MvcResult result2 = mockMvc.perform(post("/api/v1/pay")
                .header("authorization", carAdd.getUserAuthAdd().getBearerToken())
                .content(objectMapper.writeValueAsString(paymentDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();


        orderServiceService.deleteOrderServiceById(dto.getId());
    }

    @AfterAll()
    void clean() {
        carAdd.deleteUserCars();
    }
}
