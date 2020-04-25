package com.carwash.server.controllers;

import com.carwash.server.dto.ProductDto;
import com.carwash.server.models.enums.ProductCategory;
import com.carwash.server.services.ProductService;
import com.carwash.server.utilies.AuthMiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("{id}")
    public ProductDto getProduct(@PathVariable("id") int id) {
        return productService.getProduct(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProductDto> addProduct(Authentication authentication, @RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteProduct(Authentication authentication, @PathVariable("id") int productId) {
        return productService.deleteProduct(productId);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity noHandlerFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @GetMapping("/category/{category}")
    public List<ProductDto> getProductsByCategory(@PathVariable("category") ProductCategory category) {
        return productService.getProductsByCategory(category);
    }
}
