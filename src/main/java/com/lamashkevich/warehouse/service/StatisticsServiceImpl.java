package com.lamashkevich.warehouse.service;

import com.lamashkevich.warehouse.dto.StatisticsDto;
import com.lamashkevich.warehouse.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final PriceRepository priceRepository;

    @Override
    public StatisticsDto getStatistics() {
        log.info("Getting statistics");

        Long totalQuantity = priceRepository.getTotalQuantity().orElse(0L);
        BigDecimal totalPrice = priceRepository.getTotalPrice().orElse(BigDecimal.valueOf(0.00));
        LocalDateTime lastAdded = priceRepository.getLastAddedPrice().orElse(LocalDateTime.now());

        return new StatisticsDto(totalQuantity, totalPrice, lastAdded);
    }
}
