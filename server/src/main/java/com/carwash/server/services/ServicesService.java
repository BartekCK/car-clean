package com.carwash.server.services;

import dto.ServicesDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicesService {

    List<ServicesDto> getAllServices();

    ServicesDto getService(int id);
}
