package com.carwash.server.controllers;

import com.carwash.server.dto.OrderProductsDto;
import com.carwash.server.dto.ProductDto;
import com.carwash.server.services.OrderProductsService;
import com.carwash.server.utilies.AuthMiner;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/orders")
@AllArgsConstructor
public class OrderProductsController {

    private final OrderProductsService orderProductsService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<OrderProductsDto> getUserOrders(Authentication authentication) {
        return orderProductsService.getUserOrders(AuthMiner.getUsername(authentication));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderProductsDto> getUserOrderById(Authentication authentication, @PathVariable("id")int orderId) {
        return orderProductsService.getUserOrderById(AuthMiner.getUsername(authentication),orderId);
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderProductsDto> createOrder(Authentication authentication) {
        return orderProductsService.createOrder(AuthMiner.getUsername(authentication));
    }

    @GetMapping("/status/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderProductsDto> changeOrderStatus(Authentication authentication, @PathVariable("id")int orderId) {
        return orderProductsService.changeOrderStatus(AuthMiner.getUsername(authentication),orderId);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity noHandlerFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
