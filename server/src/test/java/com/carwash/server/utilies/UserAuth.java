package com.carwash.server.utilies;

import com.carwash.server.dto.SignInDto;
import com.carwash.server.dto.SignUpDto;
import com.carwash.server.services.AuthorizationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserAuth {

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    SignUpDto signUpDto;

    public void createUser() throws Exception {
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
    }

    public void loginUser() throws Exception {
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
        System.out.println(token);
    }

    public void deleteUser() {
        authorizationService.deleteUser(signUpDto.getUsername());
    }
}
