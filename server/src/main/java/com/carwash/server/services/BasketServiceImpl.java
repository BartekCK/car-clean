package com.carwash.server.services;

import com.carwash.server.dto.BasketDto;
import com.carwash.server.repositories.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl implements BasketService {


    BasketRepository basketRepository;

    @Autowired
    public BasketServiceImpl(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    public List<BasketDto> getAllBaskets() {
        return basketRepository.findAll().stream().map(basket -> BasketDto.createBasketDto(basket)).collect(Collectors.toList());
    }

    @Override
    public BasketDto getBasket(int id) {
        return BasketDto.createBasketDto
                (basketRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("Koszyk o id " + id + " nie został znaleziony")
                        )
                );
    }


}
