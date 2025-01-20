package com.lamashkevich.warehouse.service;

import com.lamashkevich.warehouse.client.shatem.ShatemClient;
import com.lamashkevich.warehouse.client.shatem.payload.ArticleCard;
import com.lamashkevich.warehouse.client.shatem.payload.ArticleFilter;
import com.lamashkevich.warehouse.dto.ProductInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShatemSupplierService implements SupplierService {

    private final ShatemClient shatemClient;

    @Override
    public List<ProductInfoDto> getProductsInfoByQuery(String query) {
        log.info("Getting products info by query : {}", query);
        return shatemClient.searchByQuery(query)
                .stream()
                .map(this::mapToProductInfoDto)
                .toList();
    }

    @Override
    public List<ProductInfoDto> getAnaloguesByCodeAndBrand(String code, String brand) {
        log.info("Getting analogues by code ({}) and brand ({})", code, brand);
        return shatemClient.searchByFilter(new ArticleFilter(List.of(new ArticleFilter.Key(code, brand))))
                .stream()
                .flatMap(card -> shatemClient.searchAnalog(card.article().getId()).stream())
                .map(this::mapToProductInfoDto)
                .toList();
    }

    private ProductInfoDto mapToProductInfoDto(ArticleCard card) {
        return new ProductInfoDto(
                card.article().getId(),
                card.article().getCode(),
                card.article().getTradeMarkName(),
                card.article().getName()
        );
    }
}
