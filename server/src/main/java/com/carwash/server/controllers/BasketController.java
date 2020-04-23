package com.carwash.server.controllers;

import com.carwash.server.dto.BasketDto;
import com.carwash.server.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/basket")
public class BasketController {

    BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping
    public List<BasketDto> getAllBaskets() {
        return basketService.getAllBaskets();
    }

    @GetMapping("{id}")
    public BasketDto getBasket(@PathVariable("id") int id) {
        return basketService.getBasket(id);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity noHandlerFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
