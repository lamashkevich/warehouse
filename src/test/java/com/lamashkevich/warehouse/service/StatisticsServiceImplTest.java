package com.lamashkevich.warehouse.service;

import com.lamashkevich.warehouse.dto.StatisticsDto;
import com.lamashkevich.warehouse.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    @Test
    void getStatistics_WhenDataExists_ReturnsStatistics() {
        Long expectedTotalQuantity = 1L;
        BigDecimal expectedTotalPrice = BigDecimal.valueOf(1234.5);
        LocalDateTime expectedLastAdded = LocalDateTime.now();

        when(priceRepository.getTotalQuantity()).thenReturn(Optional.of(expectedTotalQuantity));
        when(priceRepository.getTotalPrice()).thenReturn(Optional.of(expectedTotalPrice));
        when(priceRepository.getLastAddedPrice()).thenReturn(Optional.of(expectedLastAdded));

        StatisticsDto result = statisticsService.getStatistics();

        assertNotNull(result);
        assertEquals(expectedTotalQuantity, result.totalQuantity());
        assertEquals(expectedTotalPrice, result.totalPrice());
        assertEquals(expectedLastAdded, result.lastAdded());

        verify(priceRepository, times(1)).getTotalQuantity();
        verify(priceRepository, times(1)).getTotalPrice();
        verify(priceRepository, times(1)).getLastAddedPrice();
    }
}