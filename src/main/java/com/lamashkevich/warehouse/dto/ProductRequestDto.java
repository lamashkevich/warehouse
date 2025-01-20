package com.lamashkevich.warehouse.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDto(
        @NotNull String code,
        @NotNull String brand,
        @NotNull String name,

        @NotNull
        @DecimalMin(value = "0.00", inclusive = false)
        @Digits(integer = 19, fraction = 2)
        BigDecimal price,

        @NotNull @Min(1)
        Integer quantity) {
}
