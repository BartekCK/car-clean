package com.carwash.server.services;

import com.carwash.server.dto.BasketDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BasketService {

    ResponseEntity<BasketDto> getUserBasket(String username);

    ResponseEntity<BasketDto> addProductToBasket(String username,int productId);

    BasketDto clearUserBasket(String username);

    BasketDto removeProductFormBasket(String username, int productId);
}
