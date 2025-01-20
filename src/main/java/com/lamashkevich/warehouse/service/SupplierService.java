package com.lamashkevich.warehouse.service;

import com.lamashkevich.warehouse.dto.ProductInfoDto;

import java.util.List;

public interface SupplierService {

    List<ProductInfoDto> getProductsInfoByQuery(String query);

    List<ProductInfoDto> getAnaloguesByCodeAndBrand(String code, String brand);
}
