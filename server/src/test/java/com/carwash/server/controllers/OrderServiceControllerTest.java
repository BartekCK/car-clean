package com.carwash.server.controllers;

import com.carwash.server.dto.CreateOrderServiceDto;
import com.carwash.server.dto.EmployeeDto;
import com.carwash.server.dto.GetOrderServiceDto;
import com.carwash.server.dto.UserDto;
import com.carwash.server.models.User;
import com.carwash.server.models.enums.OrderServiceStatus;
import com.carwash.server.repositories.OrderServiceRepository;
import com.carwash.server.repositories.UserRepository;
import com.carwash.server.services.AuthorizationService;
import com.carwash.server.services.OrderServiceServiceImpl;
import com.carwash.server.utilies.CarAdd;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceControllerTest {


    @Value("${com.hour.work.start}")
    private int startWorkHour;
    @Value("${com.hour.work.end}")
    private int endWorkHour;


    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private CarAdd carAdd;

    @Autowired
    OrderServiceServiceImpl orderServiceService;

    @Autowired
    OrderServiceRepository orderServiceRepository;

    @BeforeAll
    void createUserAndCar() throws Exception {
        carAdd = new CarAdd(authorizationService, mockMvc);
        carAdd.addUserCar();
    }

    @Test
    void should_addReservationServiceByUser() throws Exception {

        User user = userRepository.findByUsername(carAdd.getUserAuthAdd().getSignUpDto().getUsername()).orElseThrow(() -> new Exception());
        CreateOrderServiceDto createOrderServiceDto = new CreateOrderServiceDto
                ((long) 1, "2000-02-03", 12, " ", " ", carAdd.getCarId().intValue(),
                        user.getId(), 1
                );

        MvcResult result = mockMvc.perform(post("/api/v1/users/services")
                .header("authorization", carAdd.getUserAuthAdd().getBearerToken())
                .content(objectMapper.writeValueAsString(createOrderServiceDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        CreateOrderServiceDto dto = objectMapper.readValue(result.getResponse().getContentAsString(), CreateOrderServiceDto.class);

        orderServiceService.deleteOrderServiceById(dto.getId());

    }

    @Test
    void should_return_error_becauseOf_the_same_date_and_time() throws Exception {

        User user = userRepository.findByUsername(carAdd.getUserAuthAdd().getSignUpDto().getUsername()).orElseThrow(() -> new Exception());
        CreateOrderServiceDto createOrderServiceDto = new CreateOrderServiceDto
                ((long) 1, "2000-02-03", 12, " ", " ", carAdd.getCarId().intValue(),
                        user.getId(), 1
                );

        MvcResult result = mockMvc.perform(post("/api/v1/users/services")
                .header("authorization", carAdd.getUserAuthAdd().getBearerToken())
                .content(objectMapper.writeValueAsString(createOrderServiceDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        MvcResult result2 = mockMvc.perform(post("/api/v1/users/services")
                .header("authorization", carAdd.getUserAuthAdd().getBearerToken())
                .content(objectMapper.writeValueAsString(createOrderServiceDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().is(404)).andReturn();

        CreateOrderServiceDto dto = objectMapper.readValue(result.getResponse().getContentAsString(), CreateOrderServiceDto.class);
        orderServiceService.deleteOrderServiceById(dto.getId());
        assertEquals(result2.getResponse().getContentAsString(), "Wybrany termin jest niedostępny");

    }

    @Test
    void should_return_FreeHoursBy_all_Day() throws Exception {

        LocalDate localDate = LocalDate.parse("1920-03-05");

        MvcResult result = mockMvc.perform(get("/api/v1/users/services/hours")
                .header("authorization", carAdd.getUserAuthAdd().getBearerToken())
                .content(objectMapper.writeValueAsString(localDate))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        List<Integer> freeHours = objectMapper.readValue(result.getResponse().getContentAsString(), new ArrayList<Integer>().getClass());

        assertEquals(freeHours.size(), endWorkHour - startWorkHour + 1);
    }


    @Test
    void should_return_notFound_FreeHoursBy_all_Day() throws Exception {

        LocalDate localDate = LocalDate.parse("1920-03-05");
        List<Long> idList = new ArrayList<>();

        User user = userRepository.findByUsername(carAdd.getUserAuthAdd().getSignUpDto().getUsername()).orElseThrow(() -> new Exception());


        for (int i = startWorkHour; i <= endWorkHour; i++) {


            CreateOrderServiceDto createOrderServiceDto = new CreateOrderServiceDto
                    ((long) 1, localDate.toString(), i, " ", " ", carAdd.getCarId().intValue(),
                            user.getId(), 1
                    );

            MvcResult result = mockMvc.perform(post("/api/v1/users/services")
                    .header("authorization", carAdd.getUserAuthAdd().getBearerToken())
                    .content(objectMapper.writeValueAsString(createOrderServiceDto))
                    .contentType("application/json"))
                    .andExpect(status().isOk()).andReturn();
            CreateOrderServiceDto dto = objectMapper.readValue(result.getResponse().getContentAsString(), CreateOrderServiceDto.class);
            idList.add(dto.getId());
        }


        mockMvc.perform(get("/api/v1/users/services/hours")
                .header("authorization", carAdd.getUserAuthAdd().getBearerToken())
                .content(objectMapper.writeValueAsString(localDate))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().is(404));


        idList.forEach(id -> orderServiceService.deleteOrderServiceById(id));

    }

    @Test
    void should_return_allUserService() throws Exception {

        User user = userRepository.findByUsername(carAdd.getUserAuthAdd().getSignUpDto().getUsername()).orElseThrow(() -> new Exception());

        LocalDate localDate = LocalDate.parse("1920-03-05");
        List<Long> idList = new ArrayList<>();

        for (int i = startWorkHour; i <= endWorkHour; i++) {

            CreateOrderServiceDto createOrderServiceDto = new CreateOrderServiceDto
                    ((long) 1, localDate.toString(), i, " ", " ", carAdd.getCarId().intValue(),
                            user.getId(), 1
                    );

            MvcResult result = mockMvc.perform(post("/api/v1/users/services")
                    .header("authorization", carAdd.getUserAuthAdd().getBearerToken())
                    .content(objectMapper.writeValueAsString(createOrderServiceDto))
                    .contentType("application/json"))
                    .andReturn();
            CreateOrderServiceDto dto = objectMapper.readValue(result.getResponse().getContentAsString(), CreateOrderServiceDto.class);
            idList.add(dto.getId());
        }


        MvcResult resultGet = mockMvc.perform(get("/api/v1/users/services")
                .header("authorization", carAdd.getUserAuthAdd().getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        List<GetOrderServiceDto> getOrderServiceDtoList = objectMapper
                .readValue(resultGet.getResponse().getContentAsString(), new TypeReference<List<GetOrderServiceDto>>() {
                });

        idList.forEach(id -> orderServiceService.deleteOrderServiceById(id));
    }


    @Test
    void should_change_ServiceStatus() throws Exception {

        User user = userRepository.findByUsername(carAdd.getUserAuthAdd().getSignUpDto().getUsername()).orElseThrow(() -> new Exception());
        CreateOrderServiceDto createOrderServiceDto = new CreateOrderServiceDto
                ((long) 1, "2000-02-03", 12, " ", " ", carAdd.getCarId().intValue(),
                        user.getId(), 1
                );

        EmployeeDto employeeDto = new EmployeeDto(0, UserDto.build(user), "Adam", "Sprzedawca");
        authorizationService.createEmployee(user, employeeDto);

        MvcResult result = mockMvc.perform(post("/api/v1/users/services")
                .header("authorization", carAdd.getUserAuthAdd().getBearerToken())
                .content(objectMapper.writeValueAsString(createOrderServiceDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();


        CreateOrderServiceDto dto = objectMapper.readValue(result.getResponse().getContentAsString(), CreateOrderServiceDto.class);

        MvcResult result2 = mockMvc.perform(put("/api/v1/users/services/{id}", dto.getId())
                .header("authorization", carAdd.getUserAuthAdd().getBearerToken())
                .content(OrderServiceStatus.DONE.toString())
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        GetOrderServiceDto dto2 = objectMapper.readValue(result2.getResponse().getContentAsString(), GetOrderServiceDto.class);

        assertNotEquals(dto.getStatus(), dto2.getStatus());
        orderServiceService.deleteOrderServiceById(dto.getId());
    }
//
//    @Test
//    void payForServiceByUser() {
//    }
//


    @AfterAll()
    void clean() {
        carAdd.deleteUserCars();
    }
}
