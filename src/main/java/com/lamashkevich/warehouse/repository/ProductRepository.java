package com.lamashkevich.warehouse.repository;

import com.lamashkevich.warehouse.dto.ProductInfoDto;
import com.lamashkevich.warehouse.entitty.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new com.lamashkevich.warehouse.dto.ProductInfoDto(p.id, p.code, p.brand, p.name) " +
            "FROM Product p " +
            "WHERE (:query IS NULL OR p.code LIKE %:query%) " +
            "OR (:query IS NULL OR p.brand LIKE %:query%) " +
            "OR (:query IS NULL OR p.name LIKE %:query%)")
    List<ProductInfoDto> findAllWithoutPrices(String query);

    @Query("SELECT p FROM Product p WHERE SIZE(p.prices) > 0")
    List<Product> findProductsWithPrices();

    Optional<Product> findByCodeAndBrandIgnoreCase(String code, String brand);

}
