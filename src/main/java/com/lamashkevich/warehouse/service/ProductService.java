package com.lamashkevich.warehouse.service;

import com.lamashkevich.warehouse.dto.*;

import java.util.List;

public interface ProductService {

    List<ProductResponseDto> findAll();

    List<ProductResponseDto> searchByFilter(SearchFilter filter);

    List<ProductInfoDto> findAllWithoutPrices(String query);

    List<ProductResponseDto> create(List<ProductRequestDto> productRequestDto);

    void deleteById(Long id);

    void deduct(List<DeductRequest> request);
}
