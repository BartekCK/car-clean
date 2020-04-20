package com.carwash.server.dbresources.offer;

import com.carwash.server.models.Services;
import com.carwash.server.repositories.ServicesRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class ServicesDbStart implements ApplicationRunner {

    ServicesRepository servicesRepository;

    @Autowired
    public ServicesDbStart(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (servicesRepository.count() <= 0) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Services> services = mapper.readValue(
                        new File("src/main/resources/services.json"),
                        new TypeReference<List<Services>>() {
                        }
                );
                services.forEach(el -> servicesRepository.save(el));
            } catch (Error e) {
                System.out.println("Bład dodawania serwisów do bazy danych");
            }
        }
    }
}
