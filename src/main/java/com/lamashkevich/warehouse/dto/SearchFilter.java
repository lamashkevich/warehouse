package com.lamashkevich.warehouse.dto;

import jakarta.validation.constraints.NotNull;

public record SearchFilter(
        @NotNull String code,
        @NotNull String brand) {
}
