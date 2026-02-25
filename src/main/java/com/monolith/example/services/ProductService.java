package com.monolith.example.services;

import com.monolith.example.dto.ProductDto;
import com.monolith.example.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductDto dto);
    List<ProductResponseDto> findProducts();
    ProductResponseDto findProductByName(String name);
    ProductResponseDto findProduct(Long id);
    ProductResponseDto updateProduct(ProductDto dto, Long id);
    void deleteProduct(Long id);
    void deleteProductByName(String name);
}
