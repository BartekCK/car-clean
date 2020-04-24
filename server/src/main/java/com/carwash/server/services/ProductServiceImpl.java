package com.carwash.server.services;

import com.carwash.server.dto.ProductDto;
import com.carwash.server.models.Product;
import com.carwash.server.models.User;
import com.carwash.server.models.enums.ProductCategory;
import com.carwash.server.repositories.ProductRepository;
import com.carwash.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return productRepository.findAll().stream().map(product -> ProductDto.build(product)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(ProductCategory category) {
        List<Product> products = productRepository.findAllByCategory(category);
        List<ProductDto> productsDto = products.stream().map(el ->ProductDto.build(el)).collect(Collectors.toList());
        return productsDto;
    }

    @Override
    public ResponseEntity<ProductDto> updateProduct(int productId, ProductDto productDto) {
        Optional<Product> prod = productRepository.findById(productId);
        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .prod_photo(productDto.getProd_photo())
                .description(productDto.getDescription())
                .category(productDto.getCategory())
                .build();
        productRepository.save(product);
        return ResponseEntity.ok(ProductDto.build(product));
    }

    @Override
    public ProductDto getProduct(int id) {
        return ProductDto.build
                (productRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("Produkt o id " + id + " nie został znaleziony")
                        )
                );
    }

    @Override
    public ResponseEntity<ProductDto> addProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .prod_photo(productDto.getProd_photo())
                .description(productDto.getDescription())
                .category(productDto.getCategory())
                .build();
        productRepository.save(product);
        return ResponseEntity.ok(ProductDto.build(product));
    }

    @Override
    public ResponseEntity deleteProduct(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produkt nie został znaleziony"));
        productRepository.delete(product);
        return ResponseEntity.ok("Usunięto produkt");
    }
}
