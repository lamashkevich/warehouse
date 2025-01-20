package com.lamashkevich.warehouse.dto;

import java.util.List;

public record ProductResponseDto(
        Long id,
        String code,
        String brand,
        String name,
        List<PriceDto> prices) {
}
