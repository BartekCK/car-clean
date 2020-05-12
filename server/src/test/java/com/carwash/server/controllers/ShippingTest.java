package com.carwash.server.controllers;

import com.carwash.server.dto.*;
import com.carwash.server.dto.payments.ShippingDto;
import com.carwash.server.models.Order;
import com.carwash.server.repositories.BasketRepository;
import com.carwash.server.repositories.OrderProductsRepo;
import com.carwash.server.repositories.UserRepository;
import com.carwash.server.services.AuthorizationService;
import com.carwash.server.services.BasketServiceImpl;
import com.carwash.server.services.OrderProductsServiceImpl;
import com.carwash.server.utilies.UserAuthAdd;
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
public class ShippingTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    BasketServiceImpl basketService;

    @Autowired
    OrderProductsRepo orderProductsRepo;

    @Autowired
    OrderProductsServiceImpl orderProductsService;

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
    void should_add_shipping_data() throws Exception {

        ShippingDto shippingDto = new ShippingDto("m","mm","ssd","fgh","asdf","rtyu","123123123");

        mockMvc.perform(get("/api/v1/basket/add/1")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/basket/add/2")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult resultGet = mockMvc.perform(get("/api/v1/orders/create")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        OrderProductsDto odto = objectMapper.readValue(resultGet.getResponse().getContentAsString(), OrderProductsDto.class);

        mockMvc.perform(post("/api/v1/orders/shipping/1")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(shippingDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());

        Order order = orderProductsRepo.findById(odto.getId());
        orderProductsRepo.delete(order);
    }

    @AfterAll()
    void deleteUser() {
        userAuthAdd.deleteUser();
    }
}
