package com.carwash.server.controllers;

import com.carwash.server.dto.BasketDto;
import com.carwash.server.models.Basket;
import com.carwash.server.services.BasketService;
import com.carwash.server.utilies.AuthMiner;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/basket")
@AllArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BasketDto> getUserBasket(Authentication authentication) {
        return basketService.getUserBasket(AuthMiner.getUsername(authentication));
    }

    @PutMapping("add/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BasketDto> addProductToBasket(Authentication authentication, @PathVariable("productId") int productId) {
        return basketService.addProductToBasket(AuthMiner.getUsername(authentication),productId);
    }

    @PutMapping("remove/{productId}")
    @PreAuthorize("hasRole('USER')")
    public BasketDto removeProductFormBasket(Authentication authentication, @PathVariable("productId") int productId) {
        return basketService.removeProductFormBasket(AuthMiner.getUsername(authentication), productId);
    }

    @PutMapping("clear")
    @PreAuthorize("hasRole('USER')")
    public BasketDto clearUserBasket(Authentication authentication) {
        return basketService.clearUserBasket(AuthMiner.getUsername(authentication));
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity noHandlerFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
