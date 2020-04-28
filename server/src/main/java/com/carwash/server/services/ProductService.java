package com.carwash.server.services;

import com.carwash.server.dto.ProductDto;
import com.carwash.server.models.enums.ProductCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto getProduct(int id);

    ResponseEntity<ProductDto> addProduct(ProductDto productDto);

    ResponseEntity deleteProduct(int productId);

    List<ProductDto> getProductsByCategory(ProductCategory category);

    List<ProductCategory> getCategories();
}
