package com.lamashkevich.warehouse.repository;

import com.lamashkevich.warehouse.entitty.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Price p")
    Optional<Long> getTotalQuantity();

    @Query("SELECT SUM(p.value * p.quantity) FROM Price p")
    Optional<BigDecimal> getTotalPrice();

    @Query("SELECT MAX(p.createdAt) FROM Price p")
    Optional<LocalDateTime> getLastAddedPrice();

}
