package com.carwash.server.services;

import com.carwash.server.dto.OrderProductsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderProductsService {
    ResponseEntity<OrderProductsDto> getUserOrderById(String username, int orderId);

    List<OrderProductsDto> getUserOrders(String username);

    ResponseEntity<OrderProductsDto> createOrder(String username);

    ResponseEntity<OrderProductsDto> changeOrderStatus(String username, int orderId);
}
