package com.carwash.server.services;

import com.carwash.server.dto.OrderProductsDto;
import com.carwash.server.dto.ProductDto;
import com.carwash.server.models.Basket;
import com.carwash.server.models.Order;
import com.carwash.server.models.User;
import com.carwash.server.models.enums.OrderServiceStatus;
import com.carwash.server.models.enums.PaidStatus;
import com.carwash.server.repositories.BasketRepository;
import com.carwash.server.repositories.OrderProductsRepo;
import com.carwash.server.repositories.ProductRepository;
import com.carwash.server.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderProductsServiceImpl implements OrderProductsService {

    private final BasketRepository basketRepository;
    private final OrderProductsRepo orderProductsRepo;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<OrderProductsDto> getUserOrderById(String username, int orderId) {
        return null;
    }

    @Override
    public List<OrderProductsDto> getUserOrders(String username) {

        return orderProductsRepo.findByUserUsername(username)
                .stream()
                .map(order ->
                        OrderProductsDto.build(order.orElseThrow(() ->
                                new RuntimeException("Zamówienia nie zostało znalezione"))))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<OrderProductsDto> createOrder(String username) {

        Basket basket = basketRepository.findByUserUsername(username).orElseThrow(() -> new RuntimeException("Koszyk nie został znaleziony"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Użytkownik nie został znaleziony"));

        Order order = Order.builder()
                .orderProducts(basket.getBasketProducts())
                .bill(basket.getBill())
                .paid_status(PaidStatus.NOT_PAID)
                .user(user)
                .build();
        orderProductsRepo.save(order);

        basket.setBill(0);
        basketRepository.save(basket);
        return ResponseEntity.ok(OrderProductsDto.build(order));
    }
}
