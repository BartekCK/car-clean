package com.carwash.server.controllers;

import com.carwash.server.dto.ProductDto;
import com.carwash.server.dto.SignUpDto;
import com.carwash.server.models.enums.ProductCategory;
import com.carwash.server.services.AuthorizationService;
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
class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    MockMvc mockMvc;

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
    void addProduct() throws Exception {

        ProductDto productDto = new ProductDto(1,"s",22,"sdsd","ss", ProductCategory.APC);

        mockMvc.perform(post("/api/v1/product")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(productDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void getAllProducts() throws Exception {
        for (int i = 0; i < 5; i++) {
            ProductDto productDto = new ProductDto(1,"s",22,"sdsd","ss", ProductCategory.APC);

            mockMvc.perform(post("/api/v1/product")
                    .header("authorization", userAuthAdd.getBearerToken())
                    .content(objectMapper.writeValueAsString(productDto))
                    .contentType("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        mockMvc.perform(get("/api/v1/product")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getProduct() throws Exception {
            ProductDto productDto = new ProductDto(1,"s",22,"sdsd","ss", ProductCategory.APC);

        MvcResult result = mockMvc.perform(post("/api/v1/product")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(productDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        int getProductId = json.get("id").asInt();
        System.out.println(getProductId);

        mockMvc.perform(get("/api/v1/product/{id}",getProductId)
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getProductsByCategory() throws Exception {
        ProductDto productDto = new ProductDto(1,"s",22,"sdsd","ss", ProductCategory.APC);

        MvcResult result = mockMvc.perform(post("/api/v1/product")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(productDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        String getProductCategory = json.get("category").asText("APC");
        System.out.println(getProductCategory);
        mockMvc.perform(get("/api/v1/product/category/{category}",getProductCategory)
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    void deleteProduct() throws Exception {
        ProductDto productDto = new ProductDto(1,"s",22,"sdsd","ss", ProductCategory.APC);

        MvcResult result = mockMvc.perform(post("/api/v1/product")
                .header("authorization", userAuthAdd.getBearerToken())
                .content(objectMapper.writeValueAsString(productDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        int deleteProductId = json.get("id").asInt();
        System.out.println(deleteProductId);

        mockMvc.perform(delete("/api/v1/product/{id}", deleteProductId)
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
    }

    @AfterAll()
    void deleteUser() {
        userAuthAdd.deleteUser();
    }


}


/*    @Test
    void getAllProducts() throws Exception {

        List<ProductDto> list = Arrays.asList(new ProductDto(1,"s",22,"sdsd","ss", ProductCategory.APC));
        when(productService.getAllProducts()).thenReturn(list);
        this.mockMvc.perform(get("/api/v1/product")).andDo(print()).andExpect(status().isOk());
    }*/
