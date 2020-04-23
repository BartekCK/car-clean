package com.carwash.server.utilies;

import com.carwash.server.dto.CarDto;
import com.carwash.server.services.AuthorizationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Data
public class CarAdd {

    AuthorizationService authorizationService;

    MockMvc mockMvc;

    public CarAdd(AuthorizationService authorizationService, MockMvc mockMvc) {
        this.authorizationService = authorizationService;
        this.mockMvc = mockMvc;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    private UserAuthAdd userAuthAdd;

    private Integer carId = null;

    public void addUserCar() throws Exception {

        if (carId == null) {
            userAuthAdd = new UserAuthAdd(authorizationService, mockMvc);
            userAuthAdd.createUser();
            userAuthAdd.loginUser();

            CarDto carDto = new CarDto(1, "Audi", "THI66666");

            MvcResult result = mockMvc.perform(post("/api/v1/users/cars")
                    .header("authorization", userAuthAdd.getBearerToken())
                    .content(objectMapper.writeValueAsString(carDto))
                    .contentType("application/json")).andReturn();

            JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
            carId = json.get("id").asInt();
        }

    }


    public void deleteUserCars() {
        userAuthAdd.deleteUser();
    }

}
