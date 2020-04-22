package com.carwash.server.dbresources.offer;

import com.carwash.server.models.Product;
import com.carwash.server.models.Services;
import com.carwash.server.repositories.ProductRepository;
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
    ProductRepository productRepository;

    @Autowired
    public ServicesDbStart(ServicesRepository servicesRepository, ProductRepository productRepository) {
        this.servicesRepository = servicesRepository;
        this.productRepository = productRepository;
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

        if (productRepository.count() <= 0) {
            try {
                ObjectMapper Prodmapper = new ObjectMapper();
                List<Product> product = Prodmapper.readValue(
                        new File("src/main/resources/products.json"),
                        new TypeReference<List<Product>>() {
                        }
                );
                product.forEach(el -> productRepository.save(el));
            } catch (Error e) {
                System.out.println("Bład dodawania produktów do bazy danych");
            }
        }
    }
}
