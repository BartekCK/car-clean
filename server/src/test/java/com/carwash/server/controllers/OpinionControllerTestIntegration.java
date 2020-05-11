package com.carwash.server.controllers;

import com.carwash.server.dto.CreateOpinionDto;
import com.carwash.server.services.AuthorizationService;
import com.carwash.server.services.OpinionService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OpinionControllerTestIntegration {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    OpinionService opinionService;

    private UserAuthAdd userAuthAdd;

    @BeforeAll()
    void createUser() throws Exception {

        userAuthAdd = new UserAuthAdd(authorizationService, mockMvc);
        userAuthAdd.createUser();
        userAuthAdd.loginUser();

    }

    @Test
    void should_create_Opinion_get_and_delete() throws Exception {
        Path path = Paths.get("src/test/resources/1.png");
        String name = "1.png";
        String originalFileName = "1.png";
        String contentType = "image/png";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }

        CreateOpinionDto createOpinionDto = new CreateOpinionDto(userAuthAdd.getSignUpDto().getUsername(), 2, LocalDateTime.now(), "Było super", null);

        MockMultipartFile image = new MockMultipartFile("file", originalFileName, contentType, content);
        MockMultipartFile dto = new MockMultipartFile("createOpinionDto", "", "application/json", objectMapper.writeValueAsString(createOpinionDto).getBytes());


        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/opinions")
                .file(image)
                .file(dto)
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());


        mockMvc.perform(get("/api/v1/opinions")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());


        mockMvc.perform(delete("/api/v1/opinions")
                .header("authorization", userAuthAdd.getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @AfterAll()
    void deleteUser() {
        userAuthAdd.deleteUser();
    }
}
