package com.lamashkevich.warehouse.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StatisticsDto(
        Long totalQuantity,
        BigDecimal totalPrice,
        LocalDateTime lastAdded) {}
