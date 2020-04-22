package com.carwash.server.services;

import com.carwash.server.dto.ServicesDto;
import com.carwash.server.models.Services;
import com.carwash.server.repositories.ServicesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ServicesServiceImplTest {

    @Mock
    ServicesRepository servicesRepository;

    @InjectMocks
    ServicesServiceImpl servicesServiceImpl;


    @Test
    void getAllServices() {

        List<Services> list = Arrays.asList(new Services(1, "a", 1, "a", "a"),
                new Services(2, "b", 2, "b", "b"));

        when(servicesRepository.findAll()).thenReturn(list);
        servicesServiceImpl.getAllServices().forEach(el -> {
            assertNotNull(el);
        });

    }

    @Test
    void getOneService() {
        Optional<Services> opt = Optional.of(new Services(1, "b", 2, "b", "b"));
        when(servicesRepository.findById(1)).thenReturn(opt);

        ServicesDto temp = servicesServiceImpl.getService(1);

        assertNotNull(temp);
    }

}
