package com.lamashkevich.warehouse.service;

import com.lamashkevich.warehouse.dto.*;
import com.lamashkevich.warehouse.entitty.Price;
import com.lamashkevich.warehouse.entitty.Product;
import com.lamashkevich.warehouse.exception.ProductNotFoundException;
import com.lamashkevich.warehouse.repository.PriceRepository;
import com.lamashkevich.warehouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final SupplierService supplierService;

    @Override
    public List<ProductResponseDto> findAll() {
        log.info("Getting all products with prices");
        return productRepository.findProductsWithPrices()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductResponseDto> searchByFilter(SearchFilter filter) {
        log.info("Searching products by filter : {}", filter);

        if (filter == null || filter.code() == null || filter.brand() == null) {
            log.warn("Invalid filter: {}", filter);
            return Collections.emptyList();
        }

        Set<Product> setProducts = new HashSet<>();
        addProductIfPricePresent(filter.code(), filter.brand(), setProducts);

        List<ProductInfoDto> analogues = supplierService.getAnaloguesByCodeAndBrand(filter.code(), filter.brand());
        analogues.forEach(analogue -> {
            log.debug("Searching analogues: code={}, brand={}", analogue.code(), analogue.brand());
            addProductIfPricePresent(analogue.code(), analogue.brand(), setProducts);
        });

        return setProducts.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductInfoDto> findAllWithoutPrices(String query) {
        log.info("Searching products by query : {}", query);

        List<ProductInfoDto> withoutPrices = productRepository.findAllWithoutPrices(query);
        List<ProductInfoDto> info = supplierService.getProductsInfoByQuery(query);

        Set<ProductInfoDto> uniqueSet = new HashSet<>(withoutPrices);
        uniqueSet.addAll(info);

        return new ArrayList<>(uniqueSet);
    }

    @Override
    @Transactional
    public List<ProductResponseDto> create(List<ProductRequestDto> request) {
        log.info("Creating products : {}", request);

        List<ProductResponseDto> response = new ArrayList<>();

        for (ProductRequestDto item : request) {
            Product product = productRepository.findByCodeAndBrandIgnoreCase(item.code(), item.brand())
                    .orElseGet(() -> createNewProduct(item));

            Price price = createPrice(item, product);
            product.getPrices().add(price);

            productRepository.save(product);

            response.add(mapToResponse(product));
        }

        return response;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting product by id : {}", id);

        if (!productRepository.existsById(id)) {
            log.error("Product not found");
            throw new ProductNotFoundException(id);
        }

        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deduct(List<DeductRequest> request) {
        log.info("Deducting: {}", request);

        if (request == null || request.isEmpty()) {
            log.warn("Invalid request");
            return;
        }

        Map<Long, Integer> quantityMap = request.stream()
                .filter(i -> i.quantity() > 0)
                .collect(Collectors.toMap(DeductRequest::id, DeductRequest::quantity));

        List<Price> prices = priceRepository.findAllById(quantityMap.keySet());

        List<Price> toRemove = new ArrayList<>();
        List<Price> toUpdate = new ArrayList<>();

        for (Price price: prices) {
            int newQuantity = price.getQuantity() - quantityMap.get(price.getId());
            if (newQuantity <= 0) {
                toRemove.add(price);
            } else {
                price.setQuantity(newQuantity);
                toUpdate.add(price);
            }
        }

        priceRepository.deleteAll(toRemove);
        priceRepository.saveAll(toUpdate);
    }

    private void addProductIfPricePresent(String code, String brand, Set<Product> setProducts) {
        productRepository.findByCodeAndBrandIgnoreCase(code, brand)
                .filter(product -> !product.getPrices().isEmpty())
                .ifPresent(setProducts::add);
    }
    private Price createPrice(ProductRequestDto item, Product product) {
        return Price.builder()
                .value(item.price())
                .quantity(item.quantity())
                .product(product)
                .build();
    }

    private Product createNewProduct(ProductRequestDto request) {
        return Product.builder()
                .code(request.code())
                .brand(request.brand())
                .name(request.name())
                .prices(new ArrayList<>())
                .build();
    }

    private ProductResponseDto mapToResponse(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getCode(),
                product.getBrand(),
                product.getName(),
                product.getPrices().stream()
                        .map(this::mapToPriceDto)
                        .toList()
        );
    }

    private PriceDto mapToPriceDto(Price price) {
        return new PriceDto(
                price.getId(),
                price.getValue(),
                price.getQuantity(),
                price.getCreatedAt()
        );
    }
}
