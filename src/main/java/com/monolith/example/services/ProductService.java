package com.monolith.example.services;

import com.monolith.example.dto.ProductCreateDto;
import com.monolith.example.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductCreateDto dto);
    List<ProductResponseDto> findProducts();
    ProductResponseDto findProductByName(String name);
    ProductResponseDto findProduct(Long id);
    ProductResponseDto updateProduct(ProductCreateDto dto, Long id);
    void deleteProduct(Long id);
}
