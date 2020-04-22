package com.carwash.server.controllers;

import com.carwash.server.services.ServicesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ServicesControllerTestIntegration {

    @Autowired
    ServicesServiceImpl servicesService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void should_get_error_status_404() throws Exception {
        this.mockMvc.perform(get("/api/v1/services/{id}", 200)).andDo(print()).andExpect(status().is(404));
    }

    @Test
    void should_get_ok() throws Exception {
        this.mockMvc.perform(get("/api/v1/services/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void should_get_ok_by_all() throws Exception {
        this.mockMvc.perform(get("/api/v1/services")).andDo(print()).andExpect(status().isOk());
    }


}



