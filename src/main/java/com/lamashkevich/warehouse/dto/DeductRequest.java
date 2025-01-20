package com.lamashkevich.warehouse.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DeductRequest(
        @NotNull Long id,
        @NotNull @Min(0) Integer quantity) {
}
