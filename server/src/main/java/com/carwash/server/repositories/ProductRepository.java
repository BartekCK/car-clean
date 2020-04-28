package com.carwash.server.repositories;

import com.carwash.server.models.Product;
import com.carwash.server.models.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findById(int productId);

    List<Product> findAllByCategory(ProductCategory category);
}
