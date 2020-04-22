package com.carwash.server.controllers;

import com.carwash.server.configuration.jwt.JwtAuthErrorEntryPoint;
import com.carwash.server.configuration.jwt.JwtProvider;
import com.carwash.server.dto.ServicesDto;
import com.carwash.server.services.ServicesServiceImpl;
import com.carwash.server.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(ServicesController.class)
class ServicesControllerTest {

    @MockBean
    JwtProvider jwtProvider;

    @MockBean
    JwtAuthErrorEntryPoint jwtAuthErrorEntryPoint;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    ServicesServiceImpl servicesService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllServices() throws Exception {

        List<ServicesDto> list = Arrays.asList(new ServicesDto(1, "a", 1, "a", "a"),
                new ServicesDto(2, "b", 2, "b", "b"));
        when(servicesService.getAllServices()).thenReturn(list);
        this.mockMvc.perform(get("/api/v1/services")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getService() throws Exception {

        ServicesDto servicesDto = new ServicesDto(1, "a", 1, "a", "a");

        when(servicesService.getService(1)).thenReturn(servicesDto);
        this.mockMvc.perform(get("/api/v1/services/1")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1));
    }

}



