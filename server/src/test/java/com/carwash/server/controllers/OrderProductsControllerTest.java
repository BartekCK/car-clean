package com.carwash.server.controllers;

import com.carwash.server.dto.*;
import com.carwash.server.models.Order;
import com.carwash.server.models.enums.ProductCategory;
import com.carwash.server.repositories.BasketRepository;
import com.carwash.server.repositories.OrderProductsRepo;
import com.carwash.server.repositories.UserRepository;
import com.carwash.server.services.AuthorizationService;
import com.carwash.server.services.BasketServiceImpl;
import com.carwash.server.services.OrderProductsServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderProductsControllerTest {

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
    void should_create_order_product() throws Exception {

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

        mockMvc.perform(get("/api/v1/orders")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/orders/1")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult resultGet2 = mockMvc.perform(get("/api/v1/orders/status/1")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        OrderProductsDto odto2 = objectMapper.readValue(resultGet2.getResponse().getContentAsString(), OrderProductsDto.class);
        assertEquals(odto2.getPaid_status(),"ZapÅ\u0082acono");

        Order order = orderProductsRepo.findById(odto.getId());
        orderProductsRepo.delete(order);
    }

    @Test
    void should_get_error_status_404() throws Exception {
        this.mockMvc.perform(get("/api/v1/orders/{prod}", 200)).andDo(print()).andExpect(status().is(404));
    }

    @AfterAll()
    void deleteUser() {

        userAuthAdd.deleteUser();
    }
}
