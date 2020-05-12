package com.carwash.server.controllers;

import com.carwash.server.dto.*;
import com.carwash.server.models.enums.ProductCategory;
import com.carwash.server.repositories.BasketRepository;
import com.carwash.server.repositories.UserRepository;
import com.carwash.server.services.AuthorizationService;
import com.carwash.server.services.BasketServiceImpl;
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
public class BasketControllerTest {

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
    void should_get_user_basket() throws Exception {

        MvcResult resultGet = mockMvc.perform(get("/api/v1/basket")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

    }

    @Test
    void should_add_product_to_basket() throws Exception {

        ProductDto productDto = new ProductDto(13, "s", 22, "sdsd", "ss", ProductCategory.APC);

        mockMvc.perform(post("/api/v1/product")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(productDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult resultGet = mockMvc.perform(get("/api/v1/basket/add/13")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        BasketDto dto = objectMapper.readValue(resultGet.getResponse().getContentAsString(), BasketDto.class);

        assertEquals(dto.getBill(),22);
        assertEquals(dto.getProds().get(0),productDto);

        basketService.clearUserBasket(userAuthAdd.getSignUpDto().getUsername());
    }

    @Test
    void should_delete_product_from_basket() throws Exception {

        ProductDto productDto = new ProductDto(13, "s", 22, "sdsd", "ss", ProductCategory.APC);
        ProductDto productDto2 = new ProductDto(14, "sa", 10, "sdsdddddd", "sssss", ProductCategory.APC);

        mockMvc.perform(post("/api/v1/product")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(productDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/product")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(productDto2))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/basket/add/13")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/basket/add/14")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult resultGet = mockMvc.perform(get("/api/v1/basket/remove/14")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        BasketDto dto3 = objectMapper.readValue(resultGet.getResponse().getContentAsString(), BasketDto.class);

        assertEquals(dto3.getBill(),22);
        assertEquals(dto3.getProds().get(0),productDto);

        basketService.clearUserBasket(userAuthAdd.getSignUpDto().getUsername());

    }

    @Test
    void should_clear_basket() throws Exception {

        ProductDto productDto = new ProductDto(13, "s", 22, "sdsd", "ss", ProductCategory.APC);
        ProductDto productDto2 = new ProductDto(14, "sa", 10, "sdsdddddd", "sssss", ProductCategory.APC);

        mockMvc.perform(post("/api/v1/product")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(productDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/product")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(productDto2))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult resultGet = mockMvc.perform(get("/api/v1/basket/clear")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        BasketDto dto2 = objectMapper.readValue(resultGet.getResponse().getContentAsString(), BasketDto.class);

        assertEquals(dto2.getProds().isEmpty(),true);
        assertEquals(dto2.getBill(),0);
    }

    @Test
    void should_get_error_status_404() throws Exception {
        this.mockMvc.perform(get("/api/v1/basket/add/{productId}", 200)).andDo(print()).andExpect(status().is(404));
    }

    @AfterAll()
    void deleteUser() {
        userAuthAdd.deleteUser();
    }
}
