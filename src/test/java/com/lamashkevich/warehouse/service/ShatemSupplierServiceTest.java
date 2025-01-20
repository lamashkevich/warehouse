package com.lamashkevich.warehouse.service;

import com.lamashkevich.warehouse.client.shatem.ShatemClient;
import com.lamashkevich.warehouse.client.shatem.payload.Article;
import com.lamashkevich.warehouse.client.shatem.payload.ArticleCard;
import com.lamashkevich.warehouse.client.shatem.payload.ArticleFilter;
import com.lamashkevich.warehouse.dto.ProductInfoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShatemSupplierServiceTest {
    @Mock
    private ShatemClient shatemClient;

    @InjectMocks
    private ShatemSupplierService shatemSupplierService;

    @Test
    void getProductInfoByQuery_WhenQueryIsValid_ReturnsListOfProductsInfoDto() {
        String query = "CODE1";

        var article = new Article(1L, "CODE1", "BRAND1", "NAME1", "DESC1", "UNIT1", false);
        var articleCard = new ArticleCard(article);

        when(shatemClient.searchByQuery(query)).thenReturn(List.of(articleCard));

        List<ProductInfoDto> result = shatemSupplierService.getProductsInfoByQuery(query);

        assertNotNull(result);
        assertEquals(1, result.size());

        ProductInfoDto productInfoDto = result.get(0);
        assertEquals(1L, productInfoDto.id());
        assertEquals("CODE1", productInfoDto.code());
        assertEquals("BRAND1", productInfoDto.brand());
        assertEquals("NAME1", productInfoDto.name());

        verify(shatemClient, times(1)).searchByQuery(query);
    }

    @Test
    void getAnaloguesByCodeAndBrand_WhenCodeAndBrandAreValid_ReturnsListOfAnalogues() {
        String code = "CODE1";
        String brand = "BRAND1";

        var article1 = new Article(1L, code, brand, "NAME1", "DESC1", "UNIT1", false);
        var articleCard1 = new ArticleCard(article1);

        var article2 = new Article(2L, "CODE2", "BRAND2", "NAME2", "DESC2", "UNIT2", false);
        var articleCard2 = new ArticleCard(article2);

        when(shatemClient.searchByFilter(any(ArticleFilter.class))).thenReturn(List.of(articleCard1));
        when(shatemClient.searchAnalog(1L)).thenReturn(List.of(articleCard2));

        List<ProductInfoDto> result = shatemSupplierService.getAnaloguesByCodeAndBrand(code, brand);

        assertNotNull(result);
        assertEquals(1, result.size());

        ProductInfoDto productInfoDto = result.get(0);
        assertEquals(2L, productInfoDto.id());
        assertEquals("CODE2", productInfoDto.code());
        assertEquals("BRAND2", productInfoDto.brand());
        assertEquals("NAME2", productInfoDto.name());

        verify(shatemClient, times(1)).searchByFilter(any(ArticleFilter.class));
        verify(shatemClient, times(1)).searchAnalog(1L);
    }

}