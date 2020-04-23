package com.carwash.server.utilies;

import com.carwash.server.dto.SignInDto;
import com.carwash.server.dto.SignUpDto;
import com.carwash.server.services.AuthorizationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Data
public class UserAuthAdd {

    private AuthorizationService authorizationService;

    private MockMvc mockMvc;

    public UserAuthAdd(AuthorizationService authorizationService, MockMvc mockMvc) {
        this.authorizationService = authorizationService;
        this.mockMvc = mockMvc;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    private String bearerToken;

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
                .contentType("application/json"));
    }

    public void loginUser() throws Exception {
        SignInDto signInDto = new SignInDto(signUpDto.getUsername(), signUpDto.getPassword());

        MvcResult result = mockMvc.perform(post("/api/v1/signin")
                .content(objectMapper.writeValueAsString(signInDto))
                .contentType("application/json")).andReturn();
        //GET TOKEN VALUE
        String jwtToken = result.getResponse().getContentAsString();
        JsonNode json = objectMapper.readTree(jwtToken);
        assertNotNull(json);

        //CHANE JSON TO STRING WITHOUT "..."
        String badToken = json.get("token").toString();
        String token = badToken.substring(1, badToken.length() - 1);
        bearerToken = new StringBuilder().append("Bearer ").append(token).toString();
    }

    public void deleteUser() {
        authorizationService.deleteUser(signUpDto.getUsername());
    }
}
