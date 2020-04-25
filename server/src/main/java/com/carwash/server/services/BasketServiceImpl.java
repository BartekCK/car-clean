package com.carwash.server.services;

import com.carwash.server.dto.BasketDto;
import com.carwash.server.models.Basket;
import com.carwash.server.models.Product;
import com.carwash.server.repositories.BasketRepository;
import com.carwash.server.repositories.ProductRepository;
import com.carwash.server.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BasketServiceImpl implements BasketService {


    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

/*    @Override
    public List<BasketDto> getAllBaskets() {
        return basketRepository.findAll().stream().map(basket -> BasketDto.build(basket)).collect(Collectors.toList());
    }*/


    @Override
    public ResponseEntity<BasketDto> getUserBasket(String username, int basketId) {
        Basket basket = basketRepository.findByUserUsername(username).orElseThrow(() -> new RuntimeException("Koszyk nie został znaleziony"));
        return ResponseEntity.ok(BasketDto.build(basket));
    }

    @Override
   // @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public ResponseEntity<BasketDto> addProductToBasket(String username,int productId) {
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
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produkt nie został znaleziony"));
        int newPrice = basket.getBill() - product.getPrice();
        basket.setBill(newPrice);
        basket.getBasketProducts().remove(product.getId());
        basketRepository.save(basket);
        return BasketDto.build(basket);
    }


}
//product.getId() (product.getId(),product.getName()),product.getPrice(),product.getProd_photo(),product.setDescription(),product.setCategory();
