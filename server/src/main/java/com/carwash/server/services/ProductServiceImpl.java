package com.carwash.server.services;

import com.carwash.server.repositories.ProductRepository;
import com.carwash.server.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(product -> ProductDto.createProductDto(product)).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProduct(int id) {
        return ProductDto.createProductDto
                (productRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("Produkt o id " + id + " nie został znaleziony")
                        )
                );
    }


}
