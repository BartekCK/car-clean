package com.carwash.server.services;

import com.carwash.server.dto.BasketDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BasketService {

    List<BasketDto> getAllBaskets();

    BasketDto getBasket(int id);
}
