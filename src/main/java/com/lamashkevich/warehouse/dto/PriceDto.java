package com.lamashkevich.warehouse.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceDto(
        Long id,
        BigDecimal value,
        Integer quantity,
        LocalDateTime createdAt) {
}
