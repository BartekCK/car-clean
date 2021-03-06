package com.carwash.server.services;

import com.carwash.server.dto.ServicesDto;
import com.carwash.server.repositories.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesServiceImpl implements ServicesService {


    ServicesRepository servicesRepository;

    @Autowired
    public ServicesServiceImpl(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    @Override
    public List<ServicesDto> getAllServices() {
        return servicesRepository.findAll().stream().map(service -> ServicesDto.build(service)).collect(Collectors.toList());
    }

    @Override
    public ServicesDto getService(int id) {
        return ServicesDto.build
                (servicesRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("Serwis o id " + id + " nie został znaleziony")
                        )
                );
    }


}
