package com.carwash.server.services;

import com.carwash.server.dto.CreateOpinionDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OpinionServiceImplTestIntegration {

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
    void should_create_Opinion_and_delete() throws IOException {
        Path path = Paths.get("src/test/resources/1.png");
        String name = "1.png";
        String originalFileName = "1.png";
        String contentType = "image/png";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile image = new MockMultipartFile(name,
                originalFileName, contentType, content);

        CreateOpinionDto createOpinionDto = new CreateOpinionDto(userAuthAdd.getSignUpDto().getUsername(), 2, LocalDateTime.now(), "Było super", null);
        opinionService.createOpinion(userAuthAdd.getSignUpDto().getUsername(), image, createOpinionDto);

        opinionService.deleteOpinion(userAuthAdd.getSignUpDto().getUsername());

    }

    @Test
    void should_getAllOpinions_and_delete() throws IOException {
        Path path = Paths.get("src/test/resources/1.png");
        String name = "1.png";
        String originalFileName = "1.png";
        String contentType = "image/png";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile image = new MockMultipartFile(name,
                originalFileName, contentType, content);

        CreateOpinionDto createOpinionDto = new CreateOpinionDto(userAuthAdd.getSignUpDto().getUsername(), 2, LocalDateTime.now(), "Było super", null);
        opinionService.createOpinion(userAuthAdd.getSignUpDto().getUsername(), image, createOpinionDto);

        List<CreateOpinionDto> opinionList = opinionService.getAllOpinions().getBody();

        opinionList.forEach(op -> System.out.println(op));
        assertFalse(opinionList.isEmpty());
        opinionService.deleteOpinion(userAuthAdd.getSignUpDto().getUsername());
    }


    @AfterAll()
    void deleteUser() {
        userAuthAdd.deleteUser();
    }
}
