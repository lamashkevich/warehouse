package com.lamashkevich.warehouse.service;

import com.lamashkevich.warehouse.dto.*;
import com.lamashkevich.warehouse.entitty.Price;
import com.lamashkevich.warehouse.entitty.Product;
import com.lamashkevich.warehouse.exception.ProductNotFoundException;
import com.lamashkevich.warehouse.repository.PriceRepository;
import com.lamashkevich.warehouse.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private PriceRepository priceRepository;
    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void findAllWithPrices_WhenProductsExist_ReturnsListOfProducts() {
        Product product = Product.builder()
                .id(1L)
                .code("CODE1")
                .brand("BRAND1")
                .name("NAME1")
                .build();

        Price price = Price.builder()
                .id(1L)
                .value(BigDecimal.valueOf(123))
                .quantity(45)
                .product(product)
                .build();


        product.setPrices(Collections.singletonList(price));

        when(productRepository.findProductsWithPrices()).thenReturn(Collections.singletonList(product));

        List<ProductResponseDto> result = productService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CODE1", result.get(0).code());
        assertEquals("BRAND1", result.get(0).brand());
        assertEquals("NAME1", result.get(0).name());
        assertEquals(1, result.get(0).prices().size());

        verify(productRepository, times(1)).findProductsWithPrices();
    }

    @Test
    void searchByFilter_WhenFilterIsValid_ReturnsListOfProducts() {
        SearchFilter filter = new SearchFilter("CODE1", "BRAND1");

        Product product1 = Product.builder()
                .id(1L)
                .code("CODE1")
                .brand("BRAND1")
                .name("NAME1")
                .build();

        Price price1 = Price.builder()
                .id(1L)
                .value(BigDecimal.valueOf(123))
                .quantity(45)
                .product(product1)
                .build();

        product1.setPrices(Collections.singletonList(price1));

        Product product2 = Product.builder()
                .id(2L)
                .code("CODE2")
                .brand("BRAND2")
                .name("NAME2")
                .build();

        Price price2 = Price.builder()
                .id(2L)
                .value(BigDecimal.valueOf(123))
                .quantity(45)
                .product(product2)
                .build();

        product2.setPrices(Collections.singletonList(price2));

        var analog = new ProductInfoDto(product2.getId(), product2.getCode(), product2.getBrand(), product2.getName());

        when(productRepository.findByCodeAndBrandIgnoreCase("CODE1", "BRAND1")).thenReturn(Optional.of(product1));
        when(productRepository.findByCodeAndBrandIgnoreCase("CODE2", "BRAND2")).thenReturn(Optional.of(product2));
        when(supplierService.getAnaloguesByCodeAndBrand("CODE1", "BRAND1")).thenReturn(List.of(analog));

        List<ProductResponseDto> result = productService.searchByFilter(filter);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> p.id().equals(1L)));
        assertTrue(result.stream().anyMatch(p -> p.id().equals(2L)));
    }

    @Test
    void create_WhenValidRequest_ReturnsListOfCreatedProducts() {
        var request = new ProductRequestDto("CODE1", "BRAND1", "NAME1", BigDecimal.valueOf(123), 45);

        Product product = Product.builder()
                .id(1L)
                .code("CODE1")
                .brand("BRAND1")
                .name("NAME1")
                .build();

        Price price = Price.builder()
                .id(1L)
                .value(BigDecimal.valueOf(123))
                .quantity(45)
                .product(product)
                .build();

        product.setPrices(Collections.singletonList(price));

        when(productRepository.findByCodeAndBrandIgnoreCase("CODE1", "BRAND1")).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        List<ProductResponseDto> result = productService.create(Collections.singletonList(request));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CODE1", result.get(0).code());
        assertEquals("BRAND1", result.get(0).brand());
        assertEquals("NAME1", result.get(0).name());
        assertEquals(1, result.get(0).prices().size());
    }

    @Test
    void deleteById_WhenProductExists_DeletesProduct() {
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteById(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void deleteById_WhenProductDoesNotExist_ThrowsException() {
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteById(productId));
    }

    @Test
    void deduct_WhenValidRequest_UpdatesQuantities() {
        DeductRequest request = new DeductRequest(1L, 5);

        Price price = Price.builder()
                .id(1L)
                .value(BigDecimal.valueOf(123))
                .quantity(45)
                .build();

        when(priceRepository.findAllById(anySet())).thenReturn(Collections.singletonList(price));
        when(priceRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        productService.deduct(Collections.singletonList(request));

        verify(priceRepository, times(1)).saveAll(anyList());
    }

}