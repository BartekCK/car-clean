package com.carwash.server.services;

import com.carwash.server.dto.BasketDto;
import com.carwash.server.models.Basket;
import com.carwash.server.models.Product;
import com.carwash.server.repositories.BasketRepository;
import com.carwash.server.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BasketServiceImpl implements BasketService {


    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<BasketDto> getUserBasket(String username) {
        Basket basket = basketRepository.findByUserUsername(username).orElseThrow(() -> new RuntimeException("Koszyk nie został znaleziony"));
        return ResponseEntity.ok(BasketDto.build(basket));
    }

    @Override
    public ResponseEntity<BasketDto> addProductToBasket(String username, int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produkt nie został znaleziony"));
        Basket basket = basketRepository.findByUserUsername(username).orElseThrow(() -> new RuntimeException("Koszyk nie został znaleziony"));

        int newPrice = basket.getBill() + product.getPrice();
        basket.setBill(newPrice);

        basket.getBasketProducts().add(product);
        basketRepository.save(basket);

        return ResponseEntity.ok(BasketDto.build(basket));
    }

    @Override
    public BasketDto clearUserBasket(String username) {
        Basket basket = basketRepository.findByUserUsername(username).orElseThrow(() -> new RuntimeException("Koszyk nie został znaleziony"));
        basket.getBasketProducts().clear();
        basket.setBill(0);
        basketRepository.save(basket);
        return BasketDto.build(basket);
    }

    @Override
    public BasketDto removeProductFormBasket(String username, int productId) {
        Basket basket = basketRepository.findByUserUsername(username).orElseThrow(() -> new RuntimeException("Koszyk nie został znaleziony"));

        basket.getBasketProducts().removeIf(t -> t.getId() == productId);
        int newPrice = 0;
        for (final Product basprod : basket.getBasketProducts()) {
            newPrice = newPrice + basprod.getPrice();
        }
        basket.setBill(newPrice);
        basketRepository.save(basket);
        return BasketDto.build(basket);
    }
}
