package com.lamashkevich.warehouse.controller;

import com.lamashkevich.warehouse.dto.*;
import com.lamashkevich.warehouse.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponseDto> findAll() {
        return productService.findAll();
    }

    @PostMapping("/search")
    public List<ProductResponseDto> search(@RequestBody @Valid SearchFilter filter) {
        return productService.searchByFilter(filter);
    }

    @GetMapping("/search")
    public List<ProductInfoDto> findAllWithoutPrices(@RequestParam String query) {
        return productService.findAllWithoutPrices(query);
    }

    @PostMapping
    public List<ProductResponseDto> create(@RequestBody @Valid List<ProductRequestDto> request) {
        return productService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PostMapping("/deduct")
    @ResponseStatus(HttpStatus.OK)
    public void deduct(@RequestBody @Valid List<DeductRequest> request) {
        productService.deduct(request);
    }
}
