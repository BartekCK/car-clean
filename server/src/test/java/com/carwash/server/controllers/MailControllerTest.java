package com.carwash.server.controllers;

import com.carwash.server.dto.MailDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MailControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Value("${enable.sending.mail}")
    private boolean isEnable;

    @Test
    void should_sendMail() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        MailDto mailDto = new MailDto("Test1998", "testicolasd@gmail.com", "Test uslugi", "Wykonuje test");
        String result = mockMvc.perform(post("/api/v1/mails")
                .content(objectMapper.writeValueAsString(mailDto))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        if (isEnable) {
            assertEquals(result, "Wiadomość została wysłana");
        } else {
            assertEquals(result, "Serwer poczty jest wyłączony, zmień ustawienia aplikacji");
        }
    }
}
