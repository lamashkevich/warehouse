package com.lamashkevich.warehouse.controller;

import com.lamashkevich.warehouse.dto.StatisticsDto;
import com.lamashkevich.warehouse.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    public StatisticsDto getStatistics() {
        return statisticsService.getStatistics();
    }
}
